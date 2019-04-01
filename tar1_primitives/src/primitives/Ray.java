package primitives;

import primitives.Vector;

public class Ray {
	Vector rayVector;
	Point3D rayPoint;
/**
 * Constructor
 * @param _pnt
 * @param _vec
 */
	public Ray(Point3D _pnt, Vector _vec) {
		rayPoint = _pnt;
		rayVector = _vec;
	}

	/**
	 * copy Constructor
	 * @param _otherRay
	 */
	public Ray(Ray _otherRay) {
		this(_otherRay.rayPoint, _otherRay.rayVector);
	}
}
