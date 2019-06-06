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
		tmpMap.putAll(parserT.getParserDescriptor().getSceneAttributes());
		test1.InitializeFromXMLstring(_f);
		assertTrue(true);
	}
}
