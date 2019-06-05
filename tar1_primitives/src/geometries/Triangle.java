package geometries;

import java.util.List;

import primitives.Color;
import primitives.Material;

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
		this(_pnt1, _pnt2, _pnt3, Color.BLACK, new Material());
	}

	public Triangle(Point3D _pnt1, Point3D _pnt2, Point3D _pnt3, Color emmission, Material material) {
		super(_pnt1, _pnt2, _pnt3, emmission, material);
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
	public List<GeoPoint> findIntersections(Ray _ray) {
		List<GeoPoint> trnglLst = super.findIntersections(_ray);
		if (trnglLst.isEmpty()) { // if ray dosn't cut plane return empty
			return EMPTY_LIST;
		}
		Point3D rayPnt = _ray.getPoint();
		Vector rayVec = _ray.getVector();
		Point3D trnglLstPnt = trnglLst.get(0).point;
		if (!(trnglLstPnt.equals(rayPnt))) { // if ray cuts plane and dosen't start at plane check if it cuts plane
												// inside triangle
			Vector v1 = trianPoints[0].subtract(rayPnt);
			Vector v2 = trianPoints[1].subtract(rayPnt);
			Vector v3 = trianPoints[2].subtract(rayPnt);
			Vector N1 = (v1.vecotrsCrossProduct(v2)).normalize();
			Vector N2 = (v2.vecotrsCrossProduct(v3)).normalize();
			Vector N3 = (v3.vecotrsCrossProduct(v1)).normalize();
			Vector pp0 = trnglLstPnt.subtract(rayPnt);
			double d1, d2, d3;
			d1 = pp0.vectorsDotProduct(N1);
			d2 = pp0.vectorsDotProduct(N2);
			d3 = pp0.vectorsDotProduct(N3);
			double s1, s2, s3;
			s1 = Math.signum(d1);
			s2 = Math.signum(d2);
			s3 = Math.signum(d3);
			if (!(s1 <= 0 && s2 <= 0 && s3 <= 0 || s1 >= 0 && s2 >= 0 && s3 >= 0)) {
				return EMPTY_LIST;
			}
			return trnglLst;
		}
		for (Point3D p : trianPoints) { // if ray starts at one of triangle corners return point
			if (rayPnt.equals(p)) {
				return trnglLst;
			}
		}
		trnglLst.clear();
		// if ray starts at plane but isn't include in plane
		trnglLst = findIntersections(new Ray(new Point3D(rayPnt.addVec(rayVec.scale(-1))), rayVec));
		return trnglLst;
	}

	@Override
	public String toString() {
		return "triangle points: " + trianPoints[0] + " " + trianPoints[1] + " " + trianPoints[2] + " normal: "
				+ normalVector + " emmission: " + _emmission + " material: " + _material;
	}
}