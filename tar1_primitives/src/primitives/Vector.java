package primitives;

import static primitives.Util.usubtract;

import primitives.Point3D;

public class Vector {
	protected double vecLength = 0.0;
	protected Point3D vectorPoint;
	protected Vector vectorUnit;

	static Point3D nullCoordinate = new Point3D();

	/********** Constructors ***********/
	/**
	 * constructor with two points
	 * 
	 * @param pnt
	 * @param beginPnt
	 */
	public Vector(Point3D pnt, Point3D beginPnt) {
		vectorPoint = pnt;
		vecLength = pnt.distance(beginPnt);
		if (this.vecLength == 1) {
			vectorUnit = this;
		} else
			vectorUnit.setVectorUnitDet(beginPnt);
	}

	/**
	 * constructor with end point & default point nullCoordinate
	 * 
	 * @param pnt
	 */
	public Vector(Point3D pnt) {
		this(pnt, nullCoordinate);
	}

	// ***************** Getters/Setters ********************** //
	/**
	 * the unit vector setting function from nullCoordinate
	 */
	public void setVectorUnitDet() {
		setVectorUnitDet(nullCoordinate);
	}

	/**
	 * the unit vector setting function from begin point
	 * 
	 * @param otherVecPnt
	 */
	public void setVectorUnitDet(Point3D otherVecPnt) {
		Double distnce = this.vectorPoint.distance(otherVecPnt);
		vectorUnit = new Vector((new Point3D(this.vectorPoint.x._coord / distnce, this.vectorPoint.y._coord / distnce,
				this.vectorPoint.z._coord / distnce)));
	}

	public Vector getVectorUnitDet() {
		return new Vector(vectorUnit.vectorPoint);
	}

	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Vector)) return false;
		return usubtract(vectorPoint.x._coord, ((Vector)obj).vectorPoint.x._coord) == 0.0
				&& usubtract(vectorPoint.y._coord, ((Vector)obj).vectorPoint.y._coord) == 0.0
				&& usubtract(vectorPoint.z._coord, ((Vector)obj).vectorPoint.z._coord) == 0.0;
	}

	@Override
	public String toString() {
		return vectorPoint.toString();
	}

	/**
	 * add Vector Function
	 * 
	 * @param otherVector
	 * @return
	 */
	public Vector vectorAdd(Vector otherVector) {
		return new Vector(new Point3D(this.vectorPoint.x._coord + otherVector.vectorPoint.x._coord,
				this.vectorPoint.y._coord + otherVector.vectorPoint.y._coord,
				this.vectorPoint.z._coord + otherVector.vectorPoint.z._coord), this.vectorPoint);
	}

	/************** Operations ***************/
	/**
	 * substruction Vector Function
	 * 
	 * @param otherVector
	 * @return
	 */
	public Vector vectorSub(Vector otherVector) {
		Vector vec = new Vector(this.vectorAdd(otherVector).vectorPoint);
		vec.vecProductByScalar(-1);
		return vec;
	}

	/**
	 * vector *scalar Function
	 * 
	 * @param scalar
	 * @return
	 */
	public void vecProductByScalar(double scalar) {
		this.vectorPoint.x._coord *= scalar;
		this.vectorPoint.y._coord *= scalar;
		this.vectorPoint.z._coord *= scalar;
	}

	/**
	 * Vector * Vector Function
	 * 
	 * @param otherVector
	 * @return
	 */
	public double vectorsDotProduct(Vector otherVector) {
		// add orto check
		return this.vectorPoint.x._coord * otherVector.vectorPoint.x._coord
				+ this.vectorPoint.y._coord * otherVector.vectorPoint.y._coord
				+ this.vectorPoint.z._coord * otherVector.vectorPoint.z._coord;
	}

	/**
	 * Vector X Vector Function
	 * 
	 * @param otherVector
	 * @return
	 */
	public Vector vectrsCrossProduct(Vector otherVector) {
		return new Vector(new Point3D(
				this.vectorPoint.y._coord * otherVector.vectorPoint.z._coord
						- this.vectorPoint.z._coord * otherVector.vectorPoint.y._coord,
				-(this.vectorPoint.x._coord * otherVector.vectorPoint.z._coord
						- this.vectorPoint.z._coord * otherVector.vectorPoint.x._coord),
				this.vectorPoint.x._coord * otherVector.vectorPoint.y._coord
						- this.vectorPoint.y._coord * otherVector.vectorPoint.x._coord));
	}

}
