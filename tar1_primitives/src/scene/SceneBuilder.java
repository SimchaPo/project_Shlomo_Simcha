package scene;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import parser.SceneDescriptor;
import primitives.Color;
import renderer.ImageWriter;

public class SceneBuilder {
	SceneDescriptor _sceneDesc;
	Scene _scene;
	ImageWriter _imageWriter;
	String filePath;

	public SceneBuilder(SceneDescriptor _sD, Scene _s, ImageWriter _imW, String _fPth) {
		_sceneDesc = _sD;
		_scene = _s;
		_imageWriter = _imW;
		filePath = _fPth;
	}

	public String[] stringSplitter(String _str) {
		String[] subStr;
		String delimeter = " ";
		subStr = _str.split(delimeter);
		return subStr;
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
		double imWid,imhig;
		AmbientLight _ambColor;
		Camera _cam;
		Sphere[] _spheres;
		Triangle[] _triangles;
		
		
		return _scene;
	}
}
