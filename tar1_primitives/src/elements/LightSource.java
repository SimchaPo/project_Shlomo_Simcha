package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for lights
 * 
 * @author OWNER
 *
 */
public interface LightSource {
	public Color getIntensity(Point3D pnt);

	public Vector getL(Point3D pnt);
}
