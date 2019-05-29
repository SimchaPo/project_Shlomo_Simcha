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
	public void testRenderImage1() {
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

	public void testRenderImage3() {
		Scene scene = new Scene("abc");
		AmbientLight amb = new AmbientLight(new Color(255, 255, 255), 0);
		scene.setBackground(new Color());
		scene.setAmbientLight(amb);
		Sphere sphere = new Sphere(new Point3D(0, 0, -150), 50, new Color(java.awt.Color.blue),
				new Material(10, 100, 20));
		scene.addGeometries(sphere);
		Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
		scene.setCamera(cam, 200);
		/*
		 * Triangle triangle = new Triangle(new Point3D(-125, -225, -260), new
		 * Point3D(-225, -125, -260), new Point3D(-225, -225, -270), new Color
		 * (java.awt.Color.green), new Material(10,100,4));
		 * 
		 * scene.addGeometries(triangle);
		 */

		scene.setLights(new spotLight(new Point3D(-200, -200, -100), 0, 0.1, 0.05, new Color(255, 100, 100),
				new Vector(0, 0, 1)));

		ImageWriter imageWriter = new ImageWriter("Spot test 3", 500, 500, 500, 500);

		Render render = new Render(scene, imageWriter);

		render.renderImage();
		render.get_imageWriter().writeToimage();
	}

	public void testRenderImage4() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight((new AmbientLight(new Color(15, 15, 15), 1.0)));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);

		// Sphere
		scene.setLights(new DirectionalLight(new Color(255, 128, 128), new Vector(0, -1, 0)));
		geometries.add(new Sphere(new Point3D(0, 0, 100), 60, new Color(17, 30, 108), new Material(0.7, 0.3, 40)));

		ImageWriter imageWriter = new ImageWriter("Sphere - DirectionalLight", 500, 500, 500, 500);

		Render render = new Render(scene, imageWriter);

		render.renderImage();
		// render.printGrid(50);
		render.get_imageWriter().writeToimage();
	}

	public void testRenderImage5() {
		// Two triangle
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);

		scene.setLights(new pointLight(new Point3D(10, 10, 20), 1, 0.0001, 0.000005, new Color(255, 100, 100)));

		geometries.add(
				new Triangle(new Point3D(-180, -180, 140), new Point3D(180, -180, 150), new Point3D(-250, 250, 150)));
		geometries.add(
				new Triangle(new Point3D(180, -180, 150), new Point3D(250, 250, 140), new Point3D(-250, 250, 150)));

		ImageWriter imageWriter = new ImageWriter("Two triangle with angle - PointLight", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);

		render.renderImage();
		// render.printGrid(50);
		render.get_imageWriter().writeToimage();

		// Sphere
		scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		geometries = new Geometries();
		scene.addGeometries(geometries);

		scene.setLights(new pointLight(new Point3D(-30, 30, 60), 1, 0.0001, 0.000005, new Color(255, 100, 100)));
		geometries.add(new Sphere(new Point3D(0, 0, 150), 80, new Color(17, 30, 108), new Material(0.4, 0.6, 40)));

		imageWriter = new ImageWriter("Sphere - PointLight", 500, 500, 500, 500);

		render = new Render(scene, imageWriter);

		render.renderImage();
		// render.printGrid(50);
		render.get_imageWriter().writeToimage();
	}

	public void testRenderImage6() {

		// Two triangle
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);

		scene.setLights(new spotLight(new Point3D(5, 5, 120), 1, 0.0001, 0.000005, new Color(255, 100, 100),
				new Vector(-1, -1, 8)));

		/*
		 * scene.setLightSource(new SpotLight(new Color(255, 100, 100), new Point3D(20,
		 * 20, 100), new Vector(-1, -1, 8), 1, 0.9, 0.005));
		 */
		geometries.add(
				new Triangle(new Point3D(-180, -180, 145), new Point3D(180, -180, 150), new Point3D(-250, 250, 150)));
		geometries.add(
				new Triangle(new Point3D(180, -180, 150), new Point3D(250, 250, 145), new Point3D(-250, 250, 150)));
		ImageWriter imageWriter = new ImageWriter("Two triangle with angle - SpotLight", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);

		render.renderImage();
		// render.printGrid(50);
		render.get_imageWriter().writeToimage();

		// Sphere
		scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		geometries = new Geometries();
		scene.addGeometries(geometries);

		scene.setLights(new spotLight(new Point3D(-50, 50, 20), 1, 0.0001, 0.000005, new Color(255, 100, 100),
				new Vector(1, -1, 2)));
		geometries.add(new Sphere(new Point3D(0, 0, 150), 80, new Color(17, 30, 108), new Material(0.5, 1.5, 40)));

		imageWriter = new ImageWriter("Sphere - SpotLight", 500, 500, 500, 500);

		render = new Render(scene, imageWriter);

		render.renderImage();
		// render.printGrid(50);
		render.get_imageWriter().writeToimage();
	}
}
