package elements;

import primitives.Color;
import primitives.Point3D;

public class pointLight extends Light {
	private Point3D _position;
	private double _kC, _kL, _kQ;

	public pointLight(Point3D pnt, double kC, double kL, double kQ, Color col) {
		super(col);
		_position = pnt;
		_kC = kC;
		_kL = kL;
		_kQ = kQ;
	}

	@Override
	public Color getIntensity() {
		// TODO Auto-generated method stub
		return null;
	}

}
