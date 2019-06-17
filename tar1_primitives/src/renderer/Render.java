package renderer;

import java.util.ArrayList;
import java.util.List;
import elements.LightSource;
import geometries.Plane;
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

	private static final int MAX_CALC_COLOR_LEVEL = 15;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double EPS = 0.1;
	private Scene _scene;
	private ImageWriter _imageWriter;
	private Plane focalPlane;
	private Point3D p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;

	/********** constructor **********/
	public Render(Scene s, ImageWriter im) {
		_scene = s;
		_imageWriter = im;
		p0 = _scene.getCamera().getP0();
		vTo = _scene.getCamera().getVTo();
		vUp = _scene.getCamera().getVUp();
		vRight = _scene.getCamera().getVRight();
		if (_scene.isFocus()) {
			focalPlane = new Plane(p0.addVec(vTo.scale(_scene.getFocusDistance())), vTo);
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
	 * get Imege writer
	 * 
	 * @return
	 */
	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	/**
	 * function colors each point in the image according to the intersections
	 */
	public void renderImage() {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		double screenDis = _scene.getScreenDistance(), imageWidth = _imageWriter.getWidth(),
				imageHeigt = _imageWriter.getHeight();
		double ry = imageHeigt / (2 * nY), rx = imageWidth / (2 * nX);
		Point3D pij;
		for (int i = 0; i < nX; ++i) {
			for (int j = 0; j < nY; ++j) {
				pij = _scene.getCamera().getPixelCenter(nX, nY, i, j, screenDis, imageWidth, imageHeigt);
				_imageWriter.writePixel(i, j, calcColor(pij, ry, rx).getColor());
			}
		}
	}

	/**
	 * gets color of point
	 * 
	 * @param ray
	 * @return
	 */
	private Color getPointColor(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? _scene.getBackground() : calcColor(closestPoint, ray);
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
			if (n.vectorsDotProduct(l) * nDotV > 0) {
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
	 * calculates the amaunt of shaddow at point
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
		Vector r = v.vectorSub(n.scale(2 * v.vectorsDotProduct(n)));
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
	 * calculate color with average of random rays
	 * 
	 * @param pij
	 * @param ry
	 * @param rx
	 * @return
	 */
	private Color calcColor(Point3D pij, double ry, double rx) {
		int r = 0, g = 0, b = 0;
		List<Point3D> pixelPoints = getRandomPoints(pij, ry, rx);
		Color col = new Color();
		int len = pixelPoints.size();
		for (Point3D pnt : pixelPoints) {
			col = _scene.isFocus() ? calcColorFocus(pnt) : getPointColor(new Ray(p0, new Vector(pnt.subtract(p0))));
			r += col.getColor().getRed();
			g += col.getColor().getGreen();
			b += col.getColor().getBlue();
		}
		return new Color(r / len, g / len, b / len);
	}

	/**
	 * calculate color with focus
	 * 
	 * @param points
	 * @param focusPoint
	 * @return
	 */
	private Color calcColorFocus(Point3D pnt) {
		double r = 0, g = 0, b = 0;
		Color col = new Color();
		double apertureRad = _scene.getApertureRadius();
		List<Point3D> aperturePoints = getRandomPoints(p0, apertureRad, apertureRad);
		Point3D focalPoint = focalPlane.findIntersections(new Ray(p0, pnt.subtract(p0))).get(0).point;
		for (Point3D point : aperturePoints) {
			col = getPointColor(new Ray(point, new Vector(focalPoint.subtract(point))));
			r += col.getColor().getRed();
			g += col.getColor().getGreen();
			b += col.getColor().getBlue();
		}
		int len = aperturePoints.size();
		return new Color(r / len, g / len, b / len);
	}

	/**
	 * get list of randomize points around point
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private List<Point3D> getRandomPoints(Point3D centerPnt, double yRad, double xRad) {
		List<Point3D> points = new ArrayList<Point3D>();
		points.add(centerPnt);
		double r1, r2;
		Point3D pntToAdd;
		for (int t = 0; t < 5; ++t) {
			r1 = Math.random() * yRad;
			r2 = Math.random() * xRad;
			pntToAdd = r1 == 0 ? new Point3D(centerPnt) : centerPnt.addVec(vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(r2));
			points.add(pntToAdd);
			r1 = Math.random() * yRad;
			r2 = Math.random() * xRad;
			pntToAdd = r1 == 0 ? new Point3D(centerPnt) : centerPnt.addVec(vUp.scale(-r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(-r2));
			points.add(pntToAdd);
			r1 = Math.random() * yRad;
			r2 = Math.random() * xRad;
			pntToAdd = r1 == 0 ? new Point3D(centerPnt) : centerPnt.addVec(vUp.scale(-r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(r2));
			points.add(pntToAdd);
			r1 = Math.random() * yRad;
			r2 = Math.random() * xRad;
			pntToAdd = r1 == 0 ? new Point3D(centerPnt) : centerPnt.addVec(vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(-r2));
			points.add(pntToAdd);
		}
		return points;
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
