package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import parser.SceneXmlParser;

public class SceneDescriptor {
	Map<String, String> _sceneAttributes;
	Map<String, String> _cameraAttributes;
	Map<String, String> _ambientLightAttributes;
	List<Map<String, String>> _spheres;
	List<Map<String, String>> _triangles;

	public SceneDescriptor() {
		_sceneAttributes = new HashMap<String, String>();
		_cameraAttributes = new HashMap<String, String>();
		_ambientLightAttributes = new HashMap<String, String>();
		_spheres = new ArrayList<Map<String, String>>(null);
		_triangles = new ArrayList<Map<String, String>>(null);
	}

	public SceneDescriptor(SceneXmlParser _obj) {
		_sceneAttributes = _obj._sceneD._sceneAttributes;
		_cameraAttributes = _obj._sceneD._cameraAttributes;
		_ambientLightAttributes = _obj._sceneD._ambientLightAttributes;
		_spheres = _obj._sceneD._spheres;
		_triangles = _obj._sceneD._triangles;
	}

	void InitializeFromXMLstring() throws IOException, SAXException, ParserConfigurationException {
		SceneXmlParser doc = new SceneXmlParser();
		this._sceneAttributes = doc._sceneD._sceneAttributes;
		this._cameraAttributes = doc._sceneD._cameraAttributes;
		this._ambientLightAttributes = doc._sceneD._ambientLightAttributes;
		this._spheres = doc._sceneD._spheres;
		this._triangles = doc._sceneD._triangles;
	}
}
