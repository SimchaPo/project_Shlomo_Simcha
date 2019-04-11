package primitives;

//import static primitives.Util.usubtract;

import primitives.Point3D;

/**
 * This class define vector & function those calculate mathematical quantities
 * 
 * @author shlomo, simcha vector is with @Point3D
 */
public class Vector {

	protected Point3D vectorPoint;

	/********** Constructors ***********/
	/**
	 * constructor with one point
	 * 
	 * @param pnt
	 */
	public Vector(Point3D pnt) {
		this(pnt.getX().get(), pnt.getY().get(), pnt.getZ().get()));
	}

	/**
	 * constructor gets 3 double
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y, double z) {
		vectorPoint = new Point3D(x, y, z);
		if (Point3D.ZERO.equals(vectorPoint))
			throw new IllegalArgumentException("ERROR!!! /n Vector Zero does not defined !");
	}

	/**
	 * copy constructor for vector
	 * 
	 * @param vec
	 */
	public Vector(Vector vec) {
		this(vec.vectorPoint);
	}

	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		return vectorPoint.equals(((Vector) obj).vectorPoint);
	}

	@Override
	public String toString() {
		return vectorPoint.toString();
	}

	/**
	 * add Vector Function, returns new vector after adding
	 * 
	 * @param otherVector
	 * @return a new Vector
	 */
	public Vector vectorAdd(Vector otherVector) {
		return new Vector(this.vectorPoint.addVec(otherVector));
	}

	/************** Operations ***************/
	/**
	 * subtraction Vector Function
	 * 
	 * @param otherVector
	 * @return a new Vector
	 */
	public Vector vectorSub(Vector otherVector) {
		return new Vector(vectorPoint.subtract(otherVector.vectorPoint));
	}

	/**
	 * vector *scalar Function
	 * 
	 * @param scalar
	 * @return a new vector Multiplied by the scalar
	 */
	public Vector scale(double scalar) {
		return new Vector(new Point3D(vectorPoint.getX().scale(scalar), vectorPoint.getY().scale(scalar),
				vectorPoint.getZ().scale(scalar)));
	}

	/**
	 * Vector * Vector Function
	 * 
	 * @param otherVector
	 * @return
	 */
	public double vectorsDotProduct(Vector otherVector) {
		Coordinate newx = vectorPoint.getX().multiply(otherVector.vectorPoint.getX());
		Coordinate newy = vectorPoint.getY().multiply(otherVector.vectorPoint.getY());
		Coordinate newz = vectorPoint.getZ().multiply(otherVector.vectorPoint.getZ());
		return newx._coord + newy._coord + newz._coord;
	}

	/**
	 * Vector X Vector Function
	 * 
	 * @param otherVector
	 * @return
	 */
	public Vector vectrsCrossProduct(Vector otherVector) {
		return new Vector(new Point3D(
				(vectorPoint.getX().multiply(otherVector.vectorPoint.getZ()))
						.subtract(vectorPoint.getZ().multiply(otherVector.vectorPoint.getY())),
				(vectorPoint.getZ().multiply(otherVector.vectorPoint.getX()))
						.subtract(vectorPoint.getX().multiply(otherVector.vectorPoint.getZ())),
				(vectorPoint.getX().multiply(otherVector.vectorPoint.getY()))
						.subtract(vectorPoint.getY().multiply(otherVector.vectorPoint.getX()))));
	}

	/**
	 * function calculate and return the Unit Vector
	 * 
	 * @param _vec
	 * @return
	 */
	public Vector vectorUnit() {
		double devider = 1/this.vectorPoint.distance(new Point3D(0, 0, 0));
		Coordinate x = this.vectorPoint.getX().scale(devider);
		Coordinate y = this.vectorPoint.getY().scale(devider);
		Coordinate z = this.vectorPoint.getZ().scale(devider);
		return new Vector(x._coord, y._coord, z._coord);
	}
}
