package elements;

import primitives.Color;
import primitives.Vector;

/**
 * class that sets light from sun
 * @author OWNER
 *
 */
public class DirectionalLight extends Light {
	private Vector direction;

	public DirectionalLight(Vector dir, Color col) {
		super(col);
		direction = dir;
	}

	@Override
	public Color getIntensity() {
		return _color;
	}

	public Vector getDirection() {
		return direction;
	}

}
