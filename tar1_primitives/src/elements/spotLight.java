package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class spotLight extends pointLight {

	private Vector _direction;

	public spotLight(Point3D pnt, double kC, double kL, double kQ, Color col, Vector dir) {
		super(pnt, kC, kL, kQ, col);
		_direction = dir.normalize();
	}

	@Override
	public Color getIntensity(Point3D pnt) {
		double sc = Math.max(0, pnt.equals(_position) ? 0 : _direction.vectorsDotProduct(getL(pnt)));
		return super.getIntensity(pnt).scale(sc);
	}
	
	@Override
	public Vector getL(Point3D pnt) {
		return super.getL(pnt);
	}

	@Override
	public Vector getD(Point3D pnt) {
		return _direction;
	}

}
