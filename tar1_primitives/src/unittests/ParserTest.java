package unittests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneDescriptor;
import parser.SceneXmlParser;

public class ParserTest extends TestCase {
	public SceneXmlParser parserT;
	public SceneDescriptor test1;
	public Map<String, String> tmpMap = new HashMap<String, String>();

	public void testInitializeFromXMLstring() throws SAXException, ParserConfigurationException, IOException {
		test1.InitializeFromXMLstring();
		tmpMap.putAll(parserT.handler.getSceneDescriptor().get_sceneAttributes());
		assertEquals(tmpMap, test1.get_sceneAttributes());
	}

}
