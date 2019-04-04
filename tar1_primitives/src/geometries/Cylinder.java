package geometries;

import java.util.function.ToDoubleBiFunction;

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

	@Override
	public Vector getNormal(Point3D pnt) {
		Vector rayVec= this.tubeRay.getRayVector();
		Vector pntVec= this.tubeRay.getRayPoint().subtract(pnt);
		double dotProdRes=rayVec.vectorsDotProduct(pntVec);
		if(dotProdRes>this.hight) {
			// TODO Auto-generated method stub
			}
		else {
			if (dotProdRes==0) {
				return new Vector(rayVec.vecProductByScalar(-1));
			}
			else if(dotProdRes==hight) {
				return new Vector(rayVec);
			}
			else {
				return super.getNormal(pnt);
			}
		}
		}
	// TODO Auto-generated method stub
	return null;
}}
