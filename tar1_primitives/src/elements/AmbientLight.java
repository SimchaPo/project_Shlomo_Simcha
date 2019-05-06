package elements;

import primitives.Color;

public class AmbientLight {
	private Color _color;
	double Ka;

	public AmbientLight(Color _Ia, double _Ka) {
		_color=_Ia;
		Ka=_Ka;
	}

	public Color getIntensity() {
		Color _colorAL = new Color(this._color.getColor().getRed() / 255, this._color.getColor().getGreen() / 255,
				this._color.getColor().getBlue() / 255).scale(this.Ka);
		return _colorAL;
	}
}
