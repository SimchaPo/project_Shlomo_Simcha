package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class pointLight extends Light implements LightSource {
	protected Point3D _position;
	protected double _kC, _kL, _kQ;

	public pointLight(Point3D pnt, double kC, double kL, double kQ, Color col) {
		super(col);
		_position = pnt;
		_kC = kC;
		_kL = kL;
		_kQ = kQ;
	}

	@Override
	public Color getIntensity(Point3D pnt) {
		double disPow = pnt.distancePow(_position);
		return _color.reduce(_kC + _kL*(Math.sqrt(disPow)) + _kQ*disPow);
	}

	@Override
	public Vector getL(Point3D pnt) {
		return pnt.subtract(_position).normalize();
	}

	@Override
	public Vector getD(Point3D pnt) {
		return getL(pnt);
	}

}
