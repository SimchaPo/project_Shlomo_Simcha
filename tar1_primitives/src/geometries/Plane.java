package geometries;
/**
 * this class define the plane in the 3D coordinate system
 * @author meerz
 *
 */

import primitives.Point3D;

import primitives.Vector;
/**
 * Plane is build by point and normal
 * @author OWNER
 *
 */
public class Plane implements Geometry {
	Point3D planePoint;
	Vector normalPlaneVector;

	/********** Constructors ***********/
	/**
	 * Constructor with Point3D and Vector
	 * @param _pnt
	 * @param _vec
	 */
	public Plane(Point3D _pnt, Vector _vec) {
		planePoint = _pnt;
		normalPlaneVector = _vec;
	}

	public Plane(Point3D pnt1, Point3D pnt2, Point3D pnt3) {
		this(pnt1, (pnt1.subtract(pnt2)).vectrsCrossProduct(pnt2.subtract(pnt3)).vectorUnit());
	}
	
	/*************** Admin *****************/
	@Override
	public Vector getNormal(Point3D pnt) {
		return normalPlaneVector;
	}

//	public void isOnPlane(Plane _plane) {
//		double xPlnpoint=_plane.planePoint.
//	}
}
