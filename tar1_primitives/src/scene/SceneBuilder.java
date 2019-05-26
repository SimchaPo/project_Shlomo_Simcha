package scene;

import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import parser.SceneDescriptor;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;

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

	public double[] stringSplitter(String str) {
		String[] subStr;
		String delimeter = " ";
		int i = 0;
		subStr = str.split(delimeter);
		double[] todble = new double[3];
		for (String s : subStr) {
			todble[i] = Double.parseDouble(s);
			i++;
		}
		return todble;
	}

//	public void fielfInitialeizer(Object obj[], String[] _str) {
//		int oLen = obj.length;
//		// int sLen=_str.length;
//		for (int i = 0; i < oLen; i++) {
//			obj[i] = _str[i];
//		}
//	}

	public Scene loadSceneFromFile(File _file) throws IOException, SAXException, ParserConfigurationException {
		_sceneDesc.InitializeFromXMLstring(_file);
		double imWid = 0.0, imhig = 0.0, screenDist = 0.0, k = 0.0, tmp = 0.0;
		AmbientLight _ambColor;
		Point3D[] trianglePnts = new Point3D[3];
		Point3D _P0 = null;
		Vector _VTo = null, _VUp = null;
		Sphere spheres = new Sphere(new Point3D(0.0, 0.0, 0.0), 1.0);
		Triangle triangles = new Triangle(new Point3D(0.0, 4.0, 0.0), new Point3D(7.0, -1.0, 0.0),
				new Point3D(2.0, 2.0, 0.0));
		double[] rgb = new double[3];
		for (Map.Entry<String, String> entry : _sceneDesc.get_sceneAttributes().entrySet()) {
			if ("background-color" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_scene.set_background(new Color(rgb[0], rgb[0], rgb[3]));
			} else if ("screen-width" == entry.getKey()) {
				imWid = Double.parseDouble(entry.getValue());
			} else if ("screen-height" == entry.getKey()) {
				imhig = Double.parseDouble(entry.getValue());
			} else if ("screen-dist" == entry.getKey()) {
				screenDist = Double.parseDouble(entry.getValue());
			}
		}
		for (Map.Entry<String, String> entry : _sceneDesc.get_ambientLightAttributes().entrySet()) {
			if ("color" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
			} else {
				k = Double.parseDouble(entry.getValue());
			}
			_ambColor = new AmbientLight(new Color(rgb[0], rgb[1], rgb[2]), k);
			_scene.set_ambientLight(_ambColor);
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
		_scene.set_camera(new Camera(_P0, _VUp, _VTo), screenDist);
		ListIterator<Map<String, String>> geometriesIterator = _sceneDesc.get_spheres().listIterator();
		while (geometriesIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) geometriesIterator
					.next();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				boolean istrue = false;
				if ("center" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					_P0 = new Point3D(rgb[0], rgb[1], rgb[2]);
				}
				if ("emmission" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					istrue = true;
				} else {
					tmp = Double.parseDouble(entry.getValue());
				}
				if (istrue) {
					spheres = new Sphere(_P0, tmp, new Color(rgb[0], rgb[1], rgb[2]));
				} else
					spheres = new Sphere(_P0, tmp);
			}
			_scene.addGeometries(spheres);
		}
		geometriesIterator = _sceneDesc.get_triangles().listIterator();
		while (geometriesIterator.hasNext()) {
			Map<java.lang.String, java.lang.String> map = (Map<java.lang.String, java.lang.String>) geometriesIterator
					.next();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				boolean istrue = false;
				if ("p0" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[0] = new Point3D(rgb[0], rgb[1], rgb[2]);
				} else if ("p1" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[1] = new Point3D(rgb[0], rgb[1], rgb[2]);
				} else {
					rgb = stringSplitter(entry.getValue());
					trianglePnts[2] = new Point3D(rgb[0], rgb[1], rgb[2]);

				}
				if ("emmission" == entry.getKey()) {
					rgb = stringSplitter(entry.getValue());
					istrue = true;
				}
				if (istrue) {
					triangles = new Triangle(trianglePnts[0], trianglePnts[1], trianglePnts[2],
							new Color(rgb[0], rgb[1], rgb[2]));
				} else
					triangles = new Triangle(trianglePnts[0], trianglePnts[1], trianglePnts[2]);
			}
			_scene.addGeometries(triangles);
			_imageWriter = new ImageWriter(_filePath, imWid, imhig, 500, 500);
		}
		return _scene;
	}
}
