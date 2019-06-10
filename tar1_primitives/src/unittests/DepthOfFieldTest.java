package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import junit.framework.TestCase;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.DepthOfField;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class DepthOfFieldTest extends TestCase {
//	public void testDepthOfFieldTest() {
//		Scene scene = new Scene("abc");
//		scene.setBackground(new Color());
//		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
//		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 200);
//		Geometries geometries = new Geometries();
//		scene.addGeometries(geometries);
//		Sphere sphere = new Sphere(new Point3D(0,50, -575), 200, new Color(70,70,70), new Material(0.5,0.5,40,0,0.3));
//		scene.addGeometries(sphere);
//		Triangle triangle1 = new Triangle(new Point3D(-200, -225, -500), new Point3D(200, -225, -500),
//				new Point3D(-200, -225, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		Triangle triangle2 = new Triangle(new Point3D(200, -225, -650), new Point3D(200, -225, -500),
//				new Point3D(-200, -225, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		Triangle triangle3 = new Triangle(new Point3D(-200, -150, -500), new Point3D(200, -150, -500),
//				new Point3D(-200, -150, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		Triangle triangle4 = new Triangle(new Point3D(200, -150, -650), new Point3D(200, -150, -500),
//				new Point3D(-200, -150, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		
//		Triangle triangle5 = new Triangle(new Point3D(-200, -225, -500), new Point3D(200, -225, -500),
//				new Point3D(-200, -150, -500), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		Triangle triangle6 = new Triangle(new Point3D(-200, -150, -500), new Point3D(200, -150, -500),
//				new Point3D(200, -225, -500), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		scene.addGeometries(triangle1, triangle2, triangle3, triangle4, triangle5, triangle6);
//		
//		sphere = new Sphere(new Point3D(-400,50, -375), 200, new Color(70,70,70), new Material(0.5,0.5,40,0,0.3));
//		scene.addGeometries(sphere);
//		triangle1 = new Triangle(new Point3D(-600, -225, -300), new Point3D(-200, -225, -300),
//				new Point3D(-600, -225, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		 triangle2 = new Triangle(new Point3D(-200, -225, -450), new Point3D(-200, -225, -300),
//				new Point3D(-600, -225, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		 triangle3 = new Triangle(new Point3D(-600, -150, -300), new Point3D(-200, -150, -300),
//				new Point3D(-600, -150, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		 triangle4 = new Triangle(new Point3D(-200, -150, -450), new Point3D(-200, -150, -300),
//				new Point3D(-600, -150, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		
//		 triangle5 = new Triangle(new Point3D(-600, -225, -300), new Point3D(-200, -225, -300),
//				new Point3D(-600, -150, -300), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		 triangle6 = new Triangle(new Point3D(-600, -150, -300), new Point3D(-200, -150, -300),
//				new Point3D(-200, -225, -300), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
//		scene.addGeometries(triangle1, triangle2, triangle3, triangle4, triangle5, triangle6);
//		scene.setLights(new SpotLight(new Point3D(-200, -200, -350), 1, 0.00001, 0.000005, new Color(255, 100, 100),
//				new Vector(2, 2, -3)));
//		scene.setLights(new SpotLight(new Point3D(0,-150,-400), 1, 0.0001, 0.00005, new Color(255, 100, 100), new Vector(0, 2, -3)));
//		scene.setLights(new SpotLight(new Point3D(-600, -200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
//				new Vector(2, 2, -3)));
//		scene.setLights(new SpotLight(new Point3D(-400,-150,-200), 1, 0.0001, 0.00005, new Color(255, 100, 100), new Vector(0, 2, -3)));
//		ImageWriter imageWriter = new ImageWriter("depth", 5000, 5000, 5000, 5000);
//		Render render = new Render(scene, imageWriter);
//		DepthOfField depth = new DepthOfField(205, render);
//		depth.addFocus();
//	//	render.renderImage();
//		render.getImageWriter().writeToImage();
//	}
	
	public void testDepthOfFieldTest2() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 400), new Vector(0, 1, 0), new Vector(0, 0, -1)), 200);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere = new Sphere(new Point3D(0,50, -575), 200, new Color(70,70,70), new Material(0.5,0.5,40,0,0.3));
		scene.addGeometries(sphere);
		Triangle triangle1 = new Triangle(new Point3D(-200, -225, -500), new Point3D(200, -225, -500),
				new Point3D(-200, -225, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle2 = new Triangle(new Point3D(200, -225, -650), new Point3D(200, -225, -500),
				new Point3D(-200, -225, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle3 = new Triangle(new Point3D(-200, -150, -500), new Point3D(200, -150, -500),
				new Point3D(-200, -150, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle4 = new Triangle(new Point3D(200, -150, -650), new Point3D(200, -150, -500),
				new Point3D(-200, -150, -650), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		
		Triangle triangle5 = new Triangle(new Point3D(-200, -225, -500), new Point3D(200, -225, -500),
				new Point3D(-200, -150, -500), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle6 = new Triangle(new Point3D(-200, -150, -500), new Point3D(200, -150, -500),
				new Point3D(200, -225, -500), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		scene.addGeometries(triangle1, triangle2, triangle3, triangle4, triangle5, triangle6);
		
		sphere = new Sphere(new Point3D(-400,50, -375), 200, new Color(70,70,70), new Material(0.5,0.5,40,0,0.3));
		scene.addGeometries(sphere);
		triangle1 = new Triangle(new Point3D(-600, -225, -300), new Point3D(-200, -225, -300),
				new Point3D(-600, -225, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		 triangle2 = new Triangle(new Point3D(-200, -225, -450), new Point3D(-200, -225, -300),
				new Point3D(-600, -225, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		 triangle3 = new Triangle(new Point3D(-600, -150, -300), new Point3D(-200, -150, -300),
				new Point3D(-600, -150, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		 triangle4 = new Triangle(new Point3D(-200, -150, -450), new Point3D(-200, -150, -300),
				new Point3D(-600, -150, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		
		 triangle5 = new Triangle(new Point3D(-600, -225, -300), new Point3D(-200, -225, -300),
				new Point3D(-600, -150, -300), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		 triangle6 = new Triangle(new Point3D(-600, -150, -300), new Point3D(-200, -150, -300),
				new Point3D(-200, -225, -300), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		scene.addGeometries(triangle1, triangle2, triangle3, triangle4, triangle5, triangle6);
		scene.setLights(new SpotLight(new Point3D(-200, -200, -350), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(2, 2, -3)));
		scene.setLights(new SpotLight(new Point3D(0,-150,-400), 1, 0.0001, 0.00005, new Color(255, 100, 100), new Vector(0, 2, -3)));
		scene.setLights(new SpotLight(new Point3D(-600, -200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(2, 2, -3)));
		scene.setLights(new SpotLight(new Point3D(-400,-150,-200), 1, 0.0001, 0.00005, new Color(255, 100, 100), new Vector(0, 2, -3)));
		ImageWriter imageWriter = new ImageWriter("depth2", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		DepthOfField depth = new DepthOfField(300, render);
		depth.addFocus();
	//	render.renderImage();
		render.getImageWriter().writeToImage();
	}
	public void testDepthOfFieldTest3() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 500);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere = new Sphere(new Point3D(-2500,0, -3000), 400, new Color(0,0,100), new Material(0.5,0.5,40,0,0));
		scene.addGeometries(sphere);
		
		sphere = new Sphere(new Point3D(0,0, -2500), 150, new Color(70,70,70), new Material(0.5,0.5,40,0,0));
		scene.addGeometries(sphere);
		
		sphere = new Sphere(new Point3D(300,0, -1500), 50, new Color(100,150,0), new Material(0.5,0.5,40,0,0));
		scene.addGeometries(sphere);
		scene.setLights(new SpotLight(new Point3D(-200, -200, -350), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(2, 2, -3)));
		scene.setLights(new SpotLight(new Point3D(0,-150,-400), 1, 0.0001, 0.00005, new Color(255, 100, 100), new Vector(0, 2, -3)));
		ImageWriter imageWriter = new ImageWriter("depth3", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		DepthOfField depth = new DepthOfField(2000, render);
		depth.addFocus();
	//	render.renderImage();
		render.getImageWriter().writeToImage();
	}
}
