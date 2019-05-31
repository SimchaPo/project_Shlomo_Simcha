package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import static primitives.Util.*;
import primitives.Vector;

/**
 * This Class define Plane in 3d space Plane is build by point and normal
 * 
 * @author OWNER
 */
public class Plane extends Geometry {
	protected Point3D point;
	protected Vector normalVector;

	/********** Constructors ***********/
	/**
	 * Constructor with Point3D and Vector
	 * 
	 * @param _pnt
	 * @param _vec
	 */
	public Plane(Point3D _pnt, Vector _vec, Color emmission, Material material) {
		_emmission = emmission;
		point = new Point3D(_pnt);
		_material = new Material(material);
		normalVector = _vec.normalize();
	}

	public Plane(Point3D _pnt, Vector _vec) {
		this(_pnt, _vec, Color.BLACK, new Material());
	}

	/**
	 * constructor receive three point3D
	 * 
	 * @param pnt1
	 * @param pnt2
	 * @param pnt3
	 */
	public Plane(Point3D pnt1, Point3D pnt2, Point3D pnt3) {
		this(pnt1, (pnt1.subtract(pnt2)).vecotrsCrossProduct(pnt2.subtract(pnt3)));
	}
	
	public Plane(Point3D pnt1, Point3D pnt2, Point3D pnt3, Color emmission, Material material) {
		this(pnt1, (pnt1.subtract(pnt2)).vecotrsCrossProduct(pnt2.subtract(pnt3)), emmission, material);
	}

	/*************** Admin *****************/
	@Override
	public Vector getNormal(Point3D pnt) {
		return normalVector;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray _ray) {
		Vector rayVec = _ray.getVector();
		double rayVecDotProNormal = rayVec.vectorsDotProduct(normalVector);
		if (isZero(rayVecDotProNormal)) {
			return EMPTY_LIST;
		}
		List<GeoPoint> planeLst = new ArrayList<GeoPoint>();
		Point3D rayPnt = _ray.getPoint();
		if (rayPnt.equals(point)) {
			planeLst.add(new GeoPoint(this, point));
			return planeLst;
		}
		Vector pq = point.subtract(rayPnt);
		double pqProDotNormal = pq.vectorsDotProduct(normalVector);
		double t = alignZero(pqProDotNormal / rayVecDotProNormal);
		if (!isZero(t) && t > 0) {
			planeLst.add(new GeoPoint(this, rayPnt.addVec(rayVec.scale(t))));
		} else if (isZero(t)) {
			planeLst.add(new GeoPoint(this, rayPnt));
		}
		return planeLst;
	}

	@Override
	public String toString() {
		return "point on plane: " + point + " noemal: " + normalVector;
	}
}
