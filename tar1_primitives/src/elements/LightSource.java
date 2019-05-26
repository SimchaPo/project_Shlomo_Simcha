package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public interface LightSource {
	public Color getIntensity(Point3D pnt);
	public Vector getL(Point3D pnt);
	public Vector geDL(Point3D pnt);
}
