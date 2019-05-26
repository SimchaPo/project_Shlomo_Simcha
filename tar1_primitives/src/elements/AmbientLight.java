package elements;

import primitives.Color;

/**
 * 
 * @author OWNER this class sets the amount of light the color needs to get at
 *         point
 */
public class AmbientLight extends Light {
	private double _ka;

	public double get_ka() {
		return _ka;
	}

	public AmbientLight(Color ia, double ka) {
		super(ia.scale(ka));
		_ka = ka;
	}

	@Override
	public Color getIntensity() {
		return _color;
	}
}
