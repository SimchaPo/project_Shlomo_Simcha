package geometries;

import java.util.List;

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
	protected Ray ray;

	/**
	 * Constructor of tube
	 * 
	 * @param _tRay
	 * @param rad
	 */
	public Tube(Ray _tRay, double rad) {
		super(rad);
		ray = new Ray(_tRay);
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		Vector rayVec = new Vector(this.ray.getVector());
		Vector pntVec = pnt.subtract(this.ray.getPoint());
		double len = rayVec.vectorsDotProduct(pntVec);
		return pnt.subtract(ray.getPoint().addVec(rayVec.scale(len))).normalize();
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
