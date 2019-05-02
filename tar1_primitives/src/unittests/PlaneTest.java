package unittests;

import geometries.Plane;
import junit.framework.TestCase;
import primitives.Point3D;
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
			result.vectrsCrossProduct(exp);
			fail("problem with normal");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
