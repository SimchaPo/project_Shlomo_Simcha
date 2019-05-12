package unittests;

import geometries.Cylinder;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CylinderTest extends TestCase {
	/**
	 * test cylinder normal when point on sides
	 */
	public void testGetNormal1() {
		Cylinder cyl = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 2.0, 8.0);
		Vector result = cyl.getNormal(new Point3D(0, -2, 7));
		Vector exp = new Vector(0, -1, 0);
		assertEquals("Cylinder get normal error", exp, result);
	}

	/**
	 * test cylinder normal when point is same point of ray
	 */
	public void testGetNormal2() {
		Cylinder cyl = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 2.0, 8.0);
		Vector result = cyl.getNormal(new Point3D(0, 0, 0));
		Vector exp = new Vector(0, 0, -1);
		assertEquals("Cylinder get normal error", exp, result);
	}

	/**
	 * test cylinder normal when point is on the bottom
	 */
	public void testGetNormal3() {
		Cylinder cyl = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 2.0, 8.0);
		Vector result = cyl.getNormal(new Point3D(1.5, -1, 0));
		Vector exp = new Vector(0, 0, -1);
		assertEquals("Cylinder get normal error", exp, result);
	}

	/**
	 * test cylinder normal when point is on the top
	 */
	public void testGetNormal4() {
		Cylinder cyl = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 2.0, 8.0);
		Vector result = cyl.getNormal(new Point3D(1, 1, 8));
		Vector exp = new Vector(0, 0, 1);
		assertEquals("Cylinder get normal error", exp, result);
	}

	/**
	 * test cylinder normal when point out of cylinder
	 */
	public void testGetNormal5() {
		Cylinder cyl = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 2.0, 8.0);
		try {
			cyl.getNormal(new Point3D(2, 0, 9));
			fail("problem with point out of cylinder");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
