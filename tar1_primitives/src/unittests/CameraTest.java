package unittests;

import java.util.ArrayList;
import java.util.List;

import elements.Camera;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import junit.framework.TestCase;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CameraTest extends TestCase {
	/**
	 * test for case they are 2 intersections with sphere after view
	 */
	public void testConstructRayThroughPixel() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				//result.addAll(sp.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		//assertEquals("problem with camera", exp, result.size());
	}
	/**
	 * test for case they are 2 intersections with sphere after view
	 */
	public void testConstructRayThroughPixel1() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Sphere sp = new Sphere(new Point3D(0, 0, -3), 1.0);
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 2;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(sp.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 18 intersections with sphere starts at camera
	 */
	public void testConstructRayThroughPixel2() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Sphere sp = new Sphere(new Point3D(0, 0, -2.5), 2.5);
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 18;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(sp.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 10 intersections with sphere starts at camera
	 */
	public void testConstructRayThroughPixel3() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Sphere sp = new Sphere(new Point3D(0, 0, -2), 2);
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 18;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(sp.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 9 intersections when camera is inside sphere
	 */
	public void testConstructRayThroughPixel4() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Sphere sp = new Sphere(new Point3D(0, 0, -2), 4);
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 9;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(sp.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 0 intersections when camera is after sphere
	 */
	public void testConstructRayThroughPixel5() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Sphere sp = new Sphere(new Point3D(0, 0, 1), 0.5);
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 0;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(sp.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 9 intersections when camera is before plane and plane
	 * is orthogonal to Vto
	 */
	public void testConstructRayThroughPixel6() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Plane pl = new Plane(new Point3D(0, 0, -4), new Vector(0, 0, 1));
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 9;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(pl.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 0 intersections when camera is after plane and plane
	 * is orthogonal to Vto
	 */
	public void testConstructRayThroughPixel7() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Plane pl = new Plane(new Point3D(0, 0, 4), new Vector(0, 0, 1));
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 0;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(pl.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 9 intersections when camera is before plane and plane
	 * isn't orthogonal to Vto
	 */
	public void testConstructRayThroughPixel8() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Plane pl = new Plane(new Point3D(0, 5, -5), new Vector(1, 1, 1));
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 9;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(pl.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 6 intersections when camera is before plane and plane
	 * isn't orthogonal to Vto
	 */
	public void testConstructRayThroughPixel9() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Plane pl = new Plane(new Point3D(0, 5, -5), new Vector(0, 1, 3));
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 6;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(pl.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they is 1 intersections when camera is before triangle
	 */
	public void testConstructRayThroughPixel10() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Triangle tr = new Triangle(new Point3D(0, -1, -2), new Point3D(-1, 1, -2), new Point3D(1, 1, -2));
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 1;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(tr.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}

	/**
	 * test for case they are 2 intersections when camera is before triangle
	 */
	public void testConstructRayThroughPixel11() {
		Point3D pnt = new Point3D(0, 0, 0);
		Vector vecTo = new Vector(0, 0, -1);
		Vector vecUp = new Vector(0, 1, 0);
		Camera cam = new Camera(pnt, vecUp, vecTo);
		Triangle tr = new Triangle(new Point3D(0, -20, -2), new Point3D(-1, 1, -2), new Point3D(1, 1, -2));
		List<Point3D> result = new ArrayList<Point3D>();
		int exp = 2;
		int Nx = 3, Ny = 3;
		double sd = 1, sw = 9, sh = 9;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				result.addAll(tr.findIntersections(cam.constructRayThroughPixel(Nx, Ny, i, j, sd, sw, sh)));
			}
		}
		assertEquals("problem with camera", exp, result.size());
	}
}
