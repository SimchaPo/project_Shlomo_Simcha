package scene;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.ToDoubleBiFunction;

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

	public SceneBuilder(SceneDescriptor _sD, Scene _s, ImageWriter _imW, String _fPth) {
		_sceneDesc = _sD;
		_scene = _s;
		_imageWriter = _imW;
		_filePath = _fPth;
	}

	public double[] stringSplitter(String _str) {
		String[] subStr;
		String delimeter = " ";
		int i = 0;
		subStr = _str.split(delimeter);
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
		Color _backcolor;
		double _imWid = 0.0, imhig = 0.0, screenDist = 0.0, k = 0.0;
		AmbientLight _ambColor;
		Camera _cam;
		Point3D _P0 = null;
		Vector _VTo = null, _VUp = null;
		Sphere[] _spheres;
		Triangle[] _triangles;
		double[] rgb = new double[3];
		for (Map.Entry<String, String> entry : _sceneDesc.get_sceneAttributes().entrySet()) {
			if ("background-color" == entry.getKey()) {
				rgb = stringSplitter(entry.getValue());
				_scene.setBackground(new Color(rgb[0], rgb[0], rgb[3]));
			} else if ("screen-width" == entry.getKey()) {
				_imWid = Double.parseDouble(entry.getValue());
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
		return _scene;
	}
}
