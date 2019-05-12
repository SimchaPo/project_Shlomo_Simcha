package unittests;

import junit.framework.TestCase;
import primitives.Coordinate;
import primitives.Util;
import primitives.Vector;

public class VectorTest extends TestCase {
	/**
	 * test for adding function
	 */
	public void testVectorAdd() {
		Vector vec1 = new Vector(5.2, 2.4, 1.4);
		Vector vec2 = new Vector(1.6, 2.0, 7.648);
		Vector exp = new Vector(6.8, 4.4, 9.048);
		Vector result = vec1.vectorAdd(vec2);
		assertEquals("problem with adding", exp, result);
	}

	/**
	 * test for sub function
	 */
	public void testVectorSub() {
		Vector vec1 = new Vector(5.2, 2.4, 1.4);
		Vector vec2 = new Vector(1.6, 2.0, 7.648);
		Vector exp = new Vector(3.6, 0.4, -6.248);
		Vector result = vec1.vectorSub(vec2);
		assertEquals("problem with subtracting", exp, result);
	}

	/**
	 * test scaling with a number
	 */
	public void testScale1() {
		Vector vec1 = new Vector(5.2, 2.4, 1.4);
		Vector exp = new Vector(10.4, 4.8, 2.8);
		Vector result = vec1.scale(2);
		assertEquals("problem with scaling", exp, result);
	}

	/**
	 * test scaling by ZERO
	 */
	public void testScale2() {
		Vector vec1 = new Vector(5.2, 2.4, 1.4);
		try {
			vec1.scale(0);
			fail("problem with vec zero");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	/**
	 * test product 2 vectors
	 */
	public void testVectorsDotProduct1() {
		Vector vec1 = new Vector(5.2, 2.4, 1.4);
		Vector vec2 = new Vector(1.6, 2.0, 7.6);
		Coordinate exp = new Coordinate(23.76);
		Coordinate result = new Coordinate(vec1.vectorsDotProduct(vec2));
		assertEquals("problem with dot product", exp, result);
	}

	/**
	 * test product orthogonal vectors
	 */
	public void testVectorsDotProduct2() {
		Vector vec1 = new Vector(5.2, 5.2, 0);
		Vector vec2 = new Vector(-1.6, 1.6, 0);
		double exp = 0;
		double result = vec1.vectorsDotProduct(vec2);
		assertTrue("problem with dot product", Util.isZero(Util.usubtract(exp, result)));
	}

	/**
	 * test product for the same vector
	 */
	public void testVectorsDotProduct3() {
		Vector vec1 = new Vector(1, 1, 1);
		Vector vec2 = new Vector(2, 2, 2);
		double exp = 6;
		double result = vec1.vectorsDotProduct(vec2);
		assertTrue("problem with dot product", Util.isZero(Util.usubtract(exp, result)));
	}

	/**
	 * test function for vector length
	 */
	public void testLength() {
		Vector vec1 = new Vector(5.2, 2.4, 1.4);
		double exp = 5.89576118919;
		double result = vec1.length();
		assertTrue("problem with length", Util.isZero(Util.usubtract(exp, result)));
	}

	/**
	 * test for cross product
	 */
	public void testVectrsCrossProduct1() {
		Vector vec1 = new Vector(3, 4, 5);
		Vector vec2 = new Vector(6, 2, 3);
		Vector exp = new Vector(2, 21, -18);
		Vector result = vec1.vecotrsCrossProduct(vec2);
		assertEquals("problem with subtracting", exp, result);
	}

	/**
	 * test cross product when parallel
	 */
	public void testVectrsCrossProduct2() {
		Vector vec1 = new Vector(1, 1, 1);
		Vector vec2 = new Vector(-1, -1, -1);
		try {
			vec1.vecotrsCrossProduct(vec2);
			fail("problem with cross of when parallel");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	/**
	 * test normalize for vector
	 */
	public void testNormalize() {
		Vector vec = new Vector(2, 1, 2);
		double dis = vec.length();
		double a = Util.uscale(2.0, 1 / dis);
		double b = Util.uscale(1, 1 / dis);
		double c = Util.uscale(2, 1 / dis);
		Vector exp = new Vector(a, b, c);
		Vector result = vec.normalize();
		assertEquals("problem with normalize", exp, result);
	}

}
