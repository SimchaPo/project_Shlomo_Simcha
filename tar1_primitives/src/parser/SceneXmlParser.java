package parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SceneXmlParser {
	private Sax_handler handler;
	private XMLReader xmlReader;
	private SAXParser saxParser;
	private SAXParserFactory spf;
	public SceneXmlParser(String systemId) throws ParserConfigurationException, SAXException, IOException {
		spf = SAXParserFactory.newInstance();
		saxParser = spf.newSAXParser();
		xmlReader = saxParser.getXMLReader();
		handler = new Sax_handler();
		xmlReader.setContentHandler(handler);
		xmlReader.parse(systemId);
	}
	SceneDescriptor _sceneD=handler.getSceneDescriptor();
}
