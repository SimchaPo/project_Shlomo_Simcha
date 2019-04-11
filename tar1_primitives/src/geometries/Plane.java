package geometries;
/**
 * this class define the plane in the 3D coordinate system
 * @author meerz
 *
 */

import primitives.Point3D;

import primitives.Vector;
/**
 * This Class define Plane in 3d space
 * @author OWNER
 *Plane is build by point and normal
 */
public class Plane implements Geometry {
	protected Point3D point;
	protected Vector normalVector;

	/********** Constructors ***********/
	/**
	 * Constructor with Point3D and Vector
	 * @param _pnt
	 * @param _vec
	 */
	public Plane(Point3D _pnt, Vector _vec) {
		point =new Point3D(_pnt);
		normalVector = _vec.normalize();
	}
/**
 * constructor receive three point3D
 * @param pnt1
 * @param pnt2
 * @param pnt3
 */
	public Plane(Point3D pnt1, Point3D pnt2, Point3D pnt3) {
		this(pnt1, (pnt1.subtract(pnt2)).vectrsCrossProduct(pnt2.subtract(pnt3)).normalize());
	}
	
	/*************** Admin *****************/
	@Override
	public Vector getNormal(Point3D pnt) {
		return normalVector;
	}

}
