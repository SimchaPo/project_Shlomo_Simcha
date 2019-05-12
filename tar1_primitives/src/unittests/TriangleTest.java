package unittests;

import java.util.ArrayList;
import java.util.List;

import geometries.Triangle;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class TriangleTest extends TestCase {
	/**
	 * test for normal, checks that normal is parallel to other normal
	 */
	public void testGetNormal() {
		Triangle tr = new Triangle(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(5, 4, 0));
		Vector result = tr.getNormal(new Point3D(1, 0, 0));
		Vector exp = new Vector(0, 0, 1);
		try {
			result.vecotrsCrossProduct(exp);
			fail("problem with normal");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	/**
	 * test for case ray dosen't cut triangles plane
	 */
	public void testFindIntersections1() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 1, 0)));
		List<Point3D> exp = new ArrayList<Point3D>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is orthogonal and cuts triangle inside
	 */
	public void testFindIntersections2() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-1, 1, -1), new Vector(0, 0, 1)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(-1, 1, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is orthogonal and cuts triangle at corner
	 */
	public void testFindIntersections3() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0, 4, -1), new Vector(0, 0, 1)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(0, 4, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is orthogonal and cuts plane but dosen't cut triangle
	 */
	public void testFindIntersections4() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-5, 0, -1), new Vector(0, 0, 1)));
		List<Point3D> exp = new ArrayList<Point3D>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't orthogonal and cuts triangle at corner
	 */
	public void testFindIntersections5() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0, 0, -2), new Vector(0, 1, 1)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(0, 2, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't orthogonal and cuts triangle inside
	 */
	public void testFindIntersections6() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(-1, 1, 1)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(-1, 1, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't orthogonal and cuts plane but not triangle
	 */
	public void testFindIntersections7() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0, 0, -2), new Vector(1, 1, 1)));
		List<Point3D> exp = new ArrayList<Point3D>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is an plane and cuts triangle at corner
	 */
	public void testFindIntersections8() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(-1, 1, 0)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(0, 0, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray point is a corner in triangle
	 */
	public void testFindIntersections9() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-4, 0, 0), new Vector(-1, 1, 7)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(-4, 0, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is an plane and cuts triangle at side
	 */
	public void testFindIntersections10() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-4, 2, 0), new Vector(1, 0, 0)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(-2, 2, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is an plane and dosn't cut triangle
	 */
	public void testFindIntersections11() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-4, 2, 0), new Vector(-1, 0, 0)));
		List<Point3D> exp = new ArrayList<Point3D>();
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is an plane and starts inside triangle
	 */
	public void testFindIntersections12() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-1, 1, 0), new Vector(-1, 0, 0)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(-1, 1, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray is an plane and starts at triangle side
	 */
	public void testFindIntersections13() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(0, 2, 0), new Vector(1, 0, 0)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(0, 2, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't an plane but starts in plane inside triangle
	 */
	public void testFindIntersections14() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-1, 2, 0), new Vector(1, 0, 3)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(-1, 2, 0));
		assertEquals("problem with intersections", exp, result);
	}

	/**
	 * test for case ray isn't an plane but starts in plane outside triangle
	 */
	public void testFindIntersections15() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(5, 2, 0), new Vector(1, 0, 3)));
		List<Point3D> exp = new ArrayList<Point3D>();
		assertEquals("problem with intersections", exp, result);
	}
}
