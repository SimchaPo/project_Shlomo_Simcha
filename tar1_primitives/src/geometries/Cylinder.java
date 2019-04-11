package geometries;

//import java.util.function.ToDoubleBiFunction;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * This Class define Cylinder extends Tube class Cylinder build by point and
 * radius
 * 
 * @author OWNER
 *
 */
public class Cylinder extends Tube {

	private double hight;

	/**
	 * Constructor for Cylinder
	 * 
	 * @param pnt
	 * @param rad
	 */
	public Cylinder(Ray pnt, double rad, double hgt) {
		super(pnt, rad);
		hight = hgt;
	}

	/**
	 * Getter
	 * 
	 * @return hight
	 */
	public double getHight() {
		return hight;
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		Vector rayVec = new Vector(this.ray.getVector());
		Vector pntVec = this.ray.getPoint().subtract(pnt);
		double length = rayVec.vectorsDotProduct(pntVec);// length of pntVec projection on rayVec

		if (length > this.hight) {// point not in cylinder case
			throw new IllegalArgumentException("ERROR!!! '\n' The point not on cylinder!");
		}
		if (length == 0) { // point on cylinder bottom
			return rayVec.scale(-1);
		} else if (length == hight) {// point on cylinder top
			return rayVec;
		}
		return super.getNormal(pnt);// point on cylinder sides

	}
}
