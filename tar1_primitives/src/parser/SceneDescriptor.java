package parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class SceneDescriptor {
	Map<String, String> _sceneAttributes;
	Map<String, String> _cameraAttributes;
	Map<String, String> _ambientLightAttributes;
	List<Map<String, String>> _spheres;
	List<Map<String, String>> _triangles;

	public SceneDescriptor(SceneDescriptor _otherSceneDescriptor) {
		_sceneAttributes = _otherSceneDescriptor._sceneAttributes;
		_cameraAttributes = _otherSceneDescriptor._cameraAttributes;
		_ambientLightAttributes = _otherSceneDescriptor._ambientLightAttributes;
		_spheres = _otherSceneDescriptor._spheres;
		_triangles = _otherSceneDescriptor._triangles;
	}

//	public SceneDescriptor(SceneXmlParser _obj) {
//		
//	}

	public SceneDescriptor(Map<String, String> get_sceneMap, Map<String, String> get_cameraMap,
			Map<String, String> get_ambientLightMap, List<Map<String, String>> get_sphereLst,
			List<Map<String, String>> get_triangleLst) {
		_sceneAttributes = get_sceneMap;
		_cameraAttributes = get_cameraMap;
		_ambientLightAttributes = get_ambientLightMap;
		_spheres = get_sphereLst;
		_triangles = get_triangleLst;
	}

	public SceneDescriptor InitializeFromXMLstring() throws IOException, SAXException, ParserConfigurationException {
		SceneXmlParser doc = new SceneXmlParser();
		SceneDescriptor _sceneD = new SceneDescriptor(doc.handler.getTmp());
		return _sceneD;
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
