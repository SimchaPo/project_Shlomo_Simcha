package geometries;

import primitives.Color;
import primitives.Mateial;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for normal
 * 
 * @author OWNER
 *
 */
public abstract class Geometry implements Intersectable {
	protected Color _emmission;
	protected Mateial _material;

	abstract public Vector getNormal(Point3D pnt);

	public Color getEmmission() {
		return _emmission;
	}
}