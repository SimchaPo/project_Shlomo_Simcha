package primitives;

//import static primitives.Util.usubtract;

import primitives.Point3D;

/**
 * This class define vector & function those calculate mathematical quantities
 * 
 * @author shlomo, simcha vector is with @Point3D
 */
public class Vector {

	private Point3D point;

	/********** Constructors ***********/
	/**
	 * constructor with one point
	 * 
	 * @param pnt
	 */
	public Vector(Point3D pnt) {
		this(pnt.getX().get(), pnt.getY().get(), pnt.getZ().get());
	}

	/**
	 * constructor gets 3 double
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y, double z) {
		point = new Point3D(x, y, z);
		if (Point3D.ZERO.equals(point))
			throw new IllegalArgumentException("ERROR!!! /n Vector Zero does not defined !");
	}

	/**
	 * copy constructor for vector
	 * 
	 * @param vec
	 */
	public Vector(Vector vec) {
		this(vec.point);
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
		return point.equals(((Vector) obj).point);
	}

	@Override
	public String toString() {
		return point.toString();
	}

	/**
	 * add Vector Function, returns new vector after adding
	 * 
	 * @param otherVector
	 * @return a new Vector
	 */
	public Vector vectorAdd(Vector otherVector) {
		return new Vector(this.point.addVec(otherVector));
	}

	/************** Operations ***************/
	/**
	 * subtraction Vector Function
	 * 
	 * @param otherVector
	 * @return a new Vector
	 */
	public Vector vectorSub(Vector otherVector) {
		return point.subtract(otherVector.point);
	}

	/**
	 * vector *scalar Function
	 * 
	 * @param scalar
	 * @return a new vector Multiplied by the scalar
	 */
	public Vector scale(double scalar) {
		return new Vector(point.getX().scale(scalar)._coord, point.getY().scale(scalar)._coord,
				point.getZ().scale(scalar)._coord);
	}

	/**
	 * Vector * Vector Function
	 * 
	 * @param otherVector
	 * @return result of vector dot product
	 */
	public double vectorsDotProduct(Vector otherVector) {
		double x = point.getX()._coord * otherVector.point.getX()._coord;
		double y = point.getY()._coord * otherVector.point.getY()._coord;
		double z = point.getZ()._coord * otherVector.point.getZ()._coord;
		return x + y + z;
	}

	/**
	 * function for calculate vector length
	 * @return double length 
	 */
	public double length() {
		return point.distance(Point3D.ZERO);
	}

	/**
	 * Vector X Vector Function
	 * 
	 * @param otherVector
	 * @return new vector result of cross vector product
	 */
	public Vector vectrsCrossProduct(Vector otherVector) {
		return new Vector(
				(point.getX().multiply(otherVector.point.getZ()))
						.subtract(point.getZ().multiply(otherVector.point.getY()))._coord,
				(point.getZ().multiply(otherVector.point.getX()))
						.subtract(point.getX().multiply(otherVector.point.getZ()))._coord,
				(point.getX().multiply(otherVector.point.getY()))
						.subtract(point.getY().multiply(otherVector.point.getX()))._coord);
	}

	/**
	 * function calculate and return the Unit Vector
	 * 
	 * @param _vec
	 * @return new vector normalized 
	 */
	public Vector normalize() {
		double devider = 1/this.length();
		Coordinate x = this.point.getX().scale(devider);
		Coordinate y = this.point.getY().scale(devider);
		Coordinate z = this.point.getZ().scale(devider);
		return new Vector(x._coord, y._coord, z._coord);
	}

	/**
	 * Getter point of the vector
	 * 
	 * @return point !!! DON`T CHANGE!!! USE THE COPY
	 */
	public Point3D getPoint() {
		return point;
	}

}
