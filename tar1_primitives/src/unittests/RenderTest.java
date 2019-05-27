package unittests;

import java.awt.List;
import java.util.ArrayList;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.LightSource;
import elements.pointLight;
import elements.spotLight;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import junit.framework.TestCase;
import primitives.Color;
import primitives.Material;
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
		scene.setBackground(new Color(75, 127, 190));
		scene.setAmbientLight(amb);
		scene.setCamera(cam, 150);
		scene.addGeometries(geometries);
		ImageWriter im = new ImageWriter("sim", 500, 500, 500, 500);
		Render ren = new Render(scene, im);
		ren.renderImage();
		ren.printGrid(10, java.awt.Color.red);
		ren.get_imageWriter().writeToimage();
	}

	/**
	 * draw in image with 3 triangles and a sphere with different colors
	 */
	public void testRenderImage2() {
		Geometries geometries = new Geometries();
		Sphere sphere = new Sphere(new Point3D(0, 0, -150), 50, new Color(java.awt.Color.darkGray), new Material());
		Triangle tr1 = new Triangle(new Point3D(100, 0, -149), new Point3D(0, 100, -149), new Point3D(100, 100, -149),
				new Color(java.awt.Color.blue), new Material());
		Triangle tr2 = new Triangle(new Point3D(-100, 0, -149), new Point3D(0, 100, -149), new Point3D(-100, 100, -149),
				new Color(java.awt.Color.pink), new Material());
		Triangle tr3 = new Triangle(new Point3D(100, 0, -149), new Point3D(0, -100, -149), new Point3D(100, -100, -149),
				new Color(java.awt.Color.green), new Material());
		Triangle tr4 = new Triangle(new Point3D(-100, 0, -149), new Point3D(0, -100, -149),
				new Point3D(-100, -100, -149), new Color(java.awt.Color.darkGray), new Material());
		geometries.add(sphere, tr1, tr2, tr3, tr4);
		Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1));
		AmbientLight amb = new AmbientLight(new Color(0, 0, 0), 1);
		Scene scene = new Scene("scene");
		scene.setBackground(new Color());
		scene.setAmbientLight(amb);
		scene.setCamera(cam, 150);
		scene.addGeometries(geometries);
		ImageWriter im = new ImageWriter("simColor", 500, 500, 500, 500);
		Render ren = new Render(scene, im);
		ren.renderImage();
		ren.printGrid(10, java.awt.Color.white);
		ren.get_imageWriter().writeToimage();
	}

	/**
	 * Testing creation of lighted images
	 */
	public void testRenderImage3() {
		Scene sc = new Scene("Simcha");
		java.util.List<LightSource> lts = new ArrayList<LightSource>();
		Camera cam = (new Camera(new Point3D(0, 0, 0), new Vector(1, 0, 0), new Vector(0, 1, 0))); // NOTE: in this
																									// test, vTo is NOT
																									// the usual 0,0,-1.
																									// It is 1,0,0!!
		sc.setCamera(cam, 5300);
		lts.add(new pointLight(new Point3D(-30, 50, 60), 1, 0.00005, 0.00003, new Color(200, 200, 200)));
		lts.add(new pointLight(new Point3D(80, 80, 120), 1, 0.00005, 0.00003, new Color(00, 00, 200)));
		lts.add(new spotLight(new Point3D(30, 0, -60), 1, 0.00005, 0.00003, new Color(200, 00, 000),
				new Vector(1, 0, 2)));
		lts.add(new DirectionalLight(new Color(10, 100, 10), new Vector(0, 0, -0.5)));

		Sphere s;

		// Remove this loop and all it's contents if you wish the rendering to take less
		// than 30 minutes!!
		for (double x = -50; x <= 50; x += 5)
			for (double y = -(50 - Math.abs(x)); y <= 50 - Math.abs(x); y += 5) {
				double z = Math.sqrt(2500 - x * x - y * y);
				s = new Sphere(
						new Point3D(x + 60, y, z), 5, new Color((int) Math.abs(x + y + z) % 25,
								(int) Math.abs(x + y + z + 10) % 25, (int) Math.abs(x + y + z + 20) % 25),
						new Material(1, 0.1, 99));
				sc.addGeometries(s);
				if (z != 0) {
					s = new Sphere(
							new Point3D(x + 60, y, -z), 5, new Color((int) Math.abs(x + y + z) % 25,
									(int) Math.abs(x + y + z + 10) % 25, (int) Math.abs(x + y + z + 20) % 25),
							new Material(1, 0.1, 99));
					sc.addGeometries(s);
				}

			}

		s = new Sphere(new Point3D(80, 0, 120), 70, new Color(0, 0, 0), new Material(0.05, 1, 15));
		sc.addGeometries(s);

		s = new Sphere(new Point3D(60, 0, 0), 30, new Color(75, 0, 25), new Material(0.2, 1, 15));
		sc.addGeometries(s);

		s = new Sphere(new Point3D(60, -900, 0), 800, new Color(0, 0, 0), new Material(0.1, 1, 15));
		sc.addGeometries(s);

		s = new Sphere(new Point3D(60, 900, 0), 800, new Color(0, 0, 0), new Material(0.1, 1, 15));
		sc.addGeometries(s);

		sc.addGeometries(new Plane(new Point3D(250, -200, -150), new Point3D(250, 200, -150),
				new Point3D(250, -200, 200), new Color(15, 15, 15), new Material(0.7, 1, 99)));

		sc.addGeometries(new Triangle(new Point3D(-5000, -200, -70), new Point3D(150, 200, -70),
				new Point3D(150, -200, -70), new Color(7, 7, 7), new Material(0.1, 1, 99)));
		sc.addGeometries(new Triangle(new Point3D(-5000, 200, -70), new Point3D(150, 200, -70),
				new Point3D(-5000, -200, -70), new Color(7, 7, 7), new Material(0.1, 1, 99)));

		sc.setAmbientLight(new AmbientLight(new Color(0, 0, 0), 0));
		Render ren = new Render(sc, new ImageWriter("simColor2", 5000, 5000, 5000, 5000));
		ren.renderImage();
		ren.get_imageWriter().writeToimage();
	}
	
	public void testBasicRendering(){
		
		Scene scene = new Scene("a");
		
		scene.addGeometries(new Sphere(new Point3D(0.0, 0.0, -150),50, new Color(java.awt.Color.blue), new Material()));
		
		Triangle triangle = new Triangle(new Point3D( 100, 0, -149),
				 						 new Point3D(  0, 100, -149),
				 						 new Point3D( 100, 100, -149), new Color(java.awt.Color.red), new Material());
		
		Triangle triangle2 = new Triangle(new Point3D( 100, 0, -149),
				 			 			  new Point3D(  0, -100, -149),
				 			 			  new Point3D( 100,-100, -149), new Color(java.awt.Color.green), new Material());
		
		Triangle triangle3 = new Triangle(new Point3D(-100, 0, -149),
				 						  new Point3D(  0, 100, -149),
				 						  new Point3D(-100, 100, -149), new Color(java.awt.Color.orange), new Material());
		
		Triangle triangle4 = new Triangle(new Point3D(-100, 0, -149),
				 			 			  new Point3D(  0,  -100, -149),
				 			 			  new Point3D(-100, -100, -149), new Color(java.awt.Color.pink), new Material());
		
		scene.addGeometries(triangle);
		scene.addGeometries(triangle2);
		scene.addGeometries(triangle3);
		scene.addGeometries(triangle4);
		Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, -1));
		AmbientLight amb = new AmbientLight(new Color(0, 0, 0), 1);
		scene.setBackground(new Color());
		scene.setAmbientLight(amb);
		scene.setCamera(cam, 150);
		ImageWriter imageWriter = new ImageWriter("Render test", 500, 500, 500, 500);
		
		Render render = new Render(scene, imageWriter);
		
		render.renderImage();
		render.printGrid(10);
		imageWriter.writeToimage();

		
	}

	public void testAddingLightSources(){
		/*
		pointLight pl = new pointLight(new pointLight(new Color(255,100,100), new Point3D(-200, -200, -100), 
							   0, 0.000001, 0.0000005));
		SpotLight sl = new SpotLight(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -100), 
				0, 0.00001, 0.000005,  new Vector()));
		
		DirectionalLight dl = new DirectionalLight(new Color(255, 100, 100),new Vector());
		*/
	}

}
