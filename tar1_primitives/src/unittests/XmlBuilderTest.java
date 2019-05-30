package unittests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;
import parser.SceneDescriptor;
import parser.XMLBuilder;

public class XmlBuilderTest extends TestCase {
	File expFile = new File("testFile.xml");
	XMLBuilder testXml = new XMLBuilder("newTestnewXML.xml");
	SceneDescriptor exp = new SceneDescriptor();
	SceneDescriptor res = new SceneDescriptor();

	public void testWriteToFile() throws FileNotFoundException, TransformerException, ParserConfigurationException,
			SAXException, IOException, XMLStreamException {
		testXml.WriteToFile(testXml.getFileName());
		File tXMLFile = new File(testXml.getFileName());
		exp.InitializeFromXMLstring(expFile);
		res.InitializeFromXMLstring(tXMLFile);
		String expstr = exp.get_sceneAttributes().toString();
		String resstr = res.get_sceneAttributes().toString();

		//assertEquals(expstr, resstr);
	}

}
