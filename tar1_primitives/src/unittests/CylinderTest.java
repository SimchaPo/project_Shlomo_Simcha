package unittests;

import geometries.Cylinder;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


public class CylinderTest extends TestCase {

	public void testGetNormal() {
		Cylinder testCylndr= new Cylinder(new Ray(new Point3D(3.0,2.0,1.0), new Vector(0.0,1.0,0.0)), 2.0, 8.0);
		Point3D pnt=new Point3D(3.0,6.0,1.0);
		Vector testVec= testCylndr.getNormal(pnt);
		Vector expectedVec= new Vector(0.0,1.0,0.0);
		assertEquals("Cylinder get normal error", expectedVec, testVec);
	}

//	public void testCylinder() {
//		fail("Not yet implemented");
//	}

}
