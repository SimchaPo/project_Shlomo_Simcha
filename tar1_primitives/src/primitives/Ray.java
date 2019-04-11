package primitives;

//import java.io.ObjectInputStream.GetField;

import primitives.Vector;

public class Ray {
	protected Vector rayVector;
	protected Point3D rayPoint;
/**
 * Constructor
 * @param _pnt
 * @param _vec
 */
	public Ray(Point3D _pnt, Vector _vec) {
		rayPoint = new Point3D(_pnt);
		rayVector = _vec.vectorUnit();
	}

	/**
	 * copy Constructor
	 * @param _otherRay
	 */
	public Ray(Ray _otherRay) {
		this(_otherRay.rayPoint, _otherRay.rayVector);
	}
	
	public Vector getRayVector() {
		return rayVector;
	}
	
	public Point3D getRayPoint() {
		return rayPoint;
	}
}
