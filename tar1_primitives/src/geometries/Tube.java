package geometries;

import java.util.List;

import primitives.Color;
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
	protected Ray _ray;

	/**
	 * Constructor of tube
	 * 
	 * @param _tRay
	 * @param rad
	 */
	public Tube(Ray ray, double rad) {
		this(ray, rad, Color.BLACK);
	}
	
	public Tube(Ray ray, double rad, Color emmission) {
		super(rad, emmission);
		_ray = new Ray(ray);
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		Vector rayVec = new Vector(this._ray.getVector());
		Vector pntVec = pnt.subtract(this._ray.getPoint());
		double len = rayVec.vectorsDotProduct(pntVec);
		return pnt.subtract(_ray.getPoint().addVec(rayVec.scale(len))).normalize();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray _ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
