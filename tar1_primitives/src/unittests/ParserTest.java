package unittests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneDescriptor;
import parser.SceneXmlParser;

public class ParserTest extends TestCase {
	public SceneXmlParser parserT = new SceneXmlParser();
	public File _f = new File("testFile.xml");
	public SceneDescriptor test1 = new SceneDescriptor();
	public Map<String, String> tmpMap = new HashMap<String, String>();

	public void testInitializeFromXMLstring() throws SAXException, ParserConfigurationException, IOException {
		parserT.parserInit(_f);
		// if (parserT.getParserDescriptor() != SceneDescriptor.EMPTY_Descriptor) {
		tmpMap.putAll(parserT.getParserDescriptor().get_sceneAttributes());
		String str1 = tmpMap.values().toString();
		System.out.println(str1 + tmpMap.keySet().toString());
		test1.InitializeFromXMLstring(_f);
		// }
		assertTrue(true);
	}
}
