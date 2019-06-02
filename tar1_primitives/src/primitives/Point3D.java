package primitives;

/**
 * The class define the point in 3d space
 * 
 * @author shlomo, simcha Point3D is a point with 3 @Coordinate
 */
public class Point3D {
	public static final Point3D ZERO = new Point3D(0, 0, 0);

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
		return "point3D: (" + x._coord + "," + y._coord + "," + z._coord + ")";
	}

	/************** Operations ***************/
	/**
	 * subtract gets another point
	 * 
	 * @param otherPoint
	 * @return a new vector between the 2 points
	 */
	public Vector subtract(Point3D otherPoint) {
		return new Vector(this.x.subtract(otherPoint.x)._coord, this.y.subtract(otherPoint.y)._coord,
				this.z.subtract(otherPoint.z)._coord);
	}

	/**
	 * adds a vector to the point
	 * 
	 * @param vec
	 * @return the new point
	 */
	public Point3D addVec(Vector vec) {
		return new Point3D(this.x.add(vec.getPoint().x), this.y.add(vec.getPoint().y), this.z.add(vec.getPoint().z));
	}

	/**
	 * distance^2 between 2 points
	 * 
	 * @param otherPoint
	 * @return a double number
	 */
	public double distancePow(Point3D otherPoint) {
		Coordinate newx = x.subtract(otherPoint.x);
		Coordinate newy = y.subtract(otherPoint.y);
		Coordinate newz = z.subtract(otherPoint.z);
		return (newx.multiply(newx)).add(newy.multiply(newy)).add(newz.multiply(newz))._coord;
	}

	/**
	 * distance between 2 points
	 * 
	 * @param _otherPoint
	 * @return a double number
	 */
	public double distance(Point3D _otherPoint) {
		return Math.sqrt(this.distancePow(_otherPoint));
	}

	/**
	 * get X
	 * 
	 * @return coordinate X, don't change
	 */
	public Coordinate getX() {
		return x;
	}

	/**
	 * get Y
	 * 
	 * @return coordinate Y, don't change
	 */
	public Coordinate getY() {
		return y;
	}

	/**
	 * get Z
	 * 
	 * @return coordinate Z, don't change
	 */
	public Coordinate getZ() {
		return z;
	}
}
