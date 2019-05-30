package elements;

import primitives.Color;
/**
 * abstract class for lights
 * @author OWNER
 *
 */
public abstract class Light {
	protected Color _color;

	public Light(Color color) {
		_color = color;
	}
	
	public Color get_color() {
		return _color;
	}

	public Color getIntensity() {
		return _color;
	}
}
