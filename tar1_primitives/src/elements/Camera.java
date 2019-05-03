package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {
	private Point3D _p0;
	private Vector _vUp;
	private Vector _vTo;
	private Vector _vRight;

	public Camera(Point3D _pnt0, Vector _vecUp, Vector _vecTo) {
		_p0 = _pnt0;
		_vUp = _vecUp.normalize();
		_vTo = _vecTo.normalize();
		if (_vUp.vectorsDotProduct(_vTo) == 0) {
			_vRight = _vTo.vectrsCrossProduct(_vUp).normalize();
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
		Point3D _Pc = this._p0.addVec(_vTo.scale(screenDistance));// _Pc => center of the screen
		double _Ry = screenHeight / Ny;//
		double _Rx = screenWidth / Nx; // _Ry*_Rx the pixel area
		// pixel center calculation:
		// {
		double _Yj = (j - Ny / 2) * _Ry + _Ry / 2;// offset by axis Y on screen
		double _Xi = (i - Nx / 2) * _Rx + _Rx / 2;// offset by axis X on screen
		Point3D _Pij = _Pc;
		if (_Xi != 0)
			_Pij = _Pij.addVec(this._vRight.scale(_Xi)); // _Pij=> pixel center calculating
															// _Pij=_Pij+(_Xi*_vRight-_Yj*_vUp)
		if (_Yj != 0)
			_Pij = _Pij.addVec(this._vUp.scale(-_Yj));
		// }
		Vector _Vij = _Pij.subtract(this._p0);// _Vij vector from camera to pixel on the screen
		Ray RayThroughPixel = new Ray(this._p0, _Vij);
		return RayThroughPixel;
	}

}
