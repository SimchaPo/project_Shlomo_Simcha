package unittests;

import java.util.ArrayList;
import java.util.List;

import geometries.Triangle;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class t1 extends TestCase {
	/**
	 * test for case ray is an plane and starts inside triangle
	 */
	public void testFindIntersections12() {
		Triangle tr = new Triangle(new Point3D(0, 0, 0), new Point3D(-4, 0, 0), new Point3D(0, 4, 0));
		List<Point3D> result = tr.findIntersections(new Ray(new Point3D(-1, 1, 0), new Vector(-1, 0, 0)));
		List<Point3D> exp = new ArrayList<Point3D>();
		exp.add(new Point3D(-3, 1, 0));
		assertEquals("problem with intersections", exp, result);
	}
}
