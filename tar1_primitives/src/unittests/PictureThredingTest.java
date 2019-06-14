package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import geometries.Geometries;
import geometries.Sphere;
import junit.framework.TestCase;
import optimized.PictureThreading;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import scene.Scene;

public class PictureThredingTest extends TestCase {

	public void testPictureThreding() {
		Scene scene = new Scene("abcThread");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 200, 200, 50);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere1;
		for (int c = -700; c <= 700; c += 200) {
			for (int a = -600, b = -1000; b >= -2000; a += 100, b -= 200) {
				sphere1 = new Sphere(new Point3D(c, a, b), 100, new Color(java.awt.Color.green),
						new Material(1, 0.5, 40, 0.5, 0));
				geometries.add(sphere1);
			}
		}
		scene.setLights(new DirectionalLight(new Color(255, 100, 100), new Vector(0, 0, -1)));
		ImageWriter imageWriter = new ImageWriter("depth of field 4 Thread", 500, 500, 500, 500);
		PictureThreading renderT = new PictureThreading(scene, imageWriter);
		renderT.renderImage();
		renderT.getImageWriter().writeToImage();
	}

}
