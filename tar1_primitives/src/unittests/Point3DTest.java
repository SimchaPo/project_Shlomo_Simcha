//Simcha Podolsky 311215149
//Shlomo Meirzon

package unittests;

import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Vector;

public class Point3DTest extends TestCase {
	/**
	 * test subtract with two points
	 */
	public void testSubtract1() {
		Point3D pnt1 = new Point3D(1, 5, 4.5);
		Point3D pnt2 = new Point3D(7, -1.5, 14);
		Vector result = pnt1.subtract(pnt2);
		Vector exp = new Vector(-6, 6.5, -9.5);
		assertEquals("problem with subtracting", exp, result);
	}

	/**
	 * test subtract when get zero
	 */
	public void testSubtract2() {
		Point3D pnt1 = new Point3D(1, 5, 4.5);
		Point3D pnt2 = new Point3D(1, 5, 4.5);
		try {
			pnt1.subtract(pnt2);
			fail("problem when vector zero");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	/**
	 * test add vector to point
	 */
	public void testAddVec() {
		Point3D pnt1 = new Point3D(1, 5, 4.5);
		Vector vec = new Vector(-6, 6.5, -9.5);
		Point3D result = pnt1.addVec(vec);
		Point3D exp = new Point3D(-5, 11.5, -5);
		assertEquals("problem with addvec", exp, result);
	}

	/**
	 * test distance power function
	 */
	public void testDistancePow() {
		Point3D pnt1 = new Point3D(1, 5, 4.5);
		Point3D pnt2 = new Point3D(7, -1.5, 14);
		double result = pnt1.distancePow(pnt2);
		double exp = 168.5;
		assertEquals("problem with distance pow", exp, result);
	}

	/**
	 * test distance/ function
	 */
	public void testDistance() {
		Point3D pnt1 = new Point3D(1, 5, 4.5);
		Point3D pnt2 = new Point3D(7, -1.5, 14);
		double result = pnt1.distance(pnt2);
		double exp = Math.sqrt(168.5);
		assertEquals("problem with distance pow", exp, result);
	}

}
