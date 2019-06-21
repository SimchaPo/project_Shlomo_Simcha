package renderer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import elements.Camera;
import elements.LightSource;
import geometries.Plane;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import static primitives.Util.*;
import primitives.Vector;
import scene.Scene;

/**
 * Render class gets scene and image writer and sets everything together to in
 * image
 * 
 * @author OWNER
 *
 */
public class Render {
	private static final int MAX_CALC_COLOR_LEVEL = 15;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double EPS = 0.1;
	private static final int MATRIX_SIZE = 9;
	private Scene _scene;
	private ImageWriter _imageWriter;
	private int _cores;
	private boolean _thred = true;
	private boolean _adaptiveSuperSampling = true;
	private boolean _superSampling = false;
	private Color _bg;
	private boolean _focal;

	/**
	 * ******** constructor *********
	 */
	public Render(Scene s, ImageWriter im) {
		_scene = s;
		_imageWriter = im;
		_bg = new Color(_scene.getBackground());
		_cores = Runtime.getRuntime().availableProcessors();
		setFocal(true);
		if(_focal)
			_adaptiveSuperSampling = false;
	}

	/**
	 * get scene
	 * 
	 * @return
	 */
	public Scene getScene() {
		return _scene;
	}

	/**
	 * get Image writer
	 * 
	 * @return
	 */
	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	/**
	 * get number of cores
	 * 
	 * @return
	 */
	public int getCores() {
		return _cores;
	}

	/**
	 * setter for running with multy thred
	 * 
	 * @param _thred
	 */
	public void setThred(boolean thred) {
		_thred = thred;
	}

	/**
	 * 
	 * @param _adaptiveSuperSampling
	 */
	public void setAdaptiveSuperSampling(boolean adaptiveSuperSampling) {
		_adaptiveSuperSampling = adaptiveSuperSampling;
	}

	/**
	 * 
	 * @param superSampling
	 */
	public void setSuperSampling(boolean superSampling) {
		_superSampling = superSampling;
	}

	public void setFocal(boolean focal) {
		if (focal)
			_focal = _scene.getCamera().isFocus();
		else
			_focal = false;
	}

	/**
	 * function colors each point in the image according to the intersections
	 * 
	 * @throws InterruptedException
	 */
	public void renderImage() throws InterruptedException {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		double screenDis = _scene.getScreenDistance(), imageWidth = _imageWriter.getWidth(),
				imageHeigt = _imageWriter.getHeight(), rx = imageWidth / nX, ry = imageHeigt / nY;
		Camera camera = _scene.getCamera();
		_bg = _scene.getBackground();
		java.awt.Color bg = _bg.getColor();
		ThreadPoolExecutor pool = null;
		if (_thred)
			pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(_cores);
		for (int i = 0; i < nX; ++i)
			for (int j = 0; j < nY; ++j) {
				final int i2 = i, j2 = j;
				Runnable worker = () -> {
					if (_adaptiveSuperSampling) {
						Point3D pij = camera.getPixelCenter(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt);
						_imageWriter.writePixel(i2, j2, adaptiveSuperSampling(pij, rx, ry).getColor());
					} else if (_superSampling) {
						Point3D pij = camera.getPixelCenter(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt);
						_imageWriter.writePixel(i2, j2, superSampling(pij, rx, ry).getColor());
					} else if (_focal) {
						_imageWriter.writePixel(i2, j2, //
								colorWithFocus(camera.constructRayThroughPixel(nX, nY, i2, j2, screenDis, imageWidth,
										imageHeigt)).getColor());
					} else {
						Ray ray = camera.constructRayThroughPixel(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt);
						GeoPoint gp = findClosestIntersection(ray);
						_imageWriter.writePixel(i2, j2, gp == null ? bg : calcColor(gp, ray).getColor());
					}
				};
				if (_thred)
					pool.execute(worker);
				else
					worker.run();
			}
		if (_thred) {
			pool.shutdown();
			while (!pool.awaitTermination(1, TimeUnit.MINUTES))
				;
		}
	}

