package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder build by point and radius
 * @author OWNER
 *
 */
public class Cylinder extends Tube {

	private double hight;
	
	/**
	 * Constructor for Cylinder
	 * @param pnt
	 * @param rad
	 */
	public Cylinder(Ray pnt, double rad, double hgt) {
		super(pnt, rad);
		hight = hgt;
	}
	
	@Override
	public Vector getNormal(Point3D pnt) {
		// TODO Auto-generated method stub
		return null;
	}
}
