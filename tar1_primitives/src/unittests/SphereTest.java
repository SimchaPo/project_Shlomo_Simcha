package unittests;

import java.util.ArrayList;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class SphereTest extends TestCase {
	/**
	 * test for sphere normal
	 */
	public void testGetNormal() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		Vector result = sp.getNormal(new Point3D(0, 3, 0));
		Vector exp = new Vector(0, 1, 0);
		assertEquals("problem with normal", exp, result);
	}

	/**
	 * test for case the ray point is same as sphere center
	 */
	public void testFindIntersections1() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(0, 0, 3)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is on sphere and crosses center
	 */
	public void testFindIntersections2() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 0, -3), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(0, 0, -3)));
		exp.add(new GeoPoint(sp, new Point3D(0, 0, 3)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is under sphere and crosses center
	 */
	public void testFindIntersections3() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 0, -4), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(0, 0, -3)));
		exp.add(new GeoPoint(sp, new Point3D(0, 0, 3)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is on sphere and dosne't go thru
	 */
	public void testFindIntersections4() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 0, 3), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(0, 0, 3)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is under sphere and crosses sphere not in center
	 */
	public void testFindIntersections5() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(-5, -2, 0), new Vector(1, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(-3, 0, 0)));	
		exp.add(new GeoPoint(sp, new Point3D(0, 3, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is out of sphere and dosn't cut sphere
	 */
	public void testFindIntersections6() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(-5, -2, 0), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is under sphere and ray vector goes other side
	 * from sphere
	 */
	public void testFindIntersections7() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(-5, -2, 0), new Vector(-1, -1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is after sphere and dosne't go thru
	 */
	public void testFindIntersections8() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 0, 4), new Vector(0, 0, 1)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is on bottom of sphere and crosses sphere not in
	 * center
	 */
	public void testFindIntersections9() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(-3, 0, 0), new Vector(1, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(-3, 0, 0)));
		exp.add(new GeoPoint(sp, new Point3D(0, 3, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is on top of sphere and dosen't crosses sphere
	 * not against center
	 */
	public void testFindIntersections10() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 3, 0), new Vector(1, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(0, 3, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is after sphere and dosen't crosses sphere not
	 * against center
	 */
	public void testFindIntersections11() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(5, 2, 0), new Vector(1, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is on sphere and tangent to sphere
	 */
	public void testFindIntersections12() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 3, 0), new Vector(0, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(0, 3, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is before sphere and tangent to sphere
	 */
	public void testFindIntersections13() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 2, 0), new Vector(0, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		exp.add(new GeoPoint(sp, new Point3D(0, 3, 0)));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is after sphere and tangent to sphere
	 */
	public void testFindIntersections14() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 4, 0), new Vector(0, 1, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case the ray point is out of sphere and vector is 90C to PO
	 */
	public void testFindIntersections15() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		List<GeoPoint> result = sp.findIntersections(new Ray(new Point3D(0, 4, 0), new Vector(1, 0, 0)));
		List<GeoPoint> exp = new ArrayList<GeoPoint>();
		assertEquals("problem with intersections", exp, result);
	}
}
