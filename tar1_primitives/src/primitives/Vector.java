package primitives;

//import static primitives.Util.usubtract;

import primitives.Point3D;

/**
 * @author shlomo, simcha
 * vector is with @Point3D
 */
public class Vector {
	//private double vecLength = 0.0;
	protected Point3D vectorPoint;

	static Point3D nullCoordinate = new Point3D(0,0,0);

	/********** Constructors ***********/
	/**
	 * constructor with one point
	 * @param pnt
	 */
	public Vector(Point3D pnt) {
		vectorPoint = pnt;
	}
	
	/**
	 * copy constructor for vector
	 * @param vec
	 */
	public Vector(Vector vec) {
		this(vec.vectorPoint);
	}


	// ***************** Getters/Setters ********************** //


	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Vector)) return false;
		return vectorPoint.equals(((Vector)obj).vectorPoint);
	}

	@Override
	public String toString() {
		return vectorPoint.toString();
	}

	/**
	 * add Vector Function, returns new vector after adding
	 * @param otherVector
	 * @return a new Vector
	 */
	public Vector vectorAdd(Vector otherVector) {
		return new Vector(this.vectorPoint.addVec(otherVector));
	}

	/************** Operations ***************/
	/**
	 * subtraction Vector Function
	 * @param otherVector
	 * @return a new Vector
	 */
	public Vector vectorSub(Vector otherVector) {
		return new Vector(vectorPoint.subtract(otherVector.vectorPoint));
	}

	/**
	 * vector *scalar Function
	 * @param scalar
	 * @return a new vector Multiplied by the scalar
	 */
	public Vector vecProductByScalar(double scalar) {
		vectorPoint.x.scale(scalar);
		return new Vector(new Point3D(vectorPoint.x.scale(scalar), vectorPoint.y.scale(scalar), vectorPoint.z.scale(scalar)));
	}

	/**
	 * Vector * Vector Function
	 * @param otherVector
	 * @return
	 */
	public double vectorsDotProduct(Vector otherVector) {
		return this.vectorPoint.distancePow(otherVector.vectorPoint);
	}

	/**
	 * Vector X Vector Function
	 * @param otherVector
	 * @return
	 */
	public Vector vectrsCrossProduct(Vector otherVector) {
		return new Vector(new Point3D(
				(vectorPoint.y.multiply(otherVector.vectorPoint.z)).subtract(vectorPoint.z.multiply(otherVector.vectorPoint.y)),
				(vectorPoint.z.multiply(otherVector.vectorPoint.x)).subtract(vectorPoint.x.multiply(otherVector.vectorPoint.z)),
				(vectorPoint.x.multiply(otherVector.vectorPoint.y)).subtract(vectorPoint.y.multiply(otherVector.vectorPoint.x))));
	}

}
