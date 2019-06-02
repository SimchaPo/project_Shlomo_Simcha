package elements;

import static primitives.Util.alignZero;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * 
 * @author OWNER class for camera, camera sends rays to objects
 */
public class Camera {
	private Point3D _p0;
	private Vector _vUp;
	private Vector _vTo;
	private Vector _vRight;

	// Contractor
	public Camera(Point3D _pnt0, Vector _vecUp, Vector _vecTo) {
		_p0 = new Point3D(_pnt0);
		_vUp = _vecUp.normalize();
		_vTo = _vecTo.normalize();
		if (_vUp.vectorsDotProduct(_vTo) == 0) {
			_vRight = _vTo.vecotrsCrossProduct(_vUp).normalize();
		} else {
			throw new IllegalArgumentException("Error!!! The vectors vUp vTo is not ortogonal vectors!");
		}
	}

	// getters
	public Point3D getP0() {
		return this._p0;
	}

	public Vector getVUp() {
		return this._vUp;
	}

	public Vector getVTo() {
		return this._vTo;
	}

	public Vector getVRight() {
		return this._vRight;
	}

	/**
	 * This Function create ray from camera to screen
	 * 
	 * @param Nx             the amount of pixels on X axis
	 * @param Ny             the amount of pixels on Y axis
	 * @param i              number of pixels from left side of screen
	 * @param j              number of pixels from up side of screen
	 * @param screenDistance distance from camera to screen
	 * @param screenWidth
	 * @param screenHeight
	 * @return Ray
	 */
	public Ray constructRayThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth,
			double screenHeight) {
		Point3D pc = this._p0.addVec(_vTo.scale(screenDistance));// _Pc => center of the screen
		double ry = alignZero(screenHeight / Ny);//
		double rx = alignZero(screenWidth / Nx); // _Ry*_Rx the pixel area
		// pixel center calculation:
		// {
		double yj = alignZero((j - Ny / 2.0) * ry) + ry / 2.0;// offset by axis Y on screen
		double xi = alignZero((i - Nx / 2.0) * rx) + rx / 2.0;// offset by axis X on screen
		Point3D pij = pc;
		if (xi != 0)
			pij = pij.addVec(this._vRight.scale(xi)); // _Pij=> pixel center calculating
														// _Pij=_Pij+(_Xi*_vRight-_Yj*_vUp)
		if (yj != 0)
			pij = pij.addVec(this._vUp.scale(-yj));
		// }
		Vector _Vij = pij.subtract(this._p0).normalize();// _Vij vector from camera to pixel on the screen
		return new Ray(this._p0, _Vij);
	}
}
