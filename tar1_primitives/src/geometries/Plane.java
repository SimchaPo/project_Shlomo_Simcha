package geometries;
/**
 * this class define the plane in the 3D coordinate system
 * @author meerz
 *
 */

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * This Class define Plane in 3d space Plane is build by point and normal
 * 
 * @author OWNER
 */
public class Plane implements Geometry {
	protected Point3D point;
	protected Vector normalVector;

	/********** Constructors ***********/
	/**
	 * Constructor with Point3D and Vector
	 * 
	 * @param _pnt
	 * @param _vec
	 */
	public Plane(Point3D _pnt, Vector _vec) {
		point = new Point3D(_pnt);
		normalVector = _vec.normalize();
	}

	/**
	 * constructor receive three point3D
	 * 
	 * @param pnt1
	 * @param pnt2
	 * @param pnt3
	 */
	public Plane(Point3D pnt1, Point3D pnt2, Point3D pnt3) {
		this(pnt1, (pnt1.subtract(pnt2)).vecotrsCrossProduct(pnt2.subtract(pnt3)).normalize());
	}

	/*************** Admin *****************/
	@Override
	public Vector getNormal(Point3D pnt) {
		return normalVector;
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		List<Point3D> planeLst = new ArrayList<Point3D>();
		Point3D rayPnt = _ray.getPoint();
		Vector rayVec = _ray.getVector();
		if (rayPnt.equals(point)) {
			planeLst.add(point);
			return planeLst;
		}
		Vector pq = point.subtract(rayPnt);
		double pqProDotNormal = pq.vectorsDotProduct(normalVector);
		if(Util.isZero(pqProDotNormal)) {
			planeLst.add(rayPnt);
			return planeLst;
		}
		double rayVecDotProNormal = rayVec.vectorsDotProduct(normalVector);
		if (Util.isZero(rayVecDotProNormal)) {
			return planeLst;
		}
		double t = Util.uscale(pqProDotNormal, 1 / rayVecDotProNormal);
		if (t > 0) {
			planeLst.add(rayPnt.addVec(rayVec.scale(t)));
		} else if (t == 0) {
			planeLst.add(rayPnt);
		}
		return planeLst;
	}

}
