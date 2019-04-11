package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube is build by ray and radius
 * 
 * @author OWNER
 *
 */
public class Tube extends RadialGeometry {
	protected Ray tubeRay;

	/**
	 * Constructor of tube
	 * 
	 * @param _tRay
	 * @param rad
	 */
	public Tube(Ray _tRay, double rad) {
		super(rad);
		tubeRay = _tRay;
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		Vector rayVec = tubeRay.getVector();
		Point3D rayPoint = tubeRay.getPoint();
		Vector vecPnt = rayPoint.subtract(pnt);
		double lenRay = vecPnt.vectorsDotProduct(rayVec);
		return new Vector((pnt.subtract(rayPoint.addVec(rayVec.scale(lenRay)))).normalize());
	}
}
