package geometries;

import java.util.ArrayList;
import java.util.List;

//import java.lang.reflect.Constructor;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * This Class define the triangle on plane extends the Plain Class
 * 
 * @author meerz
 *
 */
public class Triangle extends Plane {

	private Point3D trianPoints[] = new Point3D[3];

	/**
	 * Constructor
	 * 
	 * @param _pnt1
	 * @param _pnt2
	 * @param _pnt3
	 */
	public Triangle(Point3D _pnt1, Point3D _pnt2, Point3D _pnt3) {
		super(_pnt1, _pnt2, _pnt3);
		trianPoints[0] = _pnt1;
		trianPoints[1] = _pnt2;
		trianPoints[2] = _pnt3;
	}

	/**
	 * Getter
	 * 
	 * @return the array of Triangle points
	 */
	public Point3D[] getTrianPoints() {
		return trianPoints;
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		List<Point3D> trnglLst = super.findIntersections(_ray);
		if (trnglLst.isEmpty()) {
			return trnglLst;
		}
		Point3D rayPnt = _ray.getPoint();
		Vector rayVec = _ray.getVector();
		Point3D trnglLstPnt = trnglLst.get(0);
		if (!(trnglLstPnt.equals(rayPnt))) {
			Vector v1 = trianPoints[0].subtract(rayPnt);
			Vector v2 = trianPoints[1].subtract(rayPnt);
			Vector v3 = trianPoints[2].subtract(rayPnt);
			Vector N1 = (v1.vecotrsCrossProduct(v2)).normalize();
			Vector N2 = (v2.vecotrsCrossProduct(v3)).normalize();
			Vector N3 = (v3.vecotrsCrossProduct(v1)).normalize();
			Vector _p_p0 = trnglLstPnt.subtract(rayPnt);
			double d1, d2, d3;
			d1 = _p_p0.vectorsDotProduct(N1);
			d2 = _p_p0.vectorsDotProduct(N2);
			d3 = _p_p0.vectorsDotProduct(N3);
			double s1, s2, s3;
			s1 = Math.signum(d1);
			s2 = Math.signum(d2);
			s3 = Math.signum(d3);
			if (!(s1 <= 0 && s2 <= 0 && s3 <= 0 || s1 >= 0 && s2 >= 0 && s3 >= 0)) {
				return EMPTY_LIST;
			}
			return trnglLst;
		}
		for (Point3D p : trianPoints) {
			if (rayPnt.equals(p)) {
				return trnglLst;
			}
		}
		if (Util.isZero(normalVector.vectorsDotProduct(rayVec))) { // case ray is include in plane
			trnglLst.clear();
			Plane ab = new Plane(trianPoints[0], trianPoints[1], trianPoints[0].addVec(normalVector));
			Plane bc = new Plane(trianPoints[1], trianPoints[2], trianPoints[1].addVec(normalVector));
			Plane ca = new Plane(trianPoints[2], trianPoints[0], trianPoints[2].addVec(normalVector));
			List<Point3D> pointsAB = ab.findIntersections(_ray);
			List<Point3D> pointsBC = bc.findIntersections(_ray);
			List<Point3D> pointsCA = ca.findIntersections(_ray);
			Point3D pnt0 = null, pnt1 = null, pnt2 = null;
			if (!pointsAB.isEmpty()) {
				pnt0 = new Point3D(pointsAB.get(0));
				for (Point3D p : trianPoints) {
					if (pnt0.equals(p)) {
						pnt1 = new Point3D(pnt0);
					}
				}
				if (!pnt0.equals(pnt1)) {
					if (Util.isZero(trianPoints[0].subtract(pnt0).length() + trianPoints[1].subtract(pnt0).length()
							- trianPoints[0].subtract(trianPoints[1]).length())) {
						pnt1 = new Point3D(pnt0);
					}
				}
			}
			if (!pointsBC.isEmpty()) {
				pnt0 = new Point3D(pointsBC.get(0));
				for (Point3D p : trianPoints) {
					if (pnt0.equals(p)) {
						if (pnt1 == null) {
							pnt1 = new Point3D(pnt0);
						} else {
							pnt2 = new Point3D(pnt0);
						}
					}
				}
				if (!pnt0.equals(pnt1) && !pnt0.equals(pnt2)) {
					if (Util.isZero((trianPoints[1].subtract(pnt0).length() + trianPoints[2].subtract(pnt0).length())
							- trianPoints[1].subtract(trianPoints[2]).length())) {
						if (pnt1 == null) {
							pnt1 = new Point3D(pnt0);
						} else {
							pnt2 = new Point3D(pnt0);
						}
					}
				}
			}
			if (!pointsCA.isEmpty()) {
				pnt0 = new Point3D(pointsCA.get(0));
				for (Point3D p : trianPoints) {
					if (pnt0.equals(p)) {
						if (pnt1 == null) {
							pnt1 = new Point3D(pnt0);
						} else {
							pnt2 = new Point3D(pnt0);
						}
					}
				}
				if (!pnt0.equals(pnt1) && !pnt0.equals(pnt2)) {
					if (Util.isZero(trianPoints[2].subtract(pnt0).length() + trianPoints[0].subtract(pnt0).length()
							- trianPoints[2].subtract(trianPoints[0]).length())) {
						if (pnt1 == null) {
							pnt1 = new Point3D(pnt0);
						} else {
							pnt2 = new Point3D(pnt0);
						}
					}
				}
			}
			if (pnt1 != null && pnt2 != null) {

				if (pnt1.subtract(rayPnt).length() < pnt2.subtract(rayPnt).length()) {
					trnglLst.add(pnt1);
				} else {
					trnglLst.add(pnt2);
				}
			} else if (pnt1 != null) {

				trnglLst.add(pnt1);
			}
		}
		return trnglLst;
	}
	/*
	 * @Override public List<Point3D> findIntersections(Ray _ray) { List<Point3D>
	 * trnglLst = super.findIntersections(_ray); Point3D p = trnglLst.get(0);
	 * Point3D p0 = _ray.getPoint(); if (p.equals(p0)) { Ray newRay = new
	 * Ray(p0.addVec(_ray.getVector().scale(-0.00001)), _ray.getVector()); return
	 * findIntersections(newRay); } try { Vector v1 = trianPoints[0].subtract(p0);
	 * Vector v2 = trianPoints[1].subtract(p0); Vector v3 =
	 * trianPoints[2].subtract(p0); Vector N1 =
	 * v1.vecotrsCrossProduct(v2).normalize(); Vector N2 =
	 * v2.vecotrsCrossProduct(v3).normalize(); Vector N3 =
	 * v3.vecotrsCrossProduct(v1).normalize(); double num1 =
	 * p.subtract(p0).vectorsDotProduct(N1); double num2 =
	 * p.subtract(p0).vectorsDotProduct(N2); double num3 =
	 * p.subtract(p0).vectorsDotProduct(N3); if (Util.isZero(num1) ||
	 * Util.isZero(num2) || Util.isZero(num1)) { return EMPTY_LIST; } if ((num1 > 0
	 * && num2 > 0 && num3 > 0) || (num1 < 0 && num2 < 0 && num3 < 0)) { return
	 * trnglLst; } } catch (NumberFormatException e) { return EMPTY_LIST; } return
	 * EMPTY_LIST; }
	 */

