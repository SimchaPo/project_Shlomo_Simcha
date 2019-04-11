package geometries;

//import java.lang.reflect.Constructor;

import primitives.Point3D;

/**
 * This Class define the triangle on plane
 * extends the Plain Class
 * @author meerz
 *
 */
public class Triangle extends Plane {

	private Point3D trianPoints[] = new Point3D[3];
	/**
	 * Constructor 
	 * @param _pnt1
	 * @param _pnt2
	 * @param _pnt3
	 */
	public Triangle(Point3D _pnt1, Point3D _pnt2, Point3D _pnt3) {
		super(_pnt1, _pnt2, _pnt3);
		trianPoints[0] = _pnt1;
		trianPoints[1] = _pnt2;
		trianPoints[2] = _pnt3;
	}
	/**
	 * Getter 
	 * @return the array of Triangle points
	 */
	public Point3D[] getTrianPoints() {
		return trianPoints;
	}
}
