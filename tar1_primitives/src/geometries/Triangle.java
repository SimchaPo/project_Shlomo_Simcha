package geometries;

import java.util.ArrayList;
import java.util.List;

//import java.lang.reflect.Constructor;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * This Class define the triangle on plane extends the Plain Class
 * 
 * @author meerz
 *
 */
public class Triangle extends Plane {

	private Point3D trianPoints[] = new Point3D[3];

	/**
	 * Constructor
	 * 
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
	 * 
	 * @return the array of Triangle points
	 */
	public Point3D[] getTrianPoints() {
		return trianPoints;
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		List<Point3D> trnglLst = new ArrayList<Point3D>();
		trnglLst.addAll(super.findIntersections(_ray));
		if (trnglLst.isEmpty()) {
			return trnglLst;
		}
		Point3D rayPnt = _ray.getPoint();
		Vector v1 = trianPoints[0].subtract(rayPnt);
		Vector v2 = trianPoints[1].subtract(rayPnt);
		Vector v3 = trianPoints[2].subtract(rayPnt);
		Vector N1 = (v1.vectrsCrossProduct(v2)).normalize();
		Vector N2 = (v2.vectrsCrossProduct(v3)).normalize();
		Vector N3 = (v3.vectrsCrossProduct(v1)).normalize();
		Vector _p_p0 = trnglLst.get(0).subtract(rayPnt);
		double d1, d2, d3;
		d1 = _p_p0.vectorsDotProduct(N1);
		d2 = _p_p0.vectorsDotProduct(N2);
		d3 = _p_p0.vectorsDotProduct(N3);
		double s1, s2, s3;
		s1 = Math.signum(d1);
		s2 = Math.signum(d2); 
		s3 = Math.signum(d3);
		System.out.println(d1 + " " + Math.signum(d1) + " " + d2 + " " +  Math.signum(d2) + " " + d3 + " " + Math.signum(d3));
		if (Math.signum(d1) != Math.signum(d2) || Math.signum(d1) != Math.signum(d3)) {
			trnglLst.clear();
		}
		return trnglLst;
	}
}
