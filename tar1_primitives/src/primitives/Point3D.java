package primitives;

import primitives.Coordinate;
import primitives.Vector;
//import static primitives.Util.usubtract;
import java.lang.Math;

/**
 * The class define the point in 3d space
 * @author shlomo, simcha Point3D is a point with 3 @Coordinate
 */
public class Point3D {
	public static final Point3D ZERO = new Point3D(0,0,0);
	
	private Coordinate x;
	private Coordinate y;
	private Coordinate z;

	/********** Constructors ***********/
	/**
	 * constructor gets 3 @Coordinate and builds a point
	 * 
	 * @param _x coordinate
	 * @param _y coordinate
	 * @param _z coordinate
	 */
	public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
		this(_x._coord, _y._coord, _z._coord);
	}

	/**
	 * constructor gets 3 double numbers and builds a point
	 * 
	 * @param _x double number
	 * @param _y double number
	 * @param _z double number
	 */
	public Point3D(double _x, double _y, double _z) {
		x = new Coordinate(_x);
		y = new Coordinate(_y);
		z = new Coordinate(_z);
	}

	/**
	 * copy constructor
	 * 
	 * @param other other point to copy
	 */
	public Point3D(Point3D other) {
		this(other.x, other.y, other.z);
	}

	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		return x.equals(((Point3D) obj).x) && y.equals(((Point3D) obj).y) && z.equals(((Point3D) obj).z);
	}

	@Override
	public String toString() {
		return "(" + x._coord + "," + y._coord + "," + z._coord + ")";
	}

	/************** Operations ***************/
	/**
	 * subtract gets another point
	 * 
	 * @param otherPoint
	 * @return a new vector between the 2 points
	 */
	public Vector subtract(Point3D otherPoint) {
		return new Vector(new Point3D(this.x.subtract(otherPoint.x), this.y.subtract(otherPoint.y),
				this.z.subtract(otherPoint.z)));
	}

	/**
	 * adds a vector to the point
	 * 
	 * @param vec
	 * @return the new point
	 */
	public Point3D addVec(Vector vec) {
		return new Point3D(this.x.add(vec.vectorPoint.x), this.y.add(vec.vectorPoint.y), this.z.add(vec.vectorPoint.z));
	}

	/**
	 * distance^2 between 2 points
	 * 
	 * @param otherPoint
	 * @return
	 */
	public double distancePow(Point3D otherPoint) {
		Coordinate newx = x.subtract(otherPoint.x);
		Coordinate newy = y.subtract(otherPoint.y);
		Coordinate newz = z.subtract(otherPoint.z);
		return (newx.multiply(newx))._coord + (newy.multiply(newy))._coord + (newz.multiply(newz))._coord;
	}

	/**
	 * distance between 2 points
	 * 
	 * @param _otherPoint
	 * @return
	 */
	public double distance(Point3D _otherPoint) {
		return Math.sqrt(this.distancePow(_otherPoint));
	}
	/**
	 * Getters
	 */
	public Coordinate getX() {
		return x;
	}

	public Coordinate getY() {
		return y;
	}

	public Coordinate getZ() {
		return z;
	}
	

}
