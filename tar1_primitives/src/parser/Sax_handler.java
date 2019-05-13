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
	static final String CAMERA = "camera";
	static final String GEOMETRIES = "geometries";
	static final String SPHERE = "sphere";
	static final String TRIANGLE = "triangle";
	static final String BACKGRAUND_COLOR = "background-color";
	static final String SCREEN_WIDTH = "screen-width";
	static final String SCREEN_HEIGHT = "screen-height";
	static final String SCREEN_DIST = "screen-dist";
	static final String COLOR = "color";
	static final String P_0 = "p0";
	static final String V_TO = "vTo";
	static final String V_UP = "vUp";
	static final String CENTER = "centre";
	static final String RADIUS = "radius";
	static final String P0 = "p0";
	static final String P1 = "p1";
	static final String P2 = "p2";
	private Map<String, String> _sphereMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _triangleMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _sceneMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _cameraMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private Map<String, String> _ambientLightMap = new HashMap<String, String>(SceneDescriptor.EMPTY_MAP);
	private List<Map<String, String>> _sphereLst = new ArrayList<Map<String, String>>(SceneDescriptor.EMPTY_LIST);
	private List<Map<String, String>> _triangleLst = new ArrayList<Map<String, String>>(SceneDescriptor.EMPTY_LIST);
	private String currentElm = "";
	SceneDescriptor tmp = SceneDescriptor.EMPTY_Descriptor;
	// private String currentFigure = null;
	// int count = 0;

	public void startDocument() throws SAXException {
		System.out.println("startDocument");
	}

	public void startElement(String _nameSpace, String _localName, String _qName, Attributes atts) throws SAXException {
		// currentElm = _qName;

		if (_qName == SCENE) {
			System.out.println(_qName + " " + "startElement");
			_sceneMap.put(BACKGRAUND_COLOR, atts.getValue(BACKGRAUND_COLOR));
			_sceneMap.put(SCREEN_WIDTH, atts.getValue(SCREEN_WIDTH));
			_sceneMap.put(SCREEN_HEIGHT, atts.getValue(SCREEN_HEIGHT));
			_sceneMap.put(SCREEN_DIST, atts.getValue(SCREEN_DIST));
		}
		if (_qName == CAMERA) {
			System.out.println(_qName + " " + "startElement");
			_cameraMap.put(P_0, atts.getValue(P_0));
			_cameraMap.put(V_TO, atts.getValue(V_TO));
			_cameraMap.put(V_UP, atts.getValue(V_UP));
		}
		if (_qName == AMBIENT_LIGHT) {
			System.out.println(_qName + " " + "startElement");
			_ambientLightMap.put(COLOR, atts.getValue(COLOR));
		}
		if (_qName == SPHERE) {
			System.out.println(_qName + " " + "startElement");
			_sphereMap.put(CENTER, atts.getValue(CENTER));
			_sphereMap.put(RADIUS, atts.getValue(RADIUS));
		}
		if (_qName == TRIANGLE) {
			System.out.println(_qName + " " + "startElement");
			_triangleMap.put(P0, atts.getValue(P0));
			_triangleMap.put(P1, atts.getValue(P1));
			_triangleMap.put(P2, atts.getValue(P2));
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
			System.out.println(_qName + " " + "endElement");
			tmp = new SceneDescriptor(_sceneMap, _cameraMap, _ambientLightMap, _sphereLst, _triangleLst);
			// System.out.println(_sceneMap.keySet().toString());
		case CAMERA:
			System.out.println(_qName + " " + "endElement");
			// System.out.println(_cameraMap.keySet().toString());
		case SPHERE:
			System.out.println(_qName + " " + "endElement");
			_sphereLst.add(_sphereMap);
			// System.out.println(_sphereMap.keySet().toString());
			break;
		case TRIANGLE:
			System.out.println(_qName + " " + "endElement");
			_triangleLst.add(_triangleMap);
			// System.out.println(_triangleMap.keySet().toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDokument");
	}

	public SceneDescriptor getTmp() throws SAXException {
		if (tmp != SceneDescriptor.EMPTY_Descriptor) {
			return tmp;
		} else {
			return SceneDescriptor.EMPTY_Descriptor;
		}
	}

}
