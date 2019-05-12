package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for normal
 * 
 * @author OWNER
 *
 */
public interface Geometry extends Intersectable {
	public Vector getNormal(Point3D pnt);

	public static final List<Point3D> EMPTY_LIST = new ArrayList<>();
}
