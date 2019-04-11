package unittests;

import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

public class VectorTest extends TestCase {

	/*public void testVectorPoint3D() {
		fail("Not yet implemented");
	}

	public void testVectorDoubleDoubleDouble() {
		fail("Not yet implemented");
	}

	public void testVectorVector() {
		fail("Not yet implemented");
	}*/
/**
 * testing vector unit, test also tests equals and toString
 */
	public void testVectorUnit() {
		Point3D pnt = new Point3D(5.2, 2.4, 1.4);
		Vector vec = new Vector(pnt);
		Vector result = vec.normalize();
		double dis = pnt.distance(new Point3D(0.0,0.0,0.0));
		double a = Util.uscale(5.2, 1/dis);
		double b = Util.uscale(2.4, 1/dis);
		double c = Util.uscale(1.4, 1/dis);
		Vector exp = new Vector(new Point3D(a,b,c));
		System.out.println(exp.toString() + result.toString());
		assertEquals("problem with unit", exp, result);	
	}
	
//	public void testVectorUnit2() {
//		Point3D pnt = new Point3D(5.2, 2.4, 1.4);
//		Vector vec = new Vector(pnt);
//		Vector result = vec.vectorUnit();
//		Vector notexp = new Vector(pnt);
//		System.out.println(notexp.toString() + result.toString());
//		assertFalse("problem with unit", notexp.equals(result));
//	}
/**
 * 
 */
	public void testVectorAdd() {
		Vector vec1 = new Vector (5.2, 2.4, 1.4);
		Vector vec2 = new Vector(1.6, 2.0, 7.648);
		Vector exp = new Vector(6.8, 4.4, 9.048);
		Vector result = vec1.vectorAdd(vec2);
		System.out.println(exp.toString() + result.toString());
		assertEquals("problem with adding", exp, result);
	}

	public void testVectorSub() {
		Vector vec1 = new Vector (5.2, 2.4, 1.4);
		Vector vec2 = new Vector(1.6, 2.0, 7.648);
		Vector exp = new Vector(3.6, 0.4, -6.248);
		Vector result = vec1.vectorSub(vec2);
		System.out.println(exp.toString() + result.toString());
		assertEquals("problem with subtracting", exp, result);
	}

	/*public void testVecProductByScalar() {
		fail("Not yet implemented");
	}

	public void testVectorsDotProduct() {
		fail("Not yet implemented");
	}

	public void testVectrsCrossProduct() {
		fail("Not yet implemented");
	}

	public void testPreventNullVector() {
		fail("Not yet implemented");
	}*/

}
