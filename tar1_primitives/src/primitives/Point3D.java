package primitives;

import primitives.Coordinate;
import primitives.Vector;
import java.lang.Math;

/**
 * 
 * @author meerz
 *
 */
public class Point3D {
	Coordinate x, y, z;

	public Point3D() {
		x._coord = 0;
		y._coord = 0;
		z._coord = 0;
	}

	public Point3D(double _x, double _y, double _z) {
		x._coord = _x;
		y._coord = _y;
		z._coord = _z;
	}

	public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
		x = _x;
		y = _y;
		z = _z;
	}

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
