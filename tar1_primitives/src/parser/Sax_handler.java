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
	private Map<String, String> _sphereMap;
	private Map<String, String> _triangleMap;
	private Map<String, String> _sceneMap;
	private Map<String, String> _cameraMap;
	private Map<String, String> _ambientLightMap;
	private List<Map<String, String>> _sphereLst = null;
	private List<Map<String, String>> _triangleLst = null;
	private String currentElm = null;
	SceneDescriptor tmp = null;
	// private String currentFigure = null;
	// int count = 0;

	public void startDocument() throws SAXException {
		System.out.println("Parsing started...");
	}

	public void startElement(String _nameSpace, String _localName, String _qName, Attributes atts) throws SAXException {
		currentElm = _qName;

		switch (currentElm) {
		case SCENE:
			_sceneMap = new HashMap<String, String>();

			_sceneMap.put(BACKGRAUND_COLOR, atts.getValue(BACKGRAUND_COLOR));

			_sceneMap.put(SCREEN_WIDTH, atts.getValue(SCREEN_WIDTH));

			_sceneMap.put(SCREEN_HEIGHT, atts.getValue(SCREEN_HEIGHT));

			_sceneMap.put(SCREEN_DIST, atts.getValue(SCREEN_DIST));

			break;
		case CAMERA:
			_cameraMap = new HashMap<String, String>();
			_cameraMap.put(P_0, atts.getValue(P_0));

			_cameraMap.put(V_TO, atts.getValue(V_TO));

			_cameraMap.put(V_UP, atts.getValue(V_UP));

			break;
		case AMBIENT_LIGHT:
			_ambientLightMap = new HashMap<String, String>();
			_ambientLightMap.put(COLOR, atts.getValue(COLOR));
			break;

		case SPHERE:
			_sphereMap = new HashMap<String, String>();
			_sphereMap.put(CENTER, atts.getValue(CENTER));
			_sphereMap.put(RADIUS, atts.getValue(RADIUS));

			break;
		case TRIANGLE:
			_triangleMap = new HashMap<String, String>();
			_triangleMap.put(P0, atts.getValue(P0));

			_triangleMap.put(P1, atts.getValue(P1));

			_triangleMap.put(P2, atts.getValue(P2));

			break;

		default:
			break;
		}

	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		String text = new String(ch, start, length);
		if (text.contains("<") || currentElm == null) {
			return;
		}

	}

	public void endElement(String _nameSpace, String _localName, String _qName) throws SAXException {
		switch (_qName) {
		case SCENE:
			tmp = new SceneDescriptor(_sceneMap, _cameraMap, _ambientLightMap, _sphereLst, _triangleLst);
			System.out.println(_sceneMap.keySet().toString());
		case CAMERA:
			System.out.println(_cameraMap.keySet().toString());
		case SPHERE:
			if (_sphereLst == null) {
				_sphereLst = new ArrayList<Map<String, String>>(null);
			}
			_sphereLst.add(_sphereMap);
			System.out.println(_sphereMap.keySet().toString());
			break;
		case TRIANGLE:
			if (_triangleLst == null) {
				_triangleLst = new ArrayList<Map<String, String>>(null);
			}
			_triangleLst.add(_triangleMap);
			System.out.println(_triangleMap.keySet().toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	public SceneDescriptor getTmp() {
		return tmp;
	}

}
