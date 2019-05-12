package parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SceneXmlParser {
	Sax_handler handler = new Sax_handler();

	public void parserInit() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(handler);
		xmlReader.parse("D:\\mavoLehandasatTohna\\XML\\testFile.xml");
	}

	public Sax_handler getHandler() {
		return handler;
	}

}
