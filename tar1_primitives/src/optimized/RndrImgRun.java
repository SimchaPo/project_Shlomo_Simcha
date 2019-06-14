package optimized;

import java.util.ArrayList;
import java.util.List;

import elements.LightSource;
import geometries.Plane;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;
import scene.Scene;

public class RndrImgRun implements Runnable {
	private static final int MAX_CALC_COLOR_LEVEL = 15;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double EPS = 0.1;
	private int _nxStartIndx = 0, _nyStartIndx = 0;
	private int _nxStopIndx = 0, _nyStopIndx = 0;
	private ImageWriter _imW;
	private Scene _sc;
	private Plane focalPlane;
	private Point3D p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;

	public RndrImgRun(int nxStart, int nyStart, int nxStop, int nyStop, Scene s, ImageWriter im) {
		_imW = im;
		_sc = s;
		p0 = _sc.getCamera().getP0();
		vTo = _sc.getCamera().getVTo();
		vUp = _sc.getCamera().getVUp();
		vRight = _sc.getCamera().getVRight();
		if (_sc.isFocus()) {
			focalPlane = new Plane(p0.addVec(vTo.scale(_sc.getFocusDistance())), vTo);
		}
		_nxStartIndx = nxStart;
		_nxStopIndx = nxStop;
		_nyStartIndx = nyStart;
		_nyStopIndx = nyStop;
	}

	private double transparency(Vector l, Vector n, GeoPoint intersection) {
		Vector lightDirection = l.scale(-1);
		Vector epsVector = n.scale(n.vectorsDotProduct(lightDirection) > 0 ? EPS : -EPS);
		Point3D point = intersection.point.addVec(epsVector);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = _sc.getGeometries().findIntersections(lightRay);
		double ktr = 1;
		for (GeoPoint gp : intersections) {
			ktr *= gp.geometry.getMaterial().getKT();
		}
		return ktr;
	}

	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.vectorSub(n.scale(2 * (l.vectorsDotProduct(n)))).normalize();
		return new Color(lightIntensity.scale(ks * Math.pow(Math.max(0, -1 * v.vectorsDotProduct(r)), nShininess)));
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
	 * calls the calcColor function with default arguments
	 * 
	 * @param closestPoint
	 * @param ray
	 * @return
	 */
	private Color calcColor(GeoPoint closestPoint, Ray ray) {
		return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, 1).add(_sc.getAmbientLight().getIntensity());
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
		for (LightSource lightSource : _sc.getLights()) {
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
	 * finds closest intersection to ray point
	 * 
	 * @param ray
	 * @return
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = _sc.getGeometries().findIntersections(ray);
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

	public java.awt.Color getPointColor(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? _sc.getBackground().getColor() : calcColor(closestPoint, ray).getColor();
	}

	protected java.awt.Color calcColorFocus(Ray ray) {
		int r = 0, g = 0, b = 0;
		java.awt.Color col = new java.awt.Color(0, 0, 0);
		List<Point3D> points = getPointsInAperture(p0);
		int len = points.size();
		Point3D focalPoint = focalPlane.findIntersections(ray).get(0).point;
		for (Point3D pnt : points) {
			col = getPointColor(new Ray(pnt, new Vector(focalPoint.subtract(pnt))));
			r += col.getRed();
			g += col.getGreen();
			b += col.getBlue();
		}
		return new java.awt.Color(r / len, g / len, b / len);
	}

	/**
	 * get list of randomize points around eye
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<Point3D> getPointsInAperture(Point3D pnt) {
		List<Point3D> points = new ArrayList<Point3D>();
		points.add(pnt);
		double r1, r2;
		double apertureRadP = _sc.getApertureRadius();
		double apertureRadM = -apertureRadP;
		Point3D pntToAdd;
		for (int t = 0; t < 5; ++t) {
			r1 = Math.random() * apertureRadP;
			r2 = Math.random() * apertureRadP;
			pntToAdd = r1 == 0 ? new Point3D(pnt) : pnt.addVec(vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(r2));
			points.add(pntToAdd);
			r1 = Math.random() * apertureRadM;
			r2 = Math.random() * apertureRadM;
			pntToAdd = r1 == 0 ? new Point3D(pnt) : pnt.addVec(vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(r2));
			points.add(pntToAdd);
			r1 = Math.random() * apertureRadP;
			r2 = Math.random() * apertureRadM;
			pntToAdd = r1 == 0 ? new Point3D(pnt) : pnt.addVec(vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(r2));
			points.add(pntToAdd);
			r1 = Math.random() * apertureRadM;
			r2 = Math.random() * apertureRadP;
			pntToAdd = r1 == 0 ? new Point3D(pnt) : pnt.addVec(vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pntToAdd.addVec(vRight.scale(r2));
			points.add(pntToAdd);
		}
		System.out.println(points);
		return points;
	}

	public void run() {
		int nX = _imW.getNx();
		int nY = _imW.getNy();
		boolean focus = _sc.isFocus();
		for (int i = _nxStartIndx; i < _nxStopIndx; ++i) {
			for (int j = _nyStartIndx; j < _nyStopIndx; ++j) {
				Ray ray = _sc.getCamera().constructRayThroughPixel(nX, nY, i, j, _sc.getScreenDistance(),
						_imW.getWidth(), _imW.getHeight());
				_imW.writePixel(i, j, focus ? calcColorFocus(ray) : getPointColor(ray));
			}
		}
	}
}