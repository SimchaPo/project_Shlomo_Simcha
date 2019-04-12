package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * This class define geometric 3D figure "Sphere"
 * 
 * @author OWNER
 *
 */
public class Sphere extends RadialGeometry {
	protected Point3D sphereCenter;

	/**
	 * constructor for Sphere
	 * 
	 * @param _pnt
	 * @param rad
	 */
	public Sphere(Point3D _pnt, double rad) {
		super(rad);
		sphereCenter = _pnt;
	}

	@Override
	public Vector getNormal(Point3D pnt) {
		//
		return (sphereCenter.subtract(pnt)).normalize();
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		// TODO Auto-generated method stub
		return null;
	}

}
