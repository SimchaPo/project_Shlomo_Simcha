/**
 * 
 */
package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
 * @author meerz
 *
 */
public interface Intersectable {
	List<Point3D> findIntersections(Ray _ray);  
}
