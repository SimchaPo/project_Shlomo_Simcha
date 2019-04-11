package geometries;

//import java.util.function.ToDoubleBiFunction;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder build by point and radius
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
		Vector rayVec = this.tubeRay.getVector();
		Vector pntVec = this.tubeRay.getPoint().subtract(pnt);
		double dotProdRes = rayVec.vectorsDotProduct(pntVec);

		if (dotProdRes > this.hight) {
			throw new IllegalArgumentException("ERROR!!! '\n' The point not on cylinder!");
		}
		if (dotProdRes == 0) {
			return new Vector(rayVec.scale(-1));
		} else if (dotProdRes == hight) {
			return new Vector(rayVec);
		}
		return super.getNormal(pnt);

	}
}
