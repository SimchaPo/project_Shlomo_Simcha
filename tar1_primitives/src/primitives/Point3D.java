package primitives;
import primitives.Coordinate;
import primitives.Vector;

import static primitives.Util.usubtract;

import java.lang.Math;

/**
 * 
 * @author shlomo, simcha
 *
 */
public class Point3D {
	
	protected Coordinate x, y, z;
	
	/********** Constructors ***********/
	
	public Point3D() {
		this(0,0,0);
	}

	public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
		this(_x._coord, _y._coord, _z._coord);
	}
	
	public Point3D(double _x, double _y, double _z) {
		x._coord = _x;
		y._coord = _y;
		z._coord = _z;
	}

	// ***************** Getters/Setters ********************** //

	public Point3D get() {
		return new Point3D(x,y,z);
	}
	
	/*************** Admin *****************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Point3D)) return false;
		return usubtract(x._coord, ((Point3D)obj).x._coord) == 0.0
				&& usubtract(y._coord, ((Point3D)obj).y._coord) == 0.0
				&& usubtract(y._coord, ((Point3D)obj).y._coord) == 0.0;
	}

	@Override
	public String toString() {
		return "(" + x._coord + "," + y._coord + "," + z._coord + ")";
	}

	/************** Operations ***************/
	public Vector substruct(Point3D otherPoint) {
		return new Vector(new Point3D(this.x.subtract(otherPoint.x), this.y.subtract(otherPoint.y),
				this.z.subtract(otherPoint.z)));
	}

	public Point3D addVec(Vector vec) {
		return new Point3D(this.x.add(vec.vectorPoint.x), this.y.add(vec.vectorPoint.y), this.z.add(vec.vectorPoint.z));
	}

	public double distancePow(Point3D otherPoint) {
		Vector tmp = this.substruct(otherPoint);
		double distPow= ((tmp.vectorPoint.x.get())*(tmp.vectorPoint.x.get()) + 
				(tmp.vectorPoint.y.get())*(tmp.vectorPoint.y.get()) + 
				(tmp.vectorPoint.z.get())*(tmp.vectorPoint.z.get()));
		return distPow;
	}
	public double distance(Point3D _otherPoint) {
		return Math.sqrt(this.distancePow(_otherPoint));
	}
}
