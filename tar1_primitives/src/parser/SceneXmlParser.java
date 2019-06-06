package parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * The class initialize the SAX Parser using the SAX parser factory & etc from
 * java.xml libraries
 * 
 * @author meerz
 *
 */
public class SceneXmlParser {
	private SceneDescriptor parserDescriptor;

	public void parserInit(File _file) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		SaxHandler handler = new SaxHandler();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(handler);
		xmlReader.parse("file:\\".concat(_file.getAbsolutePath()));
		parserDescriptor = new SceneDescriptor(handler.getTmp());
	}

	public SceneDescriptor getParserDescriptor() {
		return parserDescriptor;
	}
}
