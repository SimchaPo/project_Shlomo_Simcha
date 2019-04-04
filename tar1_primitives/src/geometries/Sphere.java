package geometries;

import primitives.Point3D;
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
		return (sphereCenter.subtract(pnt)).vectorUnit();
	}

}
