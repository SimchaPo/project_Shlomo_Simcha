//Simcha Podolsky 311215149
//Shlomo Meirzon

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
	private Color _bg;
	private boolean _thred = true;
	private boolean _adaptiveSuperSampling = true;
	private boolean _superSampling = false;
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
		if (_focal)
			_adaptiveSuperSampling = false;
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
		double x = rx / (MATRIX_SIZE - 1), y = -ry / (MATRIX_SIZE - 1);
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
					if (_adaptiveSuperSampling || _superSampling) {
						Point3D pijCorner = camera.getPixelCorner(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt);
						_imageWriter.writePixel(i2, j2, (_adaptiveSuperSampling ? adaptiveSuperSampling(pijCorner, x, y)
								: superSampling(pijCorner, x, y)).getColor());
					} else {
						Ray ray = camera.constructRayThroughPixel(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt);
						if (_focal)
							_imageWriter.writePixel(i2, j2, depthOfField(ray).getColor());
						else {
							GeoPoint gp = findClosestIntersection(ray);
							_imageWriter.writePixel(i2, j2, gp == null ? bg : calcColor(gp, ray).getColor());
						}
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
	private Color depthOfField(Ray ray) {
		Color col = Color.BLACK;
		Plane focalPlane = _scene.getFocalPlane();
		Point3D focalPoint = focalPlane.findIntersections(ray).get(0).point;
		Plane viewPlane = _scene.getViewPlane();
		Point3D pCenter = viewPlane.findIntersections(ray).get(0).point;
		List<Ray> apertureRays = _scene.getCamera().getApertureRandomRays(pCenter, focalPoint);
		for (Ray aperRay : apertureRays) {
			GeoPoint gp = findClosestIntersection(aperRay);
			col = col.add(gp == null ? _bg : calcColor(gp, aperRay));
		}
		return col.reduce(apertureRays.size());
	}

	/**
	 * color points with super sampling
	 * 
	 * @param pijCorner
	 * @param x
	 * @param y
	 * @return
	 */
	private Color superSampling(Point3D pijCorner, double x, double y) {
		Color col = Color.BLACK;
		List<Ray> pixelRays = _scene.getCamera().getAllPixelRays(pijCorner, MATRIX_SIZE, x, y);
		for (Ray ray : pixelRays) {
			GeoPoint gp = findClosestIntersection(ray);
			col = col.add(_focal ? depthOfField(ray) : gp == null ? _bg : calcColor(gp, ray));
		}
		return col.reduce(pixelRays.size());
	}

	/**
	 * color with adaptive super sampling
	 * 
	 * @param pijCorner
	 * @param x
	 * @param y
	 * @return
	 */
	private Color adaptiveSuperSampling(Point3D pijCorner, double x, double y) {
		int matrixSquare = MATRIX_SIZE * MATRIX_SIZE;
		Color[] colors = new Color[matrixSquare];
		int[] corners = new int[4];
		corners[0] = 0;
		corners[1] = MATRIX_SIZE - 1;
		corners[2] = MATRIX_SIZE * (MATRIX_SIZE - 1);
		corners[3] = matrixSquare - 1;
		List<Ray> pixelCornersRays = _scene.getCamera().getPointsPixelRays(pijCorner, MATRIX_SIZE, x, y, corners[0],
				corners[1], corners[2], corners[3]);
		Ray ray;
		GeoPoint gp;
		for (int i = 0; i < 4; ++i) {
			ray = pixelCornersRays.get(i);
			gp = findClosestIntersection(ray);
			colors[corners[i]] = _focal ? depthOfField(ray) : gp == null ? _bg : calcColor(gp, ray);
		}
		setColorsArray(colors, corners[0], corners[1], corners[2], corners[3], pijCorner, x, y);
		Color col = Color.BLACK;
		for (int i = 0; i < matrixSquare; ++i) {
			col = col.add(colors[i]);
		}
		return col.reduce(matrixSquare);
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
	 * @param pijCorner
	 * @param rx
	 * @param ry
	 */
	public void setColorsArray(Color[] colors, int x, int y, int z, int w, Point3D pijCorner, double rx, double ry) {
		if (y - x == 1)
			return;
		if (Color.colorsEqual(colors[x], colors[y], colors[z], colors[w])) {
			colorSquer(colors, x, y);
			return;
		}
		int splitedPoints[] = split(x, y, z, w);
		List<Ray> splitedPixelRays = _scene.getCamera().getPointsPixelRays(pijCorner, MATRIX_SIZE, rx, ry,
				splitedPoints);
		Ray ray;
		GeoPoint gp;
		for (int i = 0; i < 5; ++i) {
			ray = splitedPixelRays.get(i);
			gp = findClosestIntersection(ray);
			colors[splitedPoints[i]] = _focal ? depthOfField(ray) : gp == null ? _bg : calcColor(gp, ray);
		}
		setColorsArray(colors, x, splitedPoints[0], splitedPoints[1], splitedPoints[2], pijCorner, rx, ry);
		setColorsArray(colors, splitedPoints[0], y, splitedPoints[2], splitedPoints[3], pijCorner, rx, ry);
		setColorsArray(colors, splitedPoints[1], splitedPoints[2], z, splitedPoints[4], pijCorner, rx, ry);
		setColorsArray(colors, splitedPoints[2], splitedPoints[3], splitedPoints[4], w, pijCorner, rx, ry);
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
		int index[] = new int[5];
		index[0] = (x + y) / 2;
		index[1] = (x + z) / 2;
		index[2] = (x + w) / 2;
		index[3] = (y + w) / 2;
		index[4] = (z + w) / 2;
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
	 * setter for running with multithread
	 * 
	 * @param _thred
	 */
	public void setThred(boolean thred) {
		_thred = thred;
	}

	/**
	 * setter for adaptive super sampling
	 * 
	 * @param _adaptiveSuperSampling
	 */
	public void setAdaptiveSuperSampling(boolean adaptiveSuperSampling) {
		_adaptiveSuperSampling = adaptiveSuperSampling;
	}

	/**
	 * setter for super sampling
	 * 
	 * @param superSampling
	 */
	public void setSuperSampling(boolean superSampling) {
		_superSampling = superSampling;
	}

	/**
	 * setter for focal (if camera has focal)
	 * 
	 * @param focal
	 */
	public void setFocal(boolean focal) {
		if (focal)
			_focal = _scene.getCamera().isFocus();
		else
			_focal = false;
	}
}
