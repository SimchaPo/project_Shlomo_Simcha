package renderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private Scene _scene;
	private ImageWriter _imageWriter;
	private Point3D p0;
	private Map<Point3D, Color> _pointsColors;
	private int _cores;
	private boolean _thred = true;
	private boolean _adaptiveSuperSampling = false;
	private boolean _superSampling = false;
	private Color _bg;

	/**
	 * ******** constructor *********
	 */
	public Render(Scene s, ImageWriter im) {
		_scene = s;
		_imageWriter = im;
		p0 = _scene.getCamera().getP0();
		_pointsColors = new HashMap<Point3D, Color>();
		_bg = new Color(_scene.getBackground());
		_cores = Runtime.getRuntime().availableProcessors();
		_thred = true;
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
	public void setThred(boolean _thred) {
		this._thred = _thred;
	}

	/**
	 * 
	 * @param _adaptiveSuperSampling
	 */
	public void setAdaptiveSuperSampling(boolean _adaptiveSuperSampling) {
		this._adaptiveSuperSampling = _adaptiveSuperSampling;
	}

	/**
	 * 
	 * @param _superSampling
	 */
	public void setSuperSampling(boolean _superSampling) {
		this._superSampling = _superSampling;
	}

	/**
	 * function colors each point in the image according to the intersections
	 * 
	 * @throws InterruptedException
	 */
	public void renderImage() throws InterruptedException {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		double screenDis = _scene.getScreenDistance(), imageWidth = _imageWriter.getWidth(),
				imageHeigt = _imageWriter.getHeight();
		boolean focal = _scene.getCamera().isFocus();
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
//					if (_adaptiveSuperSampling || _superSampling) {
//						Point3D pij = camera.getPixelCenter(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt);
//						_imageWriter.writePixel(i2, j2, calcColor(pij, ry, rx).getColor());
//					} else {
					if (focal) {
						_imageWriter.writePixel(i2, j2, //
								calcColorFocus(camera.getPixelCenter(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt))
										.getColor());
					} else {
						Ray ray = camera.constructRayThroughPixel(nX, nY, i2, j2, screenDis, imageWidth, imageHeigt);
						GeoPoint gp = findClosestIntersection(ray);
						_imageWriter.writePixel(i2, j2, //
								gp == null ? bg : calcColor(gp, ray).getColor());
					}
//					}
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

//	/**
//	 * calculate color with average of random rays
//	 * 
//	 * @param pij
//	 * @param ry
//	 * @param rx
//	 * @return
//	 */
//	private Color calcColor(Point3D pij, double ry, double rx) {
//		List<Point3D> pixelPoints = getRandomPoints(pij, ry, rx);
//		addRandomPoints(pixelPoints, p0, false, ry / 2, rx / 2);
//		Color col = Color.BLACK;
//		for (Point3D pnt : pixelPoints)
//			col = col.add(_scene.isFocus() ? calcColorFocus(pnt) : getColorOfPoint(new Ray(p0, pnt.subtract(p0))));
//		return col.reduce(pixelPoints.size());
//	}

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
	private Color calcColorFocus(Point3D pij) {
		Color col = Color.BLACK;
		double aperture = _scene.getCamera().getApertureSize();
		Plane focalPlane = _scene.getFocalPlane();
		Point3D focalPoint = focalPlane.findIntersections(new Ray(p0, pij.subtract(p0))).get(0).point;
		List<Ray> apertureRays = _scene.getCamera().getApertureRandomRays(pij, focalPoint, aperture);
		for (Ray ray : apertureRays) {
			GeoPoint gp = findClosestIntersection(ray);
			col = col.add(gp == null ? _bg : calcColor(gp, ray));
		}
		return col.reduce(apertureRays.size());
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

//	/**
//	 * gets a center and adds random points if needed
//	 * 
//	 * @param points
//	 * @param pnt
//	 * @param focal
//	 * @param yRad
//	 * @param xRad
//	 */
//	private void addRandomPoints(List<Point3D> points, Point3D pnt, boolean focal, double yRad, double xRad) {
//		int len = points.size() / 5;
//		boolean equal;
//		for (int t = 0; t < len; ++t) {
//			equal = focal
//					? colorEquale(getColorOfPoint(new Ray(points.get(1 + 5 * t), pnt.subtract(points.get(1 + 5 * t)))),
//							getColorOfPoint(new Ray(points.get(2 + 5 * t), pnt.subtract(points.get(2 + 5 * t)))),
//							getColorOfPoint(new Ray(points.get(3 + 5 * t), pnt.subtract(points.get(3 + 5 * t)))),
//							getColorOfPoint((new Ray(points.get(4 + 5 * t), pnt.subtract(points.get(4 + 5 * t))))))
//					: colorEquale(getColorOfPoint(new Ray(pnt, points.get(1 + 5 * t).subtract(pnt))),
//							getColorOfPoint(new Ray(pnt, points.get(2 + 5 * t).subtract(pnt))),
//							getColorOfPoint(new Ray(pnt, points.get(3 + 5 * t).subtract(pnt))),
//							getColorOfPoint(new Ray(pnt, points.get(4 + 5 * t).subtract(pnt))));
//			if (!equal) {
//				points.addAll(getRandomPoints(points.get(0 + 5 * t).addVec(vUp.scale(yRad)).addVec(vRight.scale(xRad)),
//						yRad, xRad));
//				points.addAll(getRandomPoints(points.get(0 + 5 * t).addVec(vUp.scale(yRad)).addVec(vRight.scale(-xRad)),
//						yRad, xRad));
//				points.addAll(getRandomPoints(points.get(0 + 5 * t).addVec(vUp.scale(-yRad)).addVec(vRight.scale(xRad)),
//						yRad, xRad));
//				points.addAll(getRandomPoints(
//						points.get(0 + 5 * t).addVec(vUp.scale(-yRad)).addVec(vRight.scale(-xRad)), yRad, xRad));
//			}
//		}
//	}

//	/**
//	 * check if different between two colors is small
//	 * 
//	 * @param col1
//	 * @param col2
//	 * @return
//	 */
//	private boolean colorEquale(Color col1, Color col2, Color col3, Color col4) {
//		return Math.abs(col1.getColor().getRed() - col2.getColor().getRed()) <= 10
//				&& Math.abs(col2.getColor().getGreen() - col3.getColor().getGreen()) <= 10
//				&& Math.abs(col3.getColor().getBlue() - col4.getColor().getBlue()) <= 10;
//	}

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

//	/**
//	 * calculates color of point and adds color to map
//	 * 
//	 * @param rayPnt
//	 * @param focalPoint
//	 * @return
//	 */
//	private Color getColorOfPoint(Ray ray) {
//		GeoPoint closestPoint = findClosestIntersection(ray);
//		if (closestPoint == null) {
//			return _scene.getBackground();
//		}
//		if (!_pointsColors.containsKey(closestPoint.point)) {
//			_pointsColors.put(closestPoint.point, calcColor(closestPoint, ray));
//		}
//		return _pointsColors.get(closestPoint.point);
//	}
}
