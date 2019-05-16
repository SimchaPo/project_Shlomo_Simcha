/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
 * @author meerz
 *
 */
public interface Intersectable {
	public List<Point3D> findIntersections(Ray _ray);
	public static final List<Point3D> EMPTY_LIST = new ArrayList<>();
}
