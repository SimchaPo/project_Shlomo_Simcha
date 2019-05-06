package geometries;

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
		if (trnglLst.isEmpty()) { // if ray dosn't cut plane return empty
			return EMPTY_LIST;
		}
		Point3D rayPnt = _ray.getPoint();
		Vector rayVec = _ray.getVector();
		Point3D trnglLstPnt = trnglLst.get(0);
		if (!(trnglLstPnt.equals(rayPnt))) { // if ray cuts plane and dosen't start at plane check if it cuts plane
												// inside triangle
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
		for (Point3D p : trianPoints) { // if ray starts at one of triangle corners return point
			if (rayPnt.equals(p)) {
				return trnglLst;
			}
		}
		trnglLst.clear();
		if (Util.isZero(normalVector.vectorsDotProduct(rayVec))) { // if ray is include in plane check if cuts triangle
			Plane ab = new Plane(trianPoints[0], trianPoints[1], trianPoints[0].addVec(normalVector));
			Plane bc = new Plane(trianPoints[1], trianPoints[2], trianPoints[1].addVec(normalVector));
			Plane ca = new Plane(trianPoints[2], trianPoints[0], trianPoints[2].addVec(normalVector));
			List<Point3D> pointsAB = ab.findIntersections(_ray);
			List<Point3D> pointsBC = bc.findIntersections(_ray);
			List<Point3D> pointsCA = ca.findIntersections(_ray);
			Point3D pnt0 = null, pnt1 = null, pnt2 = null;
			if (!pointsAB.isEmpty()) {
				pnt0 = pointsAB.get(0);
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
				pnt0 = pointsBC.get(0);
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
				pnt0 = pointsCA.get(0);
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
			if (pnt1 != null) { // if there is points that ray cuts triangle
				if (pnt1.equals(rayPnt) || pnt2 != null && pnt2.equals(rayPnt)) {
					trnglLst.add(rayPnt);
					return trnglLst;
				}
				if (pnt2 != null) {
					if (pnt2.subtract(rayPnt).length() < pnt1.subtract(rayPnt).length()) {
						trnglLst.add(pnt2);
					} else {
						trnglLst.add(pnt1);
					}
					return trnglLst;
				}
				for (Point3D p : trianPoints) {
					if (pnt1.equals(p)) {
						trnglLst.add(pnt1);
						return trnglLst;
					}
				}
				trnglLst.add(rayPnt);
			}
		} else { // if ray starts at plane but isn't include in plane
			trnglLst = findIntersections(new Ray(new Point3D(rayPnt.addVec(rayVec.scale(-1))), rayVec));
		}
		return trnglLst;
	}
}