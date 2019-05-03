package unittests;

import java.util.List;

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

	public void testFindIntersections() {
		Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
		System.out.println("abc");
		List<Point3D> result = sp.findIntersections(new Ray(new Point3D(0, 0,0),new Vector(0, 0, 1)));
		System.out.println("abc" + result);
		//Vector exp = new Vector(0, 1, 0);
		fail("faild");
		//assertEquals("problem with normal", exp, result);
	}
}
