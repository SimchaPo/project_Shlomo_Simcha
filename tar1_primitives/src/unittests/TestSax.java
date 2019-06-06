package unittests;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneDescriptor;
import parser.SceneXmlParser;

public class TestSax extends TestCase {
	SceneXmlParser a = new SceneXmlParser();
	SceneDescriptor b = new SceneDescriptor();

	public File _f = new File("testFile.xml");

	public void testHandlerTest() throws SAXException, ParserConfigurationException, IOException {
		b.InitializeFromXMLstring(_f);
		a.parserInit(_f);
		String tString1 = a.getParserDescriptor().getSceneAttributes().toString();
		String tString2 = b.getSceneAttributes().toString();
		assertEquals(tString1, tString2);
	}
}
