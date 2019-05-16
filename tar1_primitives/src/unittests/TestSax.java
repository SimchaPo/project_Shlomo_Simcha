package unittests;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneXmlParser;

public class TestSax extends TestCase {
<<<<<<< HEAD
	SceneXmlParser a= new SceneXmlParser();
	File _f = new File("testFile.xml");
	public void testHandlerTest() throws SAXException, ParserConfigurationException, IOException {
		a.parserInit(_f);
		String tString = a.getParserDescriptor().toString();
		System.out.println(tString);
=======
	SceneXmlParser a = new SceneXmlParser();

	public void testHandlerTest() throws SAXException {
		String tString = a.getParserDescriptor().get_sceneAttributes().toString();
	 System.out.println(tString);
>>>>>>> refs/remotes/origin/master
		assertEquals(true, true);
	}
}
