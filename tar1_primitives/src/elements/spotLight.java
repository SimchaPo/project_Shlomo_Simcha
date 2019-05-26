package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class spotLight extends pointLight {

	private Vector _direction;
	public spotLight(Point3D pnt, double kC, double kL, double kQ, Color col, Vector dir) {
		super(pnt, kC, kL, kQ, col);
		_direction = dir;
	}

}
