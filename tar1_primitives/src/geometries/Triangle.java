package geometries;

import java.util.ArrayList;
import java.util.List;

//import java.lang.reflect.Constructor;

import primitives.Point3D;
import primitives.Ray;
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
		List<Point3D> trnglLst = new ArrayList<Point3D>(null);
		Point3D[] tPnts = this.getTrianPoints();
		Plane tPlane = new Plane(tPnts[0], tPnts[1], tPnts[2]);
		Point3D rayPnt = _ray.getPoint();
		// Vector rayVec = _ray.getVector();
		List<Point3D> plnIntrn = tPlane.findIntersections(_ray);
		if (plnIntrn != null) {
			Point3D P = plnIntrn.get(0);
			Vector P_P0 = P.subtract(rayPnt);
			Vector v1 = tPnts[0].subtract(rayPnt);
			Vector v2 = tPnts[1].subtract(rayPnt);
			Vector v3 = tPnts[2].subtract(rayPnt);
			Vector N1 = (v1.vectrsCrossProduct(v2)).normalize();
			Vector N2 = (v2.vectrsCrossProduct(v3)).normalize();
			Vector N3 = (v3.vectrsCrossProduct(v1)).normalize();
			double[] N = new double[3];
			N[0] = P_P0.vectorsDotProduct(N1);
			N[1] = P_P0.vectorsDotProduct(N2);
			N[2] = P_P0.vectorsDotProduct(N3);
			if ((N[0] > 0 && N[1] > 0 && N[2] > 0) | (N[0] < 0 && N[1] < 0 && N[2] < 0)) {
				trnglLst.add(0, P);
			}
		}
		return trnglLst;
	}
}
