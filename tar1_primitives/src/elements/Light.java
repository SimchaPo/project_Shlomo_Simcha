package elements;

import primitives.Color;

/**
 * abstract class for lights
 * 
 * @author OWNER
 *
 */
public abstract class Light {
	protected Color _color;

	/******* constructor *****/
	public Light(Color color) {
		_color = color;
	}

	/******** getters/setters *********/
	public Color getColor() {
		return _color;
	}

	public Color getIntensity() {
		return _color;
	}
}
