package elements;

import primitives.Color;

/**
 * abstract class for lights
 * 
 * @author OWNER
 *
 */
public abstract class Light {
	private Color _color;

	/******* constructor *****/
	public Light(Color color) {
		_color = color;
	}

	/******** getters/setters *********/
	public Color getIntensity() {
		return _color;
	}
	
	@Override
	public String toString() {
		return "color: " + _color;
	}
}
