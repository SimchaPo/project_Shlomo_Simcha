package primitives;

import primitives.Vector;

/**
 * the Class define the ray in 3d space
 */
public class Ray {
	private Vector vector;
	private Point3D point;

	/**
	 * Constructor
	 * 
	 * @param _pnt
	 * @param _vec
	 */
	public Ray(Point3D _pnt, Vector _vec) {
		point = new Point3D(_pnt);
		vector = _vec.normalize();
	}

	/**
	 * copy Constructor
	 * 
	 * @param _otherRay
	 */
	public Ray(Ray _otherRay) {
		this(_otherRay.point, _otherRay.vector);
	}

	/**
	 * 
	 * @return ray vector !!!DON`T CHANGE!!!
	 */
	public Vector getVector() {
		return vector;
	}

	/**
	 * 
	 * @return ray point !!!DON`T CHANGE!!!
	 */
	public Point3D getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "ray: " + point.toString() + vector.toString();
	}
}
