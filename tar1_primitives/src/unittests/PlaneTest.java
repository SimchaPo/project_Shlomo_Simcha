package unittests;

import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class PlaneTest extends TestCase {

	/**
	 * test for normal, checks that normal is parallel to other normal
	 */
	public void testGetNormal() {
		Plane pl = new Plane(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(5, 4, 0));
		Vector result = pl.getNormal(new Point3D(1, 0, 0));
		Vector exp = new Vector(0, 0, 1);
		try {
			result.vecotrsCrossProduct(exp);
			fail("problem with normal");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	/**
	 * test for case ray point is same as plane point
	 */
	public void testFindIntersections1() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray point is different from plane point but ray is in plane
	 */
	public void testFindIntersections2() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(0, 2, 0), new Vector(0, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is orthogonal to plane and ray point is on plane
	 */
	public void testFindIntersections3() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(0, 2, 0), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(pl, new Point3D(0, 2, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't orthogonal to plane and ray point is on plane
	 */
	public void testFindIntersections4() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(0, 2, 0), new Vector(3, 4, 5)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(pl, new Point3D(0, 2, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is orthogonal to plane and ray cuts plane
	 */
	public void testFindIntersections5() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(5, 1, -1), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(pl, new Point3D(5, 1, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't orthogonal to plane and ray cuts plane in point
	 */
	public void testFindIntersections6() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(1, 1, -1), new Vector(-1, -1, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(pl, new Point3D(0, 0, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't orthogonal to plane and ray cuts plane not in point
	 */
	public void testFindIntersections7() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(1, 1, -1), new Vector(-1, 1, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(pl, new Point3D(0, 2, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is parallel to plane but not on plane
	 */
	public void testFindIntersections8() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't orthogonal to plane and ray dosn't cut plane not in
	 * point
	 */
	public void testFindIntersections9() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(1, 1, 2), new Vector(-1, 1, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is orthogonal to plane and ray dosen't cuts plane
	 */
	public void testFindIntersections10() {
		Plane pl = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		List<GeoPoint> result = pl.findIntersections(new Ray(new Point3D(5, 1, 1), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

}
