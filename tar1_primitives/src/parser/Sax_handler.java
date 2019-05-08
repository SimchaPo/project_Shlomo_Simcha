package parser;

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
	SceneDescriptor _fromXML=new SceneDescriptor();
	static final String SCENE="scene";
	static final String AMBIENT_LIGHT="ambient-light";
	static final String CAMERA="camera";
	static final String GEOMETRIES="geometries";
	static final String SPHERE ="sphere";
	static final String TRIANGLE ="triangle";
	private String currentElm=null;
	public void startDocument() throws SAXException {
		System.out.println("Parsing started...");
	}

	public void endDocument() throws SAXException {
		System.out.println("Parsing ended...");
	}

	public void startElement(String _nameSpace, String _localName, String _qName, Attributes atts) throws SAXException {
		currentElm=_qName;
		
	}

	public void endElement(String _nameSpace, String _localName, String _qName) throws SAXException {

	}
	SceneDescriptor getSceneDescriptor() {
		return _fromXML;
	}
}