	/**
	 * calls the calcColor function with default arguments
	 * 
	 * @param closestPoint
	 * @param ray
	 * @return
	 */
	private Color calcColor(GeoPoint closestPoint, Ray ray) {
		return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, 1).add(_scene.getAmbientLight().getIntensity());
	}

	/**
	 * gets a point and sets its color
	 * 
	 * @param intersection
	 * @return
	 */
	private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) {
		if (level == 0 || k <= MIN_CALC_COLOR_K) {
			return Color.BLACK;
		}
		Color color = new Color(intersection.geometry.getEmmission());
		Vector v = inRay.getVector();
		Vector n = intersection.geometry.getNormal(intersection.point);
		int nShininess = intersection.geometry.getMaterial().getNShininess();
		double kd = intersection.geometry.getMaterial().getKD();
		double ks = intersection.geometry.getMaterial().getKS();
		double nDotV = n.vectorsDotProduct(v);
		for (LightSource lightSource : _scene.getLights()) {
			Vector l = lightSource.getL(intersection.point);
			if (!isZero(n.vectorsDotProduct(l) * nDotV)) {
				double ktr = transparency(l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		double kr = intersection.geometry.getMaterial().getKR();
		double kk = kr * k;
		if (kk > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, intersection.point, inRay);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null) {
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kk).scale(kr));
			}
		}
		double kt = intersection.geometry.getMaterial().getKT();
		kk = kt * k;
		if (kk > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, intersection.point, inRay);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null) {
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kk).scale(kt));
			}
		}
		return color;
	}

	/**
	 * calculate color with focus
	 * 
	 * @param points
	 * @param focusPoint
	 * @return
	 */
	private Color colorWithFocus(Ray ray) {
		Color col = Color.BLACK;
		double aperture = _scene.getCamera().getApertureSize();
		Plane focalPlane = _scene.getFocalPlane();
		Point3D focalPoint = focalPlane.findIntersections(ray).get(0).point;
		Plane viewPlane = _scene.getViewPlane();
		Point3D pCenter = viewPlane.findIntersections(ray).get(0).point;
		List<Ray> apertureRays = _scene.getCamera().getApertureRandomRays(pCenter, focalPoint, aperture);
		for (Ray aperRay : apertureRays) {
			GeoPoint gp = findClosestIntersection(aperRay);
			col = col.add(gp == null ? _bg : calcColor(gp, aperRay));
		}
		return col.reduce(apertureRays.size());
	}

	/**
	 * color points with super sampling
	 * 
	 * @param pij
	 * @param rx
	 * @param ry
	 * @return
	 */
	private Color superSampling(Point3D pij, double rx, double ry) {
		Color col = Color.BLACK;
		List<Ray> pixelRays = _scene.getCamera().getPixelRays(pij, MATRIX_SIZE, rx, ry);
		for (Ray ray : pixelRays) {
			GeoPoint gp = findClosestIntersection(ray);
			col = col.add(_focal ? colorWithFocus(ray) : gp == null ? _bg : calcColor(gp, ray));
		}
		return col.reduce(pixelRays.size());
	}

	private Color adaptiveSuperSampling(Point3D pij, double rx, double ry) {
		List<Ray> pixelRays = _scene.getCamera().getPixelRays(pij, MATRIX_SIZE, rx, ry);
		Color[] colors = new Color[MATRIX_SIZE * MATRIX_SIZE];
		setColorsArray(pixelRays, colors, 0, MATRIX_SIZE - 1, MATRIX_SIZE * (MATRIX_SIZE - 1),
				MATRIX_SIZE * MATRIX_SIZE - 1);
		Color col = Color.BLACK;
		for (int i = 0; i < MATRIX_SIZE * MATRIX_SIZE; ++i) {
			col = col.add(colors[i]);
		}
		return col.reduce(MATRIX_SIZE * MATRIX_SIZE);
	}

	/**
	 * recursive function to set colors of array with adaptive super sampling
	 * 
	 * @param pixelRays
	 * @param colors
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void setColorsArray(List<Ray> pixelRays, Color[] colors, int x, int y, int z, int w) {
		Ray ray;
		GeoPoint gp;
		if (colors[x] == null) {
			ray = pixelRays.get(x);
			gp = findClosestIntersection(pixelRays.get(x));
			colors[x] = _focal ? colorWithFocus(ray) : gp == null ? _bg : calcColor(gp, ray);
		}
		if (colors[y] == null) {
			ray = pixelRays.get(y);
			gp = findClosestIntersection(pixelRays.get(y));
			colors[y] = _focal ? colorWithFocus(ray) : gp == null ? _bg : calcColor(gp, ray);
		}
		if (colors[z] == null) {
			ray = pixelRays.get(z);
			gp = findClosestIntersection(pixelRays.get(z));
			colors[z] = _focal ? colorWithFocus(ray) : gp == null ? _bg : calcColor(gp, ray);
		}
		if (colors[w] == null) {
			ray = pixelRays.get(w);
			gp = findClosestIntersection(pixelRays.get(w));
			colors[w] = _focal ? colorWithFocus(ray) : gp == null ? _bg : calcColor(gp, ray);
		}
		if (y - x == 1)
			return;
		if (Color.colorsEqual(colors[x], colors[y], colors[z], colors[w])) {
			colorSquer(colors, x, y);
			return;
		}
		int newPoints[] = split(x, y, z, w);
		setColorsArray(pixelRays, colors, newPoints[0], newPoints[1], newPoints[3], newPoints[4]);
		setColorsArray(pixelRays, colors, newPoints[1], newPoints[2], newPoints[4], newPoints[5]);
		setColorsArray(pixelRays, colors, newPoints[3], newPoints[4], newPoints[6], newPoints[7]);
		setColorsArray(pixelRays, colors, newPoints[4], newPoints[5], newPoints[7], newPoints[8]);
	}

	/**
	 * colors all points in part of matrix if corners have the same color
	 * 
	 * @param colors
	 * @param startP
	 * @param endP
	 */
	private void colorSquer(Color[] colors, int startP, int endP) {
		for (int j = startP; j <= startP + (endP - startP) * MATRIX_SIZE; j += MATRIX_SIZE)
			for (int i = 0; i <= endP - startP; ++i)
				colors[j + i] = colors[startP];
	}

	/**
	 * gets index of square corners and splits it into 4 smaller squares
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 * @return
	 */
	private int[] split(int x, int y, int z, int w) {
		int index[] = new int[9];
		index[0] = x;
		index[1] = (x + y) / 2;
		index[2] = y;
		index[3] = (x + z) / 2;
		index[4] = (x + w) / 2;
		index[5] = (y + w) / 2;
		index[6] = z;
		index[7] = (z + w) / 2;
		index[8] = w;
		return index;
	}

	/**
	 * calculates the amount of shadow at point
	 * 
	 * @param l
	 * @param n
	 * @param intersection
	 * @return
	 */
	private double transparency(Vector l, Vector n, GeoPoint intersection) {
		Vector lightDirection = l.scale(-1);
		Vector epsVector = n.scale(n.vectorsDotProduct(lightDirection) > 0 ? EPS : -EPS);
		Point3D point = intersection.point.addVec(epsVector);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
		double ktr = 1;
		for (GeoPoint gp : intersections) {
			ktr *= gp.geometry.getMaterial().getKT();
		}
		return ktr;
	}

	/**
	 * finds closest intersection to ray point
	 * 
	 * @param ray
	 * @return
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
		if (intersections.isEmpty()) {
			return null;
		}
		GeoPoint closestPoint = new GeoPoint(intersections.get(0));
		Point3D rayPnt = ray.getPoint();
		double minDistancePow = rayPnt.distancePow(closestPoint.point);
		double disPow;
		for (GeoPoint p : intersections) {
			disPow = rayPnt.distancePow(p.point);
			if (disPow < minDistancePow) {
				minDistancePow = disPow;
				closestPoint = p;
			}
		}
		return closestPoint;
	}

	/**
	 * returns new ray of refraction
	 * 
	 * @param point
	 * @param inRay
	 * @return
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.getVector();
		Vector epsVector = n.scale(n.vectorsDotProduct(v) > 0 ? EPS : -EPS);
		return new Ray(point.addVec(epsVector), v);
	}

	/**
	 * returns new ray of reflection
	 * 
	 * @param n
	 * @param point
	 * @param inRay
	 * @return
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.getVector();
		double vDotPn = v.vectorsDotProduct(n);
		Vector r = isZero(vDotPn) ? v : v.vectorSub(n.scale(2 * v.vectorsDotProduct(n)));
		Vector epsVector = n.scale(n.vectorsDotProduct(r) > 0 ? EPS : -EPS);
		return new Ray(point.addVec(epsVector), r);
	}

	/**
	 * calculate the diffusive of point
	 * 
	 * @param kd
	 * @param l
	 * @param n
	 * @param lightIntensity
	 * @return
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return lightIntensity.scale(kd * Math.abs(l.vectorsDotProduct(n)));
	}

	/**
	 * calculate the specular of point
	 * 
	 * @param ks
	 * @param l
	 * @param n
	 * @param v
	 * @param nShininess
	 * @param lightIntensity
	 * @return
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.vectorSub(n.scale(2 * (l.vectorsDotProduct(n)))).normalize();
		return lightIntensity.scale(ks * Math.pow(Math.max(0, -1 * v.vectorsDotProduct(r)), nShininess));
	}

	/**
	 * prints a grid in image
	 * 
	 * @param size
	 * @param opt
	 */
	public void printGrid(int size, Color... opt) {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		int sizeX = nX % 2 == 0 ? nX / size : nX / size + 1, sizeY = nY % 2 == 0 ? nY / size : nY / size + 1;
		Color _color = opt.length > 0 ? opt[0] : new Color(255, 255, 255);
		for (int i = 0; i < nX; i += sizeX) {
			for (int j = 0; j < nY; ++j) {
				_imageWriter.writePixel(i, j, _color.getColor());
				_imageWriter.writePixel(nX - 1, j, _color.getColor());
			}
		}
		for (int j = 0; j < nY; j += sizeY) {
			for (int i = 0; i < nX; ++i) {
				_imageWriter.writePixel(i, j, _color.getColor());
				_imageWriter.writePixel(i, nY - 1, _color.getColor());
			}
		}
	}
}
