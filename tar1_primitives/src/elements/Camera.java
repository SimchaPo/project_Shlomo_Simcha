//Simcha Podolsky 311215149
//Shlomo Meirzon

package elements;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * 
 * @author OWNER class for camera, camera sends rays to objects
 */
public class Camera {
	private static final int MAX_SUM_OF_RANDOM_POINTS = 20;
	private Point3D _p0;
	private Vector _vUp;
	private Vector _vTo;
	private Vector _vRight;
	private boolean _focus = true;
	private double _focusDistance;
	private double _apertureSize;

	/**
	 * Constructor
	 * 
	 * @param pnt0
	 * @param vecUp
	 * @param vecTo
	 */
	public Camera(Point3D pnt0, Vector vecUp, Vector vecTo) {
		this(pnt0, vecUp, vecTo, 0, 0);
		_focus = false;
	}

	/**
	 * constructor with depth of field
	 * 
	 * @param _pnt0
	 * @param _vecUp
	 * @param _vecTo
	 * @param focalDistance
	 * @param apertureSize
	 */
	public Camera(Point3D _pnt0, Vector _vecUp, Vector _vecTo, double focalDistance, double apertureSize) {
		_p0 = new Point3D(_pnt0);
		_vUp = _vecUp.normalize();
		_vTo = _vecTo.normalize();
		if (_vUp.vectorsDotProduct(_vTo) == 0) {
			_vRight = _vTo.vecotrsCrossProduct(_vUp).normalize();
		} else {
			throw new IllegalArgumentException("Error!!! The vectors vUp vTo is not ortogonal vectors!");
		}
		_focusDistance = focalDistance;
		_apertureSize = apertureSize;
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
		return new Ray(_p0, getPixelCenter(Nx, Ny, i, j, screenDistance, screenWidth, screenHeight).subtract(_p0));
	}

