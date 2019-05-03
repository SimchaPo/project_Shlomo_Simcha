package elements;

import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CameraTest extends TestCase {
	Point3D testPnt = new Point3D(0, 0, 0);
	Vector testVecTo = new Vector(1, 0, 0);
	Vector testVecUp = new Vector(0, 1, 0);
	Camera testerCam = new Camera(testPnt, testVecUp, testVecTo);

	public CameraTest(String name) {
		super(name);
	}

//	public void testCamera() {
//		//Camera testerCam=new Camera(testPnt, testVecUp, testVecTo);
//		fail("Not yet implemented");
//	}

	public void testConstructRayThroughPixel() {
		int i = 5, j = 14, nx = 8, ny = 16;
		double sDistance = 7.0, sW = 4.0, sH = 8.0;
		Point3D resPnt = new Point3D(7, -3.25, 0.75);
		// Vector resVec=new Vector(7.0,-3.75,0.75);
		Ray result = new Ray(testerCam.getP0(), new Vector(resPnt));
		Ray testFuncRay = testerCam.constructRayThroughPixel(nx, ny, i, j, sDistance, sW, sH);
		assertEquals("Equal", result.getVector(), testFuncRay.getVector());
	}

}
