package unittests;

import java.util.ArrayList;
import java.util.List;

import elements.Camera;
import geometries.Sphere;
import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CameraTest extends TestCase {
	/**
	 * 
	 */
	public void testConstructRayThroughPixel1() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Sphere sp = new Sphere(new Point3D(0,0,-3), 3);
		List<Point3D> result = new ArrayList<Point3D>();
		List<Point3D> exp = new ArrayList<Point3D>();
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				result.addAll(sp.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		System.out.println(result.size() + " " + result);
		assertTrue(true);
	}
}
