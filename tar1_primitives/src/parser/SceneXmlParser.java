package parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SceneXmlParser {
	private SceneDescriptor parserDescriptor;

	public void parserInit() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		Sax_handler handler = new Sax_handler();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(handler);
		xmlReader.parse("D:\\mavoLehandasatTohna\\XML\\testFile.xml");
		parserDescriptor = new SceneDescriptor(handler.getTmp());
	}

	public SceneDescriptor getParserDescriptor() {
		if (parserDescriptor != SceneDescriptor.EMPTY_Descriptor) {
			return parserDescriptor;
		} else {
			return SceneDescriptor.EMPTY_Descriptor;
		}

	}

}
