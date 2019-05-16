package elements;

import primitives.Color;

/**
 * 
 * @author OWNER this class sets the amount of light the color needs to get at
 *         point
 */
public class AmbientLight {
	private Color _color;
	private double _ka;

	public Color get_color() {
		return _color;
	}

	public double get_ka() {
		return _ka;
	}

	public AmbientLight(Color ia, double ka) {
		_color = ia.scale(ka);
		_ka = ka;
	}

	public Color getIntensity() {
		return _color;
	}
}
