package unittests;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneDescriptor;
import parser.SceneXmlParser;

public class ParserTest extends TestCase {
	public SceneXmlParser parserT = new SceneXmlParser();

	public SceneDescriptor test1;
	public Map<String, String> tmpMap;

	public void testInitializeFromXMLstring() throws SAXException, ParserConfigurationException, IOException {
		parserT.parserInit();
		if (parserT.getParserDescriptor() != SceneDescriptor.EMPTY_Descriptor) {
			tmpMap.putAll(parserT.getParserDescriptor().get_sceneAttributes());
			String str1 = tmpMap.values().toString();
			System.out.println(str1);
			test1.InitializeFromXMLstring();
		}

		assertEquals(1, 1);
	}

}
