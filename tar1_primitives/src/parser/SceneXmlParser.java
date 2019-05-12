package parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SceneXmlParser {
	private SceneDescriptor parserDescriptor = SceneDescriptor.EMPTY_Descriptor;

	public void parserInit() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		Sax_handler handler = new Sax_handler();
	
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(handler);
		xmlReader.parse("D:\\mavoLehandasatTohna\\XML\\testFile.xml");
		parserDescriptor = handler.getTmp();
	}

	public SceneDescriptor getParserDescriptor() {
		return parserDescriptor;
	}

}
