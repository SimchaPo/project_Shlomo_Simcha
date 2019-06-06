package geometries;

import primitives.Color;
import primitives.Material;
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
	protected Material _material;

	/******** getters *******/
	public Material getMaterial() {
		return _material;
	}

	public Color getEmmission() {
		return _emmission;
	}

	abstract public Vector getNormal(Point3D pnt);
}