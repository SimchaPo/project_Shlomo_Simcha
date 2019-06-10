package unittests;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import junit.framework.TestCase;
import parser.XMLBuilder;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;
import scene.SceneBuilder;

public class RenderTest extends TestCase {
	/**
	 * draw in image with 4 triangles and a sphere
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
		Scene scene = new Scene("triangles and a sphere");
		scene.setBackground(new Color(75, 127, 190));
		scene.setAmbientLight(amb);
		scene.setCamera(cam, 150);
		scene.addGeometries(geometries);
		ImageWriter im = new ImageWriter("triangles and a sphere", 500, 500, 500, 500);
		Render ren = new Render(scene, im);
		ren.renderImage();
		ren.printGrid(10, java.awt.Color.red);
		ren.getImageWriter().writeToImage();
	}

	/**
	 * draw in image with 4 triangles and a sphere with different colors
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
		ImageWriter im = new ImageWriter("triangles and a sphere with different colors", 500, 500, 500, 500);
		Render ren = new Render(scene, im);
		ren.renderImage();
		ren.printGrid(10, java.awt.Color.white);
		ren.getImageWriter().writeToImage();
	}

	/**
	 * test for directional light, sphere
	 */
	public void testRenderImage3() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight((new AmbientLight(new Color(15, 15, 15), 1.0)));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		scene.setLights(new DirectionalLight(new Color(255, 128, 128), new Vector(0, -1, 0)));
		geometries.add(new Sphere(new Point3D(0, 0, 100), 60, new Color(17, 30, 108), new Material(0.7, 0.3, 40)));
		ImageWriter imageWriter = new ImageWriter("directional light, sphere", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test for point light, two triangle
	 */
	public void testRenderImage4() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		scene.setLights(new PointLight(new Point3D(10, 10, 20), 1, 0.0001, 0.000005, new Color(255, 100, 100)));
		geometries.add(
				new Triangle(new Point3D(-180, -180, 140), new Point3D(180, -180, 150), new Point3D(-250, 250, 150)));
		geometries.add(
				new Triangle(new Point3D(180, -180, 150), new Point3D(250, 250, 140), new Point3D(-250, 250, 150)));
		ImageWriter imageWriter = new ImageWriter("point light, two triangle", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test for point light, sphere
	 */
	public void testRenderImage5() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		scene.setLights(new PointLight(new Point3D(-30, 30, 60), 1, 0.0001, 0.000005, new Color(255, 100, 100)));
		geometries.add(new Sphere(new Point3D(0, 0, 150), 80, new Color(17, 30, 108), new Material(0.4, 0.6, 40)));
		ImageWriter imageWriter = new ImageWriter("point light, sphere", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test for spot light, two triangle
	 */
	public void testRenderImage6() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		scene.setLights(new SpotLight(new Point3D(5, 5, 120), 1, 0.0001, 0.000005, new Color(255, 100, 100),
				new Vector(-1, -1, 8)));
		geometries.add(
				new Triangle(new Point3D(-180, -180, 145), new Point3D(180, -180, 150), new Point3D(-250, 250, 150)));
		geometries.add(
				new Triangle(new Point3D(180, -180, 150), new Point3D(250, 250, 145), new Point3D(-250, 250, 150)));
		ImageWriter imageWriter = new ImageWriter("spot light, two triangle", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test for spot light, sphere
	 */
	public void testRenderImage7() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)), 150);
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		scene.setLights(new SpotLight(new Point3D(-50, 50, 20), 1, 0.0001, 0.000005, new Color(255, 100, 100),
				new Vector(1, -1, 2)));
		geometries.add(new Sphere(new Point3D(0, 0, 150), 80, new Color(17, 30, 108), new Material(0.5, 1.5, 40)));
		ImageWriter imageWriter = new ImageWriter("spot light, sphere", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test for spot light, sphere, XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XMLStreamException
	 * @throws TransformerException
	 */
	public void testRenderImage8()
			throws IOException, SAXException, ParserConfigurationException, TransformerException, XMLStreamException {
		XMLBuilder xmlBuilder = new XMLBuilder("spotLihet");
		xmlBuilder.WriteToFile();
		Scene sc = new Scene("abc");
		SceneBuilder scene = new SceneBuilder();
		File _file = new File(xmlBuilder.getFileName());
		sc = scene.loadSceneFromFile(_file);
		System.out.println(sc);
		Render render = new Render(sc, scene.getImageWriter());
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test shadow under sphere
	 */
	public void testRenderImage9() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 100);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere = new Sphere(new Point3D(0.0, 0.0, -1000), 500, new Color(0, 0, 100), new Material(1.5, 0.5, 40));
		scene.addGeometries(sphere);
		Triangle triangle1 = new Triangle(new Point3D(3500, 3500, -2000), new Point3D(-3500, -3500, -1000),
				new Point3D(3500, -3500, -2000), new Color(), new Material(1.5, 0.5, 40));
		Triangle triangle2 = new Triangle(new Point3D(3500, 3500, -2000), new Point3D(-3500, 3500, -1000),
				new Point3D(-3500, -3500, -1000), new Color(), new Material(1.5, 0.5, 40));
		scene.addGeometries(triangle1);
		scene.addGeometries(triangle2);
		scene.setLights(new SpotLight(new Point3D(200, 200, -100), 1, 0.000001, 0.0000005, new Color(255, 100, 100),
				new Vector(-2, -2, -3)));
		ImageWriter imageWriter = new ImageWriter("shadow under sphere", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test 1 recursive function
	 */
	public void testRenderImage10() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 300);
		Sphere sphere = new Sphere(new Point3D(0.0, 0.0, -1000), 500, new Color(0, 0, 100),
				new Material(1.5, 0.5, 40, 0, 0.8));
		scene.addGeometries(sphere);
		Sphere sphere2 = new Sphere(new Point3D(0.0, 0.0, -1000), 250, new Color(100, 20, 20),
				new Material(0.5, 0.5, 40, 0, 0));
		scene.addGeometries(sphere2);
		scene.setLights(new SpotLight(new Point3D(-200, -200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(2, 2, -3)));
		ImageWriter imageWriter = new ImageWriter("Recursive Test 1", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test 1 recursive function
	 */
	public void testRenderImage11() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 300);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere = new Sphere(new Point3D(-550, -500, -1000), 300, new Color(0, 0, 100),
				new Material(1, 1, 40, 0, 0.7));
		scene.addGeometries(sphere);
		Sphere sphere2 = new Sphere(new Point3D(-550, -500, -1000), 150, new Color(100, 20, 20),
				new Material(1, 1, 40, 0, 0));
		scene.addGeometries(sphere2);
		Triangle triangle = new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
				new Point3D(200, 200, -375), new Color(20, 20, 20), new Material(1, 1, 40, 1, 0));
		Triangle triangle2 = new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
				new Point3D(-1500, -1500, -1500), new Color(20, 20, 20), new Material(1, 1, 40, 1, 0));
		scene.addGeometries(triangle);
		scene.addGeometries(triangle2);
		scene.setLights(new SpotLight(new Point3D(200, 200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(-2, -2, -3)));
		ImageWriter imageWriter = new ImageWriter("Recursive Test 2", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test 3 recursive function
	 */
	public void testRenderImage12() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 300);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere = new Sphere(new Point3D(0, 0, -1000), 300, new Color(0, 0, 100),
				new Material(0.5, 0.5, 20, 0, 0.5));
		scene.addGeometries(sphere);
		Sphere sphere2 = new Sphere(new Point3D(0, 0, -1000), 150, new Color(100, 20, 20),
				new Material(0.5, 0.5, 20, 0, 0));
		scene.addGeometries(sphere2);
		Triangle triangle = new Triangle(new Point3D(2000, -1000, -1500), new Point3D(-1000, 2000, -1500),
				new Point3D(700, 700, -375), new Color(20, 20, 20), new Material(0.5, 0.5, 20, 1, 0));
		Triangle triangle2 = new Triangle(new Point3D(2000, -1000, -1500), new Point3D(-1000, 2000, -1500),
				new Point3D(-1000, -1000, -1500), new Color(20, 20, 20), new Material(0.5, 0.5, 20, 0.5, 0));
		scene.addGeometries(triangle);
		scene.addGeometries(triangle2);
		scene.setLights(new SpotLight(new Point3D(200, 200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(-2, -2, -3)));
		ImageWriter imageWriter = new ImageWriter("Recursive Test 3", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * draw flag
	 */
	public void testRenderImage13() {
		Scene scene = new Scene("abc");
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 100);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		scene.setBackground(new Color(java.awt.Color.white));
		Triangle triangle1 = new Triangle(new Point3D(-750, -500, -1000), new Point3D(750, -500, -1000),
				new Point3D(0, 1000, -1000), new Color(java.awt.Color.blue), new Material(0.5, 0.5, 20, 0, 0));
		Triangle triangle2 = new Triangle(new Point3D(-750, 500, -1000), new Point3D(750, 500, -1000),
				new Point3D(0, -1000, -1000), new Color(java.awt.Color.blue), new Material(0.5, 0.5, 20, 0, 0));
		Triangle triangle3 = new Triangle(new Point3D(-3500, -1500, -1000), new Point3D(3500, -1500, -1000),
				new Point3D(-3500, -1800, -1000), new Color(java.awt.Color.blue), new Material(0.5, 0.5, 20, 0, 0));
		Triangle triangle4 = new Triangle(new Point3D(-3500, -1800, -1000), new Point3D(3500, -1800, -1000),
				new Point3D(3500, -1500, -1000), new Color(java.awt.Color.blue), new Material(0.5, 0.5, 20, 0, 0));
		Triangle triangle5 = new Triangle(new Point3D(-3500, 1500, -1000), new Point3D(3500, 1500, -1000),
				new Point3D(-3500, 1800, -1000), new Color(java.awt.Color.blue), new Material(0.5, 0.5, 20, 0, 0));
		Triangle triangle6 = new Triangle(new Point3D(-3500, 1800, -1000), new Point3D(3500, 1800, -1000),
				new Point3D(3500, 1500, -1000), new Color(java.awt.Color.blue), new Material(0.5, 0.5, 20, 0, 0));
		scene.addGeometries(triangle1, triangle2, triangle3, triangle4, triangle5, triangle6);
		scene.setLights(new SpotLight(new Point3D(200, 200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(-2, -2, -3)));
		ImageWriter imageWriter = new ImageWriter("star", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	/**
	 * test shadow with triangle and sphere
	 */
	public void testRenderImage14() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 200);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere = new Sphere(new Point3D(0, 0, -1000), 500, new Color(0, 0, 100), new Material(1.5, 0.5, 40));
		scene.addGeometries(sphere);
		Triangle triangle = new Triangle(new Point3D(-125, -225, -260), new Point3D(-225, -125, -260),
				new Point3D(-225, -225, -270), new Color(0, 0, 100), new Material(1.5, 0.5, 40));
		scene.addGeometries(triangle);
		scene.setLights(new SpotLight(new Point3D(-200, -200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(2, 2, -3)));
		ImageWriter imageWriter = new ImageWriter("shadow with triangle and sphere", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}

	public void testRenderImage15() {
		Scene scene = new Scene("abc");
		scene.setBackground(new Color());
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 1.0));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)), 200);
		Geometries geometries = new Geometries();
		scene.addGeometries(geometries);
		Sphere sphere = new Sphere(new Point3D(0,50, -375), 200, new Color(70,70,70), new Material(0.5,0.5,40,0,0.3));
		scene.addGeometries(sphere);
		Triangle triangle1 = new Triangle(new Point3D(-200, -225, -300), new Point3D(200, -225, -300),
				new Point3D(-200, -225, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle2 = new Triangle(new Point3D(200, -225, -450), new Point3D(200, -225, -300),
				new Point3D(-200, -225, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle3 = new Triangle(new Point3D(-200, -150, -300), new Point3D(200, -150, -300),
				new Point3D(-200, -150, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle4 = new Triangle(new Point3D(200, -150, -450), new Point3D(200, -150, -300),
				new Point3D(-200, -150, -450), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		
		Triangle triangle5 = new Triangle(new Point3D(-200, -225, -300), new Point3D(200, -225, -300),
				new Point3D(-200, -150, -300), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		Triangle triangle6 = new Triangle(new Point3D(-200, -150, -300), new Point3D(200, -150, -300),
				new Point3D(200, -225, -300), new Color(0, 0, 100), new Material(0.5,0.5,40,0,0.3));
		scene.addGeometries(triangle1, triangle2, triangle3, triangle4, triangle5, triangle6);
		scene.setLights(new SpotLight(new Point3D(-200, -200, -150), 1, 0.00001, 0.000005, new Color(255, 100, 100),
				new Vector(2, 2, -3)));
		scene.setLights(new SpotLight(new Point3D(0,-150,-200), 1, 0.0001, 0.00005, new Color(255, 100, 100), new Vector(0, 2, -3)));
		ImageWriter imageWriter = new ImageWriter("15", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.renderImage();
		render.getImageWriter().writeToImage();
	}
}
