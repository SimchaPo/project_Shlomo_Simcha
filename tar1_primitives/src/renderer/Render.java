package renderer;

import java.util.List;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
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

	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double EPS = 0.1;
	private Scene _scene;
	private ImageWriter _imageWriter;

	public Render(Scene s, ImageWriter im) {
		_scene = s;
		_imageWriter = im;
	}

	public Scene getScene() {
		return _scene;
	}

	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	/**
	 * function colors each point in the image according to the intersections
	 */
	public void renderImage() {
		for (int i = 0; i < _imageWriter.getNx(); ++i) {
			for (int j = 0; j < _imageWriter.getNy(); ++j) {
				Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), i, j,
						_scene.getScreenDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
				List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
				if (intersections.isEmpty()) {
					_imageWriter.writePixel(i, j, _scene.getBackground().getColor());
				} else {
					GeoPoint closestPoint = getClosestPoint(intersections);
					_imageWriter.writePixel(i, j, calcColor(closestPoint, ray).getColor());
				}
			}
		}
	}

	private Color calcColor(GeoPoint closestPoint, Ray ray) {
		return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, 1).add(_scene.getAmbientLight().getIntensity());
	}

	/**
	 * prints a grid and in image
	 * 
	 * @param size
	 * @param opt
	 */
	public void printGrid(int size, java.awt.Color... opt) {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		int sizeX = nX % 2 == 0 ? nX / size : nX / size + 1, sizeY = nY % 2 == 0 ? nY / size : nY / size + 1;
		java.awt.Color _color = opt.length > 0 ? opt[0] : java.awt.Color.white;
		for (int i = 0; i < nX; i += sizeX) {
			for (int j = 0; j < nY; ++j) {
				_imageWriter.writePixel(i, j, _color);
				_imageWriter.writePixel(nX - 1, j, _color);
			}
		}
		for (int j = 0; j < nY; j += sizeY) {
			for (int i = 0; i < nX; ++i) {
				_imageWriter.writePixel(i, j, _color);
				_imageWriter.writePixel(i, nY - 1, _color);
			}
		}
	}

	/**
	 * gets a point and sets its color
	 * 
	 * @param intersection
	 * @return
	 */
	private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) {
		if (level == 0 || k <= 0.001) {
			return Color.BLACK;
		}
		Color color = new Color();
		color = color.add(intersection.geometry.getEmmission());
		Vector v = inRay.getVector();
		Vector n = intersection.geometry.getNormal(intersection.point);
		int nShininess = intersection.geometry.getMaterial().getNShininess();
		double kd = intersection.geometry.getMaterial().getKD();
		double ks = intersection.geometry.getMaterial().getKS();
		for (LightSource lightSource : _scene.getLights()) {
			Vector l = lightSource.getL(intersection.point);
			if (n.vectorsDotProduct(l) * n.vectorsDotProduct(v) > 0) {
				if (unshaded(l, n, intersection)) {
					Color lightIntensity = new Color(lightSource.getIntensity(intersection.point));
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}
		Color reflectedLight = new Color(), refractedLight = new Color();
		double kr = intersection.geometry.getMaterial().getKR();
		if (kr != 0) {
			Ray reflectedRay = constructReflectedRay(n, intersection.point, inRay);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null) {
				reflectedLight = calcColor(reflectedPoint, reflectedRay, level - 1, k * kr).scale(kr);
			}
		}
		double kt = intersection.geometry.getMaterial().getKT();
		if (kt != 0) {
			Ray refractedRay = constructRefractedRay(intersection.point, inRay);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null) {
				refractedLight = calcColor(refractedPoint, refractedRay, level - 1, k * kt).scale(kt);
			}
		}
		color = color.add(reflectedLight, refractedLight);
		return color;
	}

	private boolean unshaded(Vector l, Vector n, GeoPoint intersection) {
		Vector lightDirection = l.scale(-1);
		Vector epsVector = n.scale(n.vectorsDotProduct(lightDirection) > 0 ? EPS : -EPS);
		Point3D point = intersection.point.addVec(epsVector);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
		return intersections.isEmpty();
	}

	private GeoPoint findClosestIntersection(Ray ray) {
		Vector rayVec = ray.getVector();
		Vector epsVector = rayVec.scale(EPS);
		Point3D point = ray.getPoint().addVec(epsVector);
		Ray newRay = new Ray(point, rayVec);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(newRay);
		Point3D rayPnt = ray.getPoint();
		if (intersections.isEmpty()) {
			return null;
		}
		GeoPoint closestPoint = new GeoPoint(intersections.get(0));
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

	private Ray constructRefractedRay(Point3D point, Ray inRay) {
		return new Ray(point, inRay.getVector());
	}

	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = new Vector(inRay.getVector());
		return new Ray(point, v.vectorSub(n.scale(2 * v.vectorsDotProduct(n))));
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
		return new Color(lightIntensity.scale(kd * Math.abs(l.vectorsDotProduct(n))));
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
		return new Color(lightIntensity.scale(ks * Math.pow(Math.max(0, -1 * v.vectorsDotProduct(r)), nShininess)));
	}

	/**
	 * function gets a Point3D list and returns the closest point to camera
	 * 
	 * @param intersectionsPoints
	 * @return returns the closest point to camera
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> intersectionsPoints) {
		Point3D rayPnt = _scene.getCamera().getP0();
		GeoPoint closestPoint = new GeoPoint(intersectionsPoints.get(0));
		double minDistancePow = rayPnt.distancePow(closestPoint.point);
		double disPow;
		for (GeoPoint p : intersectionsPoints) {
			disPow = rayPnt.distancePow(p.point);
			if (disPow < minDistancePow) {
				minDistancePow = disPow;
				closestPoint = p;
			}
		}
		return closestPoint;
	}
}
