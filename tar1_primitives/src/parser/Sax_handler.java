package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class implement scene building from XML document by using SAX for XML
 * parsing
 * 
 * @author meerz
 *
 */
public class Sax_handler extends DefaultHandler {
	static final String SCENE = "scene";
	static final String AMBIENT_LIGHT = "ambient-light";
	static final String LIGHT = "light";
	static final String POINT = "point";
	static final String VECTOR = "vector";
	static final String DIRECTION = "direction";
	static final String KC = "kC";
	static final String KL = "kL";
	static final String KQ = "kQ";
	static final String CAMERA = "camera";
	static final String GEOMETRIES = "geometries";
	static final String SPHERE = "sphere";
	static final String TRIANGLE = "triangle";
	static final String BACKGRAUND_COLOR = "background-color";
	static final String SCREEN_WIDTH = "screen-width";
	static final String SCREEN_HEIGHT = "screen-height";
	static final String SCREEN_DIST = "screen-dist";
	static final String COLOR = "color";
	static final String KA = "ka";
	static final String LIGHT_COLOR = "light-color";
	static final String P_0 = "p0";
	static final String V_TO = "vTo";
	static final String V_UP = "vUp";
	static final String CENTER = "center";
	static final String RADIUS = "radius";
	static final String EMMISSION = "emmission";
	static final String MATERIAL = "material";
	static final String P0 = "p0";
	static final String P1 = "p1";
	static final String P2 = "p2";
	private Map<String, String> _sphereMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _triangleMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _sceneMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _lightMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _cameraMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _ambientLightMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private List<Map<String, String>> _sphereLst = new ArrayList<Map<String, String>>(SceneDescriptor.EMPTY_LIST);
	private List<Map<String, String>> _triangleLst = new ArrayList<Map<String, String>>(SceneDescriptor.EMPTY_LIST);
	private List<Map<String, String>> _lightLst = new ArrayList<Map<String, String>>(SceneDescriptor.EMPTY_LIST);
	private String currentElm = "";
	SceneDescriptor tmp = SceneDescriptor.EMPTY_Descriptor;
	// private String currentFigure = null;
	// int count = 0;

	public void startDocument() throws SAXException {
		
	}

	public void startElement(String _nameSpace, String _localName, String _qName, Attributes atts) throws SAXException {
		// currentElm = _qName;

		if (_qName == SCENE) {
			_sceneMap.put(BACKGRAUND_COLOR, atts.getValue(BACKGRAUND_COLOR));
			_sceneMap.put(SCREEN_WIDTH, atts.getValue(SCREEN_WIDTH));
			_sceneMap.put(SCREEN_HEIGHT, atts.getValue(SCREEN_HEIGHT));
			_sceneMap.put(SCREEN_DIST, atts.getValue(SCREEN_DIST));
		}
		if (_qName == LIGHT) {
			_lightMap.put(LIGHT_COLOR, atts.getValue(LIGHT_COLOR));
			_lightMap.put(POINT, atts.getValue(POINT));
			_lightMap.put(KC, atts.getValue(KC));
			_lightMap.put(KL, atts.getValue(KL));
			_lightMap.put(KQ, atts.getValue(KQ));
			_lightMap.put(DIRECTION, atts.getValue(DIRECTION));
		}
		if (_qName == CAMERA) {
			_cameraMap.put(P_0, atts.getValue(P_0));
			_cameraMap.put(V_TO, atts.getValue(V_TO));
			_cameraMap.put(V_UP, atts.getValue(V_UP));
		}
		if (_qName == AMBIENT_LIGHT) {
			_ambientLightMap.put(COLOR, atts.getValue(COLOR));
			_ambientLightMap.put(KA, atts.getValue(KA));
		}
		if (_qName == SPHERE) {
			_sphereMap.put(CENTER, atts.getValue(CENTER));
			_sphereMap.put(RADIUS, atts.getValue(RADIUS));
			_sphereMap.put(EMMISSION, atts.getValue(EMMISSION));
			_sphereMap.put(MATERIAL, atts.getValue(MATERIAL));
		}
		if (_qName == TRIANGLE) {
			_triangleMap.put(P0, atts.getValue(P0));
			_triangleMap.put(P1, atts.getValue(P1));
			_triangleMap.put(P2, atts.getValue(P2));
			_triangleMap.put(EMMISSION, atts.getValue(EMMISSION));
			_triangleMap.put(MATERIAL, atts.getValue(MATERIAL));
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		String text = new String(ch, start, length);
		if (text.contains("<") || currentElm == "") {
			return;
		}
	}

	public void endElement(String _nameSpace, String _localName, String _qName) throws SAXException {
		switch (_qName) {
		case SCENE:
			tmp = new SceneDescriptor(_sceneMap, _cameraMap, _ambientLightMap, _sphereLst, _triangleLst, _lightLst);

			// System.out.println(_sceneMap.keySet().toString());
		case CAMERA:
			// System.out.println(_cameraMap.keySet().toString());
		case SPHERE:
			_sphereLst.add(_sphereMap);
			// System.out.println(_sphereMap.keySet().toString());
			break;
		case TRIANGLE:
			_triangleLst.add(_triangleMap);
			// System.out.println(_triangleMap.keySet().toString());
			break;
		case LIGHT:
			_lightLst.add(_lightMap);
			break;
		default:
			break;
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}

	public SceneDescriptor getTmp() throws SAXException {
		if (tmp != SceneDescriptor.EMPTY_Descriptor) {
			return tmp;
		} else {
			return SceneDescriptor.EMPTY_Descriptor;
		}
	}
}
