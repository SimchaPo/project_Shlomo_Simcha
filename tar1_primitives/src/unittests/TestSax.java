package unittests;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneXmlParser;

public class TestSax extends TestCase {
	SceneXmlParser a;

	public void testHandlerTest() throws SAXException, ParserConfigurationException, IOException {
		a.parserInit();
		String tString = a.getParserDescriptor().toString();
		System.out.println(tString);
		assertEquals(true, true);
	}

}