	/**
	 * get a center of pixel
	 * 
	 * @param Nx
	 * @param Ny
	 * @param i
	 * @param j
	 * @param screenDistance
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public Point3D getPixelCenter(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth,
			double screenHeight) {
		Point3D pij = getPixelCorner(Nx, Ny, i, j, screenDistance, screenWidth, screenHeight);
		double yToAdd = alignZero(screenHeight / Ny) / 2.0;
		double xToAdd = alignZero(screenWidth / Nx) / 2.0;
		if (xToAdd != 0)
			pij = pij.addVec(this._vRight.scale(xToAdd));
		if (yToAdd != 0)
			pij = pij.addVec(this._vUp.scale(-yToAdd));
		return pij;
	}

	/**
	 * get pixel top left corner
	 * 
	 * @param Nx
	 * @param Ny
	 * @param i
	 * @param j
	 * @param screenDistance
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public Point3D getPixelCorner(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth,
			double screenHeight) {
		double ry = alignZero(screenHeight / Ny);//
		double rx = alignZero(screenWidth / Nx); // _Ry*_Rx the pixel area
		// pixel center calculation:
		// {
		double yj = alignZero((j - Ny / 2.0) * ry);// offset by axis Y on screen
		double xi = alignZero((i - Nx / 2.0) * rx);// offset by axis X on screen
		Point3D pijCorner = _p0.addVec(_vTo.scale(screenDistance));
		;
		if (xi != 0)
			pijCorner = pijCorner.addVec(this._vRight.scale(xi)); // _Pij=> pixel center calculating
		// _Pij=_Pij+(_Xi*_vRight-_Yj*_vUp)
		if (yj != 0)
			pijCorner = pijCorner.addVec(this._vUp.scale(-yj));
		// }
		return pijCorner;
	}

	/**
	 * get list of randomize rays around pixel center
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<Ray> getApertureRandomRays(Point3D pij, Point3D focalPoint) {
		List<Ray> rays = new ArrayList<Ray>();
		rays.add(new Ray(pij, focalPoint.subtract(pij)));
		double rand;
		Point3D pntToAdd;
		for (int i = 0; i < MAX_SUM_OF_RANDOM_POINTS; ++i) {
			rand = (Math.random() - 0.5) * _apertureSize;
			if (!isZero(rand))
				pntToAdd = pij.addVec(_vUp.scale(rand));
			else
				pntToAdd = pij;
			rand = (Math.random() - 0.5) * _apertureSize;
			if (!isZero(rand))
				pntToAdd = pntToAdd.addVec(_vRight.scale(rand));
			rays.add(new Ray(pntToAdd, focalPoint.subtract(pntToAdd)));
		}
		return rays;
	}

	/**
	 * returns rays to all matrix points on pixel
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<Ray> getAllPixelRays(Point3D pijCorner, int matrixSize, double x, double y) {
		List<Ray> rays = new ArrayList<Ray>();
		Point3D pntToAdd = pijCorner;
		for (int i = 0; i < matrixSize; ++i) {
			if (i == 0)
				rays.add(new Ray(_p0, pntToAdd.subtract(_p0)));
			else
				rays.add(new Ray(_p0, pntToAdd.addVec(_vUp.scale(i * y)).subtract(_p0)));
			for (int j = 1; j < matrixSize; ++j) {
				if (i == 0)
					rays.add(new Ray(_p0, pntToAdd.addVec(_vRight.scale(j * x)).subtract(_p0)));
				else
					rays.add(new Ray(_p0,
							pntToAdd.addVec(_vRight.scale(j * x)).addVec(_vUp.scale(i * y)).subtract(_p0)));
			}
		}
		return rays;
	}

	/**
	 * gets indexes of points on pixel matrix and returns rays
	 * 
	 * @param pijCorner
	 * @param matrixSize
	 * @param p1
	 * @param x
	 * @param y
	 * @param indexes
	 * @return
	 */
	public List<Ray> getPointsPixelRays(Point3D pijCorner, int matrixSize, double x, double y, int... indexes) {
		List<Ray> rays = new ArrayList<Ray>();
		int i, j;
		for (int index : indexes) {
			i = index / matrixSize;
			j = index % matrixSize;
			if (i == 0 && j == 0)
				rays.add(new Ray(_p0, pijCorner.subtract(_p0)));
			else if (i == 0)
				rays.add(new Ray(_p0, pijCorner.addVec(_vRight.scale(j * x)).subtract(_p0)));
			else if (j == 0)
				rays.add(new Ray(_p0, pijCorner.addVec(_vUp.scale(i * y)).subtract(_p0)));
			else
				rays.add(
						new Ray(_p0, pijCorner.addVec(_vRight.scale(j * x)).addVec(_vUp.scale(i * y)).subtract(_p0)));
		}
		return rays;
	}

	/**
	 * get P0
	 * 
	 * @return
	 */
	public Point3D getP0() {
		return this._p0;
	}

	/**
	 * get vUp
	 * 
	 * @return
	 */
	public Vector getVUp() {
		return this._vUp;
	}

	/**
	 * get vTo
	 * 
	 * @return
	 */
	public Vector getVTo() {
		return this._vTo;
	}

	/**
	 * get vRight
	 * 
	 * @return
	 */
	public Vector getVRight() {
		return this._vRight;
	}

	/**
	 * get if camera has focus
	 * 
	 * @return
	 */
	public boolean isFocus() {
		return _focus;
	}

	/**
	 * get focus distance
	 * 
	 * @return
	 */
	public double getFocusDistance() {
		return _focusDistance;
	}

	/**
	 * get aperture size
	 * 
	 * @return
	 */
	public double getApertureSize() {
		return _apertureSize;
	}

	@Override
	public String toString() {
		return "Camera - P0: " + _p0 + " vTo: " + _vTo + " vUp: " + _vUp + " vRight: " + _vRight;
	}
}
