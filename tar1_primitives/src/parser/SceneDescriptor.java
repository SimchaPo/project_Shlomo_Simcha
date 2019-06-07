package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * The class describe the Scene from XML document using SAX parser
 * 
 * @author meerz
 *
 */
public class SceneDescriptor {
	public static final String EMPTY_STRING = "";
	public static final Map<String, String> EMPTY_MAP = new HashMap<String, String>();
	public static final List<Map<String, String>> EMPTY_LIST = new ArrayList<Map<String, String>>();
	public static final SceneDescriptor EMPTY_Descriptor = new SceneDescriptor(EMPTY_MAP, EMPTY_MAP, EMPTY_MAP,
			EMPTY_LIST, EMPTY_LIST, EMPTY_LIST);
	private Map<String, String> _sceneAttributes;
	private Map<String, String> _cameraAttributes;
	private Map<String, String> _ambientLightAttributes;
	private List<Map<String, String>> _spheres;
	private List<Map<String, String>> _triangles;
	private List<Map<String, String>> _lightLst;

	/********** constructors *******/
	public SceneDescriptor() {
		_sceneAttributes = new HashMap<String, String>();
		_cameraAttributes = new HashMap<String, String>();
		_ambientLightAttributes = new HashMap<String, String>();
		_spheres = new ArrayList<Map<String, String>>();
		_triangles = new ArrayList<Map<String, String>>();
		_lightLst = new ArrayList<Map<String, String>>();
	}

	public SceneDescriptor(SceneDescriptor _otherSceneDescriptor) {
		this();
		if (!_otherSceneDescriptor.isEmpty()) {
			_sceneAttributes.putAll(_otherSceneDescriptor._sceneAttributes);
			_cameraAttributes.putAll(_otherSceneDescriptor._cameraAttributes);
			_ambientLightAttributes.putAll(_otherSceneDescriptor._ambientLightAttributes);
			_spheres.addAll(_otherSceneDescriptor._spheres);
			_triangles.addAll(_otherSceneDescriptor._triangles);
			_lightLst.addAll(_otherSceneDescriptor._lightLst);
		}
	}

	public SceneDescriptor(Map<String, String> get_sceneMap, Map<String, String> get_cameraMap,
			Map<String, String> get_ambientLightMap, List<Map<String, String>> get_sphereLst,
			List<Map<String, String>> get_triangleLst, List<Map<String, String>> get_lightLst) {
		this();
		if (get_sceneMap != EMPTY_MAP || get_cameraMap != EMPTY_MAP || get_ambientLightMap != EMPTY_MAP) {
			_sceneAttributes.putAll(get_sceneMap);
			_cameraAttributes.putAll(get_cameraMap);
			_ambientLightAttributes.putAll(get_ambientLightMap);
			for (Map<String, String> sphere : get_sphereLst) {
				_spheres.add(new HashMap<String, String>(sphere));
			}
			for (Map<String, String> triangle : get_triangleLst) {
				_triangles.add(new HashMap<String, String>(triangle));
			}
			for (Map<String, String> light : get_lightLst) {
				_lightLst.add(new HashMap<String, String>(light));
			}
		}
	}

	public void InitializeFromXMLstring(File _file) throws IOException, SAXException, ParserConfigurationException {
		SceneXmlParser doc = new SceneXmlParser();
		doc.parserInit(_file);
		SceneDescriptor _sceneD = new SceneDescriptor(doc.getParserDescriptor());
		this._sceneAttributes.putAll(_sceneD._sceneAttributes);
		this._ambientLightAttributes.putAll(_sceneD._ambientLightAttributes);
		this._cameraAttributes.putAll(_sceneD._cameraAttributes);
		this._spheres.addAll(_sceneD._spheres);
		this._triangles.addAll(_sceneD._triangles);
		this._lightLst.addAll(_sceneD._lightLst);
	}

	/**
	 * function checks if attributes are empty
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (this._sceneAttributes.isEmpty() || this._cameraAttributes.isEmpty()
				|| this._ambientLightAttributes.isEmpty()) {
			return true;
		}
		return false;
	}

	/********* getters *********/
	public Map<String, String> getSceneAttributes() {
		return _sceneAttributes;
	}

	public Map<String, String> getCameraAttributes() {
		return _cameraAttributes;
	}

	public Map<String, String> getAmbientLightAttributes() {
		return _ambientLightAttributes;
	}

	public List<Map<String, String>> getSpheres() {
		return _spheres;
	}

	public List<Map<String, String>> getTriangles() {
		return _triangles;
	}

	public List<Map<String, String>> getLightLst() {
		return _lightLst;
	}
}
