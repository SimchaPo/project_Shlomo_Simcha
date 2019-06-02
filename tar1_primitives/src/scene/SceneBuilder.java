package scene;

import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import parser.SceneDescriptor;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;

/**
 * The class define scene & image writer by using XML document that user gave
 * 
 * @author meerzon shlomo & podolsky simha
 *
 */
public class SceneBuilder {
	SceneDescriptor _sceneDesc;
	Scene _scene;
	ImageWriter _imageWriter;
	String _filePath;

	public SceneBuilder(SceneDescriptor sD, Scene s, ImageWriter imW, String fPth) {
		_sceneDesc = sD;
		_scene = s;
		_imageWriter = imW;
		_filePath = fPth;
	}

	public SceneBuilder() {
		_sceneDesc = new SceneDescriptor();
		_scene = new Scene();
		_imageWriter = new ImageWriter("pic1", 500, 500, 500, 500);
	}

	public double[] stringSplitter(String str) {
		String delimeter = " ";
		int i = 0;
		String[] subStr = str.split(delimeter);
		double[] todble = new double[3];
		for (String s : subStr) {
			todble[i] = Double.parseDouble(s);
			i++;
		}
		return todble;
	}

	public Scene loadSceneFromFile(File _file) throws IOException, SAXException, ParserConfigurationException {
		_sceneDesc.InitializeFromXMLstring(_file);
		double imWid = 0.0, imhig = 0.0, screenDist = 0.0, k = 0.0, tmp = 0.0;
		AmbientLight _ambColor;
		Color C1 = new Color();
		Color C2 = new Color();
		Point3D[] trianglePnts = new Point3D[3];
		Point3D _P0 = new Point3D(0, 0, 0);
		Vector _VTo = new Vector(0, 0, -1), _VUp = new Vector(0, 1, 0);
		Sphere spheres = new Sphere(new Point3D(0.0, 0.0, 0.0), 1.0);
		Triangle triangles = new Triangle(new Point3D(0.0, 4.0, 0.0), new Point3D(7.0, -1.0, 0.0),
				new Point3D(2.0, 2.0, 0.0));
		double[] rgb = new double[3];
		for (Map.Entry<String, String> entry : _sceneDesc.get_sceneAttributes().entrySet()) {
			if ("background-color" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				System.out.println(rgb[0] + " " + rgb[1] + " " + rgb[2]);
				_scene.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
			} else if ("screen-width" == entry.getKey()) {
				imWid = Double.parseDouble(entry.getValue());
			} else if ("screen-height" == entry.getKey()) {
				imhig = Double.parseDouble(entry.getValue());
			} else if ("screen-dist" == entry.getKey()) {
				screenDist = Double.parseDouble(entry.getValue());
			}
		}
		ListIterator<Map<String, String>> lightsIterator = _sceneDesc.get_lightLst().listIterator();
		while (lightsIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) lightsIterator
					.next();
			boolean spotLight = false;
			boolean directLight = false;
			double kCTmp = 0.0, kLTmp = 0.0, kQTmp = 0.0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if ("direction" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					_VTo = new Vector(rgb[0], rgb[1], rgb[2]);
					directLight = true;
				} else if ("kC" == entry.getKey()) {
					kCTmp = Double.parseDouble(entry.getValue());
					spotLight = true;
				} else if ("kL" == entry.getKey()) {
					kLTmp = Double.parseDouble(entry.getValue());
				} else if ("kQ" == entry.getKey()) {
					kQTmp = Double.parseDouble(entry.getValue());
				} else if ("light-color" == entry.getKey()) {
					System.out.println(entry.getValue());
					rgb = stringSplitter(entry.getValue());
					C2.setColor(rgb[0], rgb[1], rgb[2]);
				} else if ("point" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					_P0 = new Point3D(rgb[0], rgb[1], rgb[2]);
				}
			}
			if (directLight) {
				if (spotLight) {
					_scene.setLights(new SpotLight(_P0, kCTmp, kLTmp, kQTmp, C2, _VTo));
				} else {
					_scene.setLights(new DirectionalLight(C2, _VTo));
				}
			} else {
				_scene.setLights(new PointLight(_P0, kCTmp, kLTmp, kQTmp, C2));
			}
		}

		for (Map.Entry<String, String> entry : _sceneDesc.get_ambientLightAttributes().entrySet()) {
			if ("color" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
			} else {
				k = Double.parseDouble(entry.getValue());
			}
			_ambColor = new AmbientLight(new Color(rgb[0], rgb[1], rgb[2]), k);
			_scene.setAmbientLight(_ambColor);
		}
		for (Map.Entry<String, String> entry : _sceneDesc.get_cameraAttributes().entrySet()) {
			if ("p0" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_P0 = new Point3D(rgb[0], rgb[1], rgb[2]);
			} else if ("vTo" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_VTo = new Vector(rgb[0], rgb[1], rgb[2]);
			} else if ("pUp" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_VUp = new Vector(rgb[0], rgb[1], rgb[2]);
			}
		}
		_scene.setCamera(new Camera(_P0, _VUp, _VTo), screenDist);

		ListIterator<Map<String, String>> sphereIterator = _sceneDesc.get_spheres().listIterator();
		while (sphereIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) sphereIterator
					.next();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				boolean istrue = false;
				if ("center" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					_P0 = new Point3D(rgb[0], rgb[1], rgb[2]);
				}
				if ("emmission" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					C1.setColor(rgb[0], rgb[1], rgb[2]);
					istrue = true;
				} else if ("material" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
				} else if ("radius" == entry.getKey()) {
					tmp = Double.parseDouble(entry.getValue());
				}
				if (istrue) {
					spheres = new Sphere(_P0, tmp, C1, new Material(rgb[0], rgb[1], (int) rgb[2]));
				} else
					spheres = new Sphere(_P0, tmp);
			}
			_scene.addGeometries(spheres);

		}
		ListIterator<Map<String, String>> triangleIterator = _sceneDesc.get_triangles().listIterator();
		while (triangleIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) triangleIterator
					.next();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				boolean istrue = false;
				if ("p0" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[0] = new Point3D(rgb[0], rgb[1], rgb[2]);
				} else if ("p1" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[1] = new Point3D(rgb[0], rgb[1], rgb[2]);
				} else if ("p2" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[2] = new Point3D(rgb[0], rgb[1], rgb[2]);

				}
				if ("emmission" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					C1.setColor(rgb[0], rgb[1], rgb[2]);
					istrue = true;
				} else if ("material" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
				}
				if (istrue) {
					triangles = new Triangle(trianglePnts[0], trianglePnts[1], trianglePnts[2], C1,
							new Material(rgb[0], rgb[1], (int) rgb[2]));
				}
				_scene.addGeometries(triangles);
				_imageWriter = new ImageWriter(_filePath, imWid, imhig, 500, 500);
			}

			return _scene;
		}
		return _scene;
	}

	public SceneDescriptor getSceneDesc() {
		return _sceneDesc;
	}

	public Scene getScene() {
		return _scene;
	}

	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	public String getFilePath() {
		return _filePath;
	}
}