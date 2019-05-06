package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import java.lang.Math;

/**
 * This class define geometric 3D figure "Sphere"
 * 
 * @author OWNER
 *
 */
public class Sphere extends RadialGeometry {
	protected Point3D sphereCenter;

	/**
	 * constructor for Sphere
	 * 
	 * @param _pnt
	 * @param rad
	 */
	public Sphere(Point3D _pnt, double rad) {
		super(rad);
		sphereCenter = _pnt;
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		return (pnt.subtract(sphereCenter)).normalize();
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		List<Point3D> sphereLst = new ArrayList<Point3D>();
		Point3D rayPnt = _ray.getPoint();
		Vector rayVec = _ray.getVector();
		if (rayPnt.equals(sphereCenter)) {
			sphereLst.add(sphereCenter.addVec(rayVec.scale(_radius)));
			return sphereLst;
		}
		Vector vecP_O = sphereCenter.subtract(rayPnt);
		double disP_O = vecP_O.length();
		double _tm = rayVec.vectorsDotProduct(vecP_O);// length of ray to the point meeting radius orthogonal
		// to the ray
		double powD = Util.uscale(disP_O, disP_O) - Util.uscale(_tm, _tm);
		double d = Math.sqrt(powD); // distance from sphere center to the ray
		if (d > _radius) {
			return sphereLst;
		}
		double _th = Math.sqrt(Util.uscale(_radius, _radius) - powD);
		double t1 = _tm + _th;
		double t2 = _tm - _th;
		if (t2 >= 0) {
			if (Util.isZero(t2)) {
				sphereLst.add(rayPnt);
			} else {
				sphereLst.add(rayPnt.addVec(rayVec.scale(t2)));
			}
			sphereLst.add(rayPnt.addVec(rayVec.scale(t1)));
		} else if (t1 >= 0) {
			if (Util.isZero(t1)) {
				sphereLst.add(rayPnt);
			} else {
				sphereLst.add(rayPnt.addVec(rayVec.scale(t1)));
			}
		}
		return sphereLst;
	}
}
