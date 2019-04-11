package unittests;

import geometries.Plane;
import java.lang.Math;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Vector;

public class PlaneTest extends TestCase {

//	public void testPlanePoint3DVector() {
//		fail("Not yet implemented");
//	}
//
//	public void testPlanePoint3DPoint3DPoint3D() {
//		fail("Not yet implemented");
//	}

	public void testGetNormal() {
		Point3D pnt1=new Point3D(2.0,4.0,8.0),pnt2=new Point3D(1.0,3.0,7.0),pnt3=new Point3D(5.0,5.0,5.0);
		Plane pln=new Plane(pnt1, pnt2, pnt3);
		Vector plnNorm=pln.getNormal(pnt2);
		Vector expectedVec=new Vector(new Point3D(4/Math.sqrt(56.0),-6/Math.sqrt(56.0),2/Math.sqrt(56.0)));
		assertEquals("Get normal Error", expectedVec, plnNorm);
		//fail("Not yet implemented");
	}

}
