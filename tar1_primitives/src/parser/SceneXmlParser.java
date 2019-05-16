package parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SceneXmlParser {
	private SceneDescriptor parserDescriptor;

	public void parserInit(File _file) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		Sax_handler handler = new Sax_handler();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(handler);
		// File fXmlFile = new File("testFile.xml");
		xmlReader.parse("file:\\".concat(_file.getAbsolutePath()));
		parserDescriptor = new SceneDescriptor(handler.getTmp());
	}
//yhfcyfytf
	public SceneDescriptor getParserDescriptor() {
		if (parserDescriptor != SceneDescriptor.EMPTY_Descriptor) {
			return parserDescriptor;
		} else {
			return SceneDescriptor.EMPTY_Descriptor;
		}
	}
}
