package parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SceneXmlParser {
	public Sax_handler handler;
	public XMLReader xmlReader;
	public SAXParser saxParser;
	public SAXParserFactory spf;

	public SceneXmlParser() throws ParserConfigurationException, SAXException, IOException {
		spf = SAXParserFactory.newInstance();
		saxParser = spf.newSAXParser();
		xmlReader = saxParser.getXMLReader();
		handler = new Sax_handler();
		xmlReader.setContentHandler(handler);
		xmlReader.parse("D:\\mavoLehandasatTohna\\XML\\testFile.xml");
	}

	public SceneDescriptor _sceneD = handler.getSceneDescriptor();
}
