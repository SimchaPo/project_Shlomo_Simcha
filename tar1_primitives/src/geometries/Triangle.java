package geometries;

import primitives.Point3D;

/**
 * 
 * @author OWNER
 *
 */
public class Triangle extends Plane {

	Point3D trianPoints[] = new Point3D[3];

	public Triangle(Point3D _pnt1, Point3D _pnt2, Point3D _pnt3) {
		super(_pnt1, _pnt2, _pnt3);
		trianPoints[0] = _pnt1;
		trianPoints[1] = _pnt2;
		trianPoints[2] = _pnt3;
	}
}
