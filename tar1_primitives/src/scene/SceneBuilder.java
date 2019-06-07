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
 * @author meerzon shlomo & podolsky simcha
 *
 */
public class SceneBuilder {
	SceneDescriptor _sceneDesc;
	Scene _scene;
	ImageWriter _imageWriter;
	String _filePath;

	/********* constructors ***********/
	public SceneBuilder(SceneDescriptor sD, Scene s, ImageWriter imW, String fPth) {
		_sceneDesc = sD;
		_scene = s;
		_imageWriter = imW;
		_filePath = fPth;
	}

	public SceneBuilder() {
		_sceneDesc = new SceneDescriptor();
		_scene = new Scene("abc");
		_imageWriter = new ImageWriter("pic1", 500, 500, 500, 500);
	}

	/**
	 * splits string of numbers to array
	 * 
	 * @param str
	 * @return
	 */
	public double[] stringSplitter(String str) {
		String delimeter = " ";
		int i = 0;
		String[] subStr = str.split(delimeter);
		double[] todble = new double[subStr.length];
		for (String s : subStr) {
			todble[i] = Double.parseDouble(s);
			i++;
		}
		return todble;
	}

	/**
	 * read scene from XML to scene element
	 * 
	 * @param _file
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Scene loadSceneFromFile(File _file) throws IOException, SAXException, ParserConfigurationException {
		_sceneDesc.InitializeFromXMLstring(_file);
		double imWid = 0.0, imhig = 0.0, screenDist = 0.0, k = 0.0, tmp = 0.0;

		AmbientLight _ambColor;
		Color C1 = new Color();
		Color C2 = new Color();
		Point3D[] trianglePnts = new Point3D[3];
		Point3D _P0 = new Point3D(0, 0, 0);
		Vector _VTo = new Vector(0, 0, -1), _VUp = new Vector(0, 1, 0);
		double[] rgb = new double[3];
		double[] mat = new double[5];
		for (Map.Entry<String, String> entry : _sceneDesc.getSceneAttributes().entrySet()) {
			if ("background-color" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_scene.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
			} else if ("screen-width" == entry.getKey()) {
				imWid = Double.parseDouble(entry.getValue());
			} else if ("screen-height" == entry.getKey()) {
				imhig = Double.parseDouble(entry.getValue());
			} else if ("screen-dist" == entry.getKey()) {
				screenDist = Double.parseDouble(entry.getValue());
			}
		}
		ListIterator<Map<String, String>> lightsIterator = _sceneDesc.getLightLst().listIterator();
		while (lightsIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) lightsIterator
					.next();
			boolean spotLight = false;
			boolean directLight = false;
			double kCTmp = 0.0, kLTmp = 0.0, kQTmp = 0.0;

			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					switch (entry.getKey()) {
					case "direction": {
						rgb = stringSplitter(entry.getValue());
						_VTo = new Vector(rgb[0], rgb[1], rgb[2]);
						directLight = true;
						break;
					}
					case "kC": {
						kCTmp = Double.parseDouble(entry.getValue());
						spotLight = true;
						break;
					}
					case "kL": {
						kLTmp = Double.parseDouble(entry.getValue());
						break;
					}
					case "kQ": {
						kQTmp = Double.parseDouble(entry.getValue());
						break;
					}
					case "light-color": {
						rgb = stringSplitter(entry.getValue());
						C2.setColor(rgb[0], rgb[1], rgb[2]);
						break;
					}
					case "point": {
						rgb = stringSplitter(entry.getValue());
						_P0 = new Point3D(rgb[0], rgb[1], rgb[2]);
						break;
					}
					default:
						break;
					}
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

		for (Map.Entry<String, String> entry : _sceneDesc.getAmbientLightAttributes().entrySet()) {
			if ("color" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
			} else if ("ka" == entry.getKey()) {
				k = Double.parseDouble(entry.getValue());
			}
		}
		_ambColor = new AmbientLight(new Color(rgb[0], rgb[1], rgb[2]), k);
		_scene.setAmbientLight(_ambColor);

		for (Map.Entry<String, String> entry : _sceneDesc.getCameraAttributes().entrySet()) {
			if ("p0" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_P0 = new Point3D(rgb[0], rgb[1], rgb[2]);
			} else if ("vTo" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_VTo = new Vector(rgb[0], rgb[1], rgb[2]);
			} else if ("vUp" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_VUp = new Vector(rgb[0], rgb[1], rgb[2]);
			}
		}
		_scene.setCamera(new Camera(_P0, _VUp, _VTo), screenDist);

		ListIterator<Map<String, String>> sphereIterator = _sceneDesc.getSpheres().listIterator();
		while (sphereIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) sphereIterator
					.next();
			boolean istrue = false;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if ("center" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					_P0 = new Point3D(rgb[0], rgb[1], rgb[2]);
				}
				if ("emmission" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					C1.setColor(rgb[0], rgb[1], rgb[2]);
				}
				if ("material" == entry.getKey()) {
					mat = stringSplitter(entry.getValue());
				}
				if ("radius" == entry.getKey()) {
					tmp = Double.parseDouble(entry.getValue());
				}
			}
			if (istrue) {
				_scene.addGeometries(new Sphere(_P0, tmp, C1, new Material(mat)));
			} else {
				_scene.addGeometries(new Sphere(_P0, tmp));
			}

		}
		ListIterator<Map<String, String>> triangleIterator = _sceneDesc.getTriangles().listIterator();
		while (triangleIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) triangleIterator
					.next();
			boolean istrue = false;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if ("p0" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[0] = new Point3D(rgb[0], rgb[1], rgb[2]);
				}
				if ("p1" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[1] = new Point3D(rgb[0], rgb[1], rgb[2]);
				}
				if ("p2" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[2] = new Point3D(rgb[0], rgb[1], rgb[2]);
				}
				if ("emmission" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					C1.setColor(rgb[0], rgb[1], rgb[2]);
					istrue = true;
				}
				if ("material" == entry.getKey()) {
					mat = stringSplitter(entry.getValue());
				}
			}
			if (istrue) {
				_scene.addGeometries(
						new Triangle(trianglePnts[0], trianglePnts[1], trianglePnts[2], C1, new Material(mat)));
			} else {
				_scene.addGeometries(new Triangle(trianglePnts[0], trianglePnts[1], trianglePnts[2]));
			}

		}
		_imageWriter = new ImageWriter(_filePath, imWid, imhig, 500, 500);
		return _scene;
	}

	/********* getters/setters **********/
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