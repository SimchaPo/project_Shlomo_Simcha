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
		ray =new Ray(_tRay);
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		
		Vector rayVec = new Vector(ray.getVector());
		Point3D rayPoint =new Point3D(ray.getPoint()); 
		Vector vecPnt = rayPoint.subtract(pnt); //vector from rey_Begining_point to pnt
		double lenRay = vecPnt.vectorsDotProduct(rayVec);// lengtn of vecPnt projection on rayVec
		/**
		 * math calculating of vector ray<->pnt
		 * Vector (pnt-(rayPoint+(rayVec*lenRay))) normalized
		 */
		return  (pnt.subtract(rayPoint.addVec(rayVec.scale(lenRay)))).normalize();
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
