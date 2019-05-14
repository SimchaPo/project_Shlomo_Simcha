package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class SceneDescriptor {
	public static final String EMPTY_STRING = "";
	public static final Map<String, String> EMPTY_MAP = new HashMap<String, String>();
	public static final List<Map<String, String>> EMPTY_LIST = new ArrayList<Map<String, String>>();
	public static final SceneDescriptor EMPTY_Descriptor = new SceneDescriptor(EMPTY_MAP, EMPTY_MAP, EMPTY_MAP,
			EMPTY_LIST, EMPTY_LIST);
	Map<String, String> _sceneAttributes;
	Map<String, String> _cameraAttributes;
	Map<String, String> _ambientLightAttributes;
	List<Map<String, String>> _spheres;
	List<Map<String, String>> _triangles;

	public SceneDescriptor() {
		_sceneAttributes = new HashMap<String, String>();
		_cameraAttributes = new HashMap<String, String>();
		_ambientLightAttributes = new HashMap<String, String>();
		_spheres = new ArrayList<Map<String, String>>();
		_triangles = new ArrayList<Map<String, String>>();
	}

	public SceneDescriptor(SceneDescriptor _otherSceneDescriptor) {
		this();
		if (!_otherSceneDescriptor.isEmpty()) {
			_sceneAttributes.putAll(_otherSceneDescriptor._sceneAttributes);
			_cameraAttributes.putAll(_otherSceneDescriptor._cameraAttributes);
			_ambientLightAttributes.putAll(_otherSceneDescriptor._ambientLightAttributes);
			_spheres.addAll(_otherSceneDescriptor._spheres);
			_triangles.addAll(_otherSceneDescriptor._triangles);
		}
	}

//	public SceneDescriptor(SceneXmlParser _obj) {
//		
//	}

	public SceneDescriptor(Map<String, String> get_sceneMap, Map<String, String> get_cameraMap,
			Map<String, String> get_ambientLightMap, List<Map<String, String>> get_sphereLst,
			List<Map<String, String>> get_triangleLst) {
		this();
		if (get_sceneMap != EMPTY_MAP || get_cameraMap != EMPTY_MAP || get_ambientLightMap != EMPTY_MAP) {
			_sceneAttributes.putAll(get_sceneMap);
			_cameraAttributes.putAll(get_cameraMap);
			_ambientLightAttributes.putAll(get_ambientLightMap);
			_spheres.addAll(get_sphereLst);
			_triangles.addAll(get_triangleLst);
		}
	}

	public SceneDescriptor InitializeFromXMLstring() throws IOException, SAXException, ParserConfigurationException {
		SceneXmlParser doc = new SceneXmlParser();
		doc.parserInit();
		SceneDescriptor _sceneD = new SceneDescriptor(doc.getParserDescriptor());
		return _sceneD;
	}

	public boolean isEmpty() {
		if (this._sceneAttributes.isEmpty() || this._cameraAttributes.isEmpty()
				|| this._ambientLightAttributes.isEmpty()) {
			return true;
		} else
			return false;
	}

	public Map<String, String> get_sceneAttributes() {
		return _sceneAttributes;
	}

	public Map<String, String> get_cameraAttributes() {
		return _cameraAttributes;
	}

	public Map<String, String> get_ambientLightAttributes() {
		return _ambientLightAttributes;
	}

	public List<Map<String, String>> get_spheres() {
		return _spheres;
	}

	public List<Map<String, String>> get_triangles() {
		return _triangles;
	}

}