	/*
	 * public List<Point3D> findIntersections(Ray parm) { List<Point3D> list =
	 * super.findIntersections(parm); // Get intersections with plane. Point3D
	 * startOfRayPoint = parm.getPoint(); System.out.println(list); if
	 * (list.isEmpty()) { System.out.println("0"); return list; } Point3D currPoint
	 * = list.get(0); // If the intersection point is one from triangle points. if
	 * (currPoint.equals(trianPoints[0]) || currPoint.equals(trianPoints[1]) ||
	 * currPoint.equals(trianPoints[2])) { System.out.println("1"); return list; }
	 * // Create direction points between triangle points and currPoint Vector u1 =
	 * trianPoints[0].subtract(currPoint).normalize(); Vector u2 =
	 * trianPoints[1].subtract(currPoint).normalize(); Vector u3 =
	 * trianPoints[2].subtract(currPoint).normalize(); Vector n1 = null, n2 = null,
	 * n3 = null; try { n1 = (u1.vecotrsCrossProduct(u2)).normalize();
	 * System.out.println("3"); } catch (IllegalArgumentException e) { // If
	 * currPint is on the line A - B if ((u1.equals(u2.scale(-1)))) {
	 * System.out.println("4"); return list; } else { System.out.println("5");
	 * return EMPTY_LIST; } } try { n2 = (u2.vecotrsCrossProduct(u3)).normalize();
	 * System.out.println("6"); } catch (IllegalArgumentException e) { // If
	 * currPint is on the line B - C if ((u2.equals(u3.scale(-1)))) {
	 * System.out.println("7"); return list; } else { System.out.println("8");
	 * return EMPTY_LIST; } } try { n3 = (u3.vecotrsCrossProduct(u1)).normalize();
	 * System.out.println("9"); } catch (IllegalArgumentException e) { // If
	 * currPint is on the line A - C if ((u1.equals(u3.scale(-1)))) {
	 * System.out.println("10"); return list; } else { System.out.println("11");
	 * return EMPTY_LIST; } } Vector directionToPVector = null; try {
	 * directionToPVector = currPoint.subtract(startOfRayPoint);
	 * System.out.println("12"); } catch (IllegalArgumentException e) { // If P0 is
	 * P Ray ray = new Ray(parm.getPoint().addVec(parm.getVector().scale(-0.00001)),
	 * parm.getVector()); System.out.println(ray.getPoint() + " " +
	 * ray.getVector()); return findIntersections(ray); } double a =
	 * n1.vectorsDotProduct(directionToPVector), b =
	 * n2.vectorsDotProduct(directionToPVector), c =
	 * n3.vectorsDotProduct(directionToPVector); // If all with the same sign if ((a
	 * > 0 && b > 0 && c > 0) || (a < 0 && b < 0 && c < 0)) {
	 * System.out.println("14"); return list; } System.out.println("15"); return
	 * EMPTY_LIST; }
	 */
}