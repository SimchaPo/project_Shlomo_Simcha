package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for spot light
 * 
 * @author OWNER
 *
 */
public class SpotLight extends PointLight {

	private Vector _direction;

	/********* constructor ********/
	public SpotLight(Point3D pnt, double kC, double kL, double kQ, Color col, Vector dir) {
		super(pnt, kC, kL, kQ, col);
		_direction = dir.normalize();
	}

	/******** getters/setters ********/
	@Override
	public Color getIntensity(Point3D pnt) {
		try {
			double sc = _direction.vectorsDotProduct(getL(pnt));
			if (sc <= 0)
				return Color.BLACK;
			return super.getIntensity(pnt).scale(sc);
		} catch (Exception e) {
			return Color.BLACK;
		}
	}
	
	@Override
	public String toString() {
		return "SpotLight: " + _direction + " positin: " + _position + " color: " + super.toString() + " kc: " + _kC + " kl: "
				+ _kL + " kq: " + _kQ;
	}
}
