package unittests;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import junit.framework.TestCase;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class RenderTest extends TestCase {
	/**
	 * draw in image with 3 triangles and a sphere
	 */
	public void testRenderImage() {
		Geometries geometries = new Geometries();
		Sphere sphere = new Sphere(new Point3D(0, 0, -150), 50);
		Triangle tr1 = new Triangle(new Point3D(100, 0, -149), new Point3D(0, 100, -149), new Point3D(100, 100, -149));
		Triangle tr2 = new Triangle(new Point3D(-100, 0, -149), new Point3D(0, 100, -149),
				new Point3D(-100, 100, -149));
		Triangle tr3 = new Triangle(new Point3D(100, 0, -149), new Point3D(0, -100, -149),
				new Point3D(100, -100, -149));
		Triangle tr4 = new Triangle(new Point3D(-100, 0, -149), new Point3D(0, -100, -149),
				new Point3D(-100, -100, -149));
		geometries.add(sphere, tr1, tr2, tr3, tr4);
		Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1));
		AmbientLight amb = new AmbientLight(new Color(255, 255, 255), 1);
		Scene scene = new Scene("scene");
		scene.set_background(new Color(75, 127, 190));
		scene.set_ambientLight(amb);
		scene.set_camera(cam, 150);
		scene.addGeometries(geometries);
		ImageWriter im = new ImageWriter("sim", 500, 500, 500, 500);
		Render ren = new Render(scene, im);
		ren.renderImage();
		ren.printGrid(10, java.awt.Color.red);
		ren.get_imageWriter().writeToimage();
	}

}