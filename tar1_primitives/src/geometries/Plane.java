package geometries;
/**
 * this class define the plane in the 3D coordinate system
 * @author meerz
 *
 */

import primitives.Point3D;
import primitives.Vector;

public class Plane {
	Point3D planePoint;
	Vector planeVector;
/**
 * Constructor
 * @param _pnt
 * @param _vec
 */
	public Plane(Point3D _pnt, Vector _vec) {
		planePoint = _pnt;
		planeVector = _vec;
	}
/**
 * copy Constructor
 * @param _otherPlane
 */
	public Plane(Plane _otherPlane) {
		this(_otherPlane.planePoint, _otherPlane.planeVector);
	}

//	public void isOnPlane(Plane _plane) {
//		double xPlnpoint=_plane.planePoint.
//	}
}
