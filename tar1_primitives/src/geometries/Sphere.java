package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
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
		//
		return (sphereCenter.subtract(pnt)).normalize();
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		List<Point3D> sphereLst = new ArrayList<Point3D>(null);
		Point3D rayPnt = _ray.getPoint();
		Vector rayVec = _ray.getVector();
		Point3D sphCntr = this.sphereCenter;
		Vector vecP_O = sphCntr.subtract(rayPnt);
		double rad = this._radius;
		double disP_O = vecP_O.length();
		double _tm = rayVec.vectorsDotProduct(vecP_O);// length of ray to the point meeting radius ortogonal
		// to the ray
		double d = Math.sqrt(((Math.pow(disP_O, 2) - Math.pow(_tm, 2)))); // distance from sphere center to the
																			// ray
		if (disP_O > rad) {
			if (d > rad) {
				return sphereLst;
			} else if (d == rad) {// then the ray tangent to the sphere
				Point3D p1 = rayPnt.addVec(rayVec.scale(_tm));
				sphereLst.add(0, p1);
				return sphereLst;
			} else {
				double th = Math.sqrt((Math.pow(rad, 2) - Math.pow(d, 2)));// distance from first ray<->sphere
																			// intersection point to the point
																			// meeting radius ortogonal to the
																			// ray
				Point3D p1 = rayPnt.addVec(rayVec.scale(_tm - th));
				Point3D p2 = rayPnt.addVec(rayVec.scale(_tm + th));
				sphereLst.add(0, p1);
				sphereLst.add(0, p2);
				return sphereLst;
			}
		} else if (disP_O == rad) {
			if (vecP_O.vectorsDotProduct(rayVec) != 0) {
				Point3D p1 = rayPnt.addVec(rayVec.scale(_tm + _tm));
			} else {
				return sphereLst;
			}
		} else if (disP_O < rad) {
			Point3D revRayPnt = rayPnt.addVec(rayVec);
			Vector revRayVec = revRayPnt.subtract(rayPnt);
			Vector revP_O = sphCntr.subtract(revRayPnt);
			double disRevP_O = revP_O.length();
			double rev_tm = revRayVec.vectorsDotProduct(revP_O);
			double revD = Math.sqrt(((Math.pow(disRevP_O, 2) - Math.pow(rev_tm, 2))));
			double th = Math.sqrt((Math.pow(rad, 2) - Math.pow(revD, 2)));
			Point3D p1 = revRayPnt.addVec(revRayVec.scale(rev_tm - th));
		}
	}

}
