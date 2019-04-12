package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for normal
 * @author OWNER
 *
 */
public interface Geometry extends Intersectable{
	Vector getNormal(Point3D pnt);
}
