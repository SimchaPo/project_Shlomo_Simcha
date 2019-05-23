package geometries;

import primitives.Color;

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
		this(pnt, rad, hgt, Color.BLACK);
	}

	public Cylinder(Ray pnt, double rad, double hgt, Color emmission) {
		super(pnt, rad, emmission);
		hight = hgt;
	}

	/**
	 * Getter
	 * 
	 * @return height
	 */
	public double getHight() {
		return hight;
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		Vector rayVec = new Vector(this._ray.getVector());
		if (pnt.equals(_ray.getPoint())) {
			return rayVec.scale(-1);
		}
		Vector pntVec = pnt.subtract(this._ray.getPoint());
		double len = rayVec.vectorsDotProduct(pntVec);
		if (len > 0 && len < hight) {
			return pnt.subtract(_ray.getPoint().addVec(rayVec.scale(len))).normalize();
		} else if (len == 0) {
			return rayVec.scale(-1);
		} else if (len == hight) {
			return rayVec;
		} else {
			throw new IllegalArgumentException("point isn't on cylinder");
		}
	}
}
