package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class that sets light from sun
 * @author OWNER
 *
 */
public class DirectionalLight extends Light implements LightSource {
	private Vector direction;

	public DirectionalLight(Color col, Vector dir) {
		super(col);
		direction = dir.normalize();
	}

	public Vector getDirection() {
		return direction;
	}

	@Override
	public Color getIntensity(Point3D pnt) {
		return getIntensity();
	}

	@Override
	public Vector getL(Point3D pnt) {
		return direction;
	}

	@Override
	public Vector getD(Point3D pnt) {
		return direction;
	}

}
