package unittests;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneDescriptor;
import parser.SceneXmlParser;

public class TestSax extends TestCase {
	SceneXmlParser a = new SceneXmlParser();
	SceneDescriptor b = new SceneDescriptor();

	public void testHandlerTest() throws SAXException {
		String tString1 = a.getParserDescriptor().get_sceneAttributes().toString();
		String tString2 = b.get_sceneAttributes().toString();
		assertEquals(tString1, tString2);
	}
}
