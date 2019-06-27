//Simcha Podolsky 311215149
//Shlomo Meirzon

package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import static primitives.Util.usubtract;

import java.util.ArrayList;
import java.util.List;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * This class define geometric 3D figure "Sphere"
 * 
 * @author OWNER
 *
 */
public class Sphere extends RadialGeometry {
	protected Point3D _sphereCenter;

	/*********** constructors **********/
	/**
	 * constructor for Sphere
	 * 
	 * @param pnt
	 * @param rad
	 */
	public Sphere(Point3D pnt, double rad) {
		this(pnt, rad, Color.BLACK, new Material());
	}

	public Sphere(Point3D pnt, double rad, Color emmission, Material material) {
		super(rad, emmission, material);
		_sphereCenter = pnt;
	}

	/********* admin *********/
	@Override
	public Vector getNormal(Point3D pnt) {
		return (pnt.subtract(_sphereCenter)).normalize();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray _ray) {
		List<GeoPoint> sphereLst = new ArrayList<GeoPoint>();
		Point3D rayPnt = _ray.getPoint();
		Vector rayVec = _ray.getVector();
		if (rayPnt.equals(_sphereCenter)) {
			sphereLst.add(new GeoPoint(this, _sphereCenter.addVec(rayVec.scale(_radius))));
			return sphereLst;
		}
		Vector vecPO = _sphereCenter.subtract(rayPnt);
		double disPO = vecPO.length();
		double tm = rayVec.vectorsDotProduct(vecPO);// length of ray to the point meeting radius orthogonal
		// to the ray
		double powD = alignZero(disPO * disPO) - alignZero(tm * tm);
		double d = Math.sqrt(powD); // distance from sphere center to the ray
		if (d > _radius) {
			return sphereLst;
		}
		double th = Math.sqrt(alignZero(_radius * _radius) - powD);
		double t1 = usubtract(tm, -th);
		double t2 = usubtract(tm, th);
		if (isZero(t2) || t2 > 0) {
			sphereLst.add(new GeoPoint(this, isZero(t2) ? rayPnt : rayPnt.addVec(rayVec.scale(t2))));
		}
		if (isZero(t1) || t1 >= 0) {
			sphereLst.add(new GeoPoint(this, isZero(t1) ? rayPnt : rayPnt.addVec(rayVec.scale(t1))));
		}
		return sphereLst;
	}

	@Override
	public String toString() {
		return "center: " + _sphereCenter + " radius: " + _radius + " emmission: " + _emmission + " material: "
				+ _material;
	}
}
