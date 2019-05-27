package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class spotLight extends pointLight implements LightSource {

	private Vector _direction;

	public spotLight(Point3D pnt, double kC, double kL, double kQ, Color col, Vector dir) {
		super(pnt, kC, kL, kQ, col);
		_direction = dir;
	}

	@Override
	public Color getIntensity(Point3D pnt) {
		double disPow = pnt.distancePow(_position);
		Point3D dirPnt = _direction.getPoint();
		double sc = Math.max(0, pnt.equals(dirPnt) ? 0 : _direction.vectorsDotProduct(pnt.subtract(dirPnt)));
		return _color.scale(sc).reduce(_kC + _kL * (Math.sqrt(disPow)) + _kQ * disPow);

	}
	
	@Override
	public Vector getL(Point3D pnt) {
		return pnt.subtract(_position).normalize();
	}

	@Override
	public Vector getD(Point3D pnt) {
		return _direction;
	}

}
