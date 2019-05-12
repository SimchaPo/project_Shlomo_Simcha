package parser;

import java.util.HashMap;
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
	SceneDescriptor _fromXML = new SceneDescriptor();
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
	Map<String, String> _sphereMap = new HashMap<String, String>();
	Map<String, String> _triangleMap = new HashMap<String, String>();
	private String currentElm = null;
	private String currentFigure = null;

	public void startDocument() throws SAXException {
		System.out.println("Parsing started...");
	}

	public void endDocument() throws SAXException {
		System.out.println("Parsing ended...");
	}

	public void startElement(String _nameSpace, String _localName, String _qName, Attributes atts) throws SAXException {
		currentElm = _qName;
		switch (currentElm) {
		case SCENE:
			_fromXML._sceneAttributes.put(BACKGRAUND_COLOR, atts.getValue(BACKGRAUND_COLOR));
			_fromXML._sceneAttributes.put(SCREEN_WIDTH, atts.getValue(SCREEN_WIDTH));
			_fromXML._sceneAttributes.put(SCREEN_HEIGHT, atts.getValue(SCREEN_HEIGHT));
			_fromXML._sceneAttributes.put(SCREEN_DIST, atts.getValue(SCREEN_DIST));
			break;
		case CAMERA:
			_fromXML._cameraAttributes.put(P_0, atts.getValue(P_0));
			_fromXML._cameraAttributes.put(V_TO, atts.getValue(V_TO));
			_fromXML._cameraAttributes.put(V_UP, atts.getValue(V_UP));
			break;
		case AMBIENT_LIGHT:
			_fromXML._ambientLightAttributes.put(COLOR, atts.getValue(COLOR));
			break;
		case GEOMETRIES:
			if (currentFigure == null) {
				return;
			}
			switch (currentFigure) {
			case SPHERE:
				_sphereMap.put(CENTER, atts.getValue(CENTER));
				_sphereMap.put(RADIUS, atts.getValue(RADIUS));
				break;
			case TRIANGLE:
				_triangleMap.put(P0, atts.getValue(P0));
				_triangleMap.put(P1, atts.getValue(P1));
				_triangleMap.put(P2, atts.getValue(P2));
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}

	}

//	public void characters(char[] ch, int start, int length) throws SAXException {
//		String text=new String(ch, start, length)
//		if (text.contains("<")||currentElm==null) {
//			return;
//		}
//		switch (currentElm) {
//		
//		case BACKGRAUND_COLOR:
//			_sceneA.put(BACKGRAUND_COLOR,attri);
//			break;
//		case TRIANGLE:
//
//			break;
//		default:
//			break;
//		}
//	}

	public void endElement(String _nameSpace, String _localName, String _qName) throws SAXException {
		switch (_qName) {
		case SPHERE:
			//int spheresCount = _fromXML._spheres.size();
			_fromXML._spheres.add(_sphereMap);
			break;
		case TRIANGLE:
			//int trianglesCount = _fromXML._triangles.size();
			_fromXML._triangles.add(_triangleMap);
			break;
		default:
			break;
		}
	}

	SceneDescriptor getSceneDescriptor() {
		return _fromXML;
	}
}
