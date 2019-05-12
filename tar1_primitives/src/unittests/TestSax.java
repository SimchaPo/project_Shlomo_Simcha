package unittests;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneXmlParser;

public class TestSax extends TestCase {
	SceneXmlParser a;

	public void testHandlerTest() throws SAXException {
		String tString = a.getParserDescriptor().get_sceneAttributes().toString();
		System.out.println(tString);
		assertEquals(true, true);
	}

}
