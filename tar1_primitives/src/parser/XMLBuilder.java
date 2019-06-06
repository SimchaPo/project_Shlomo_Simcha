package parser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * The class build XML document and save it in file has getter for file name
 * those build
 * 
 * @author meerz
 *
 */
public class XMLBuilder {

	private String fileName;

	/********* constructors ********/
	public XMLBuilder() {
		this("NewXMLFile");
	}

	public XMLBuilder(String str) {
		fileName = str + ".xml";
	}

	/******** getter *********/
	public String getFileName() {
		return fileName;
	}

	/**
	 * function writes scene detailes to XML file
	 * 
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	public void WriteToFile() throws TransformerException, ParserConfigurationException, SAXException, IOException,
			FileNotFoundException, XMLStreamException {
		XMLOutputFactory _outFactory = XMLOutputFactory.newFactory();
		XMLStreamWriter _fileWriter = _outFactory.createXMLStreamWriter(new FileOutputStream(fileName));
		_fileWriter.writeStartDocument();
		_fileWriter.writeStartElement("scene");
		_fileWriter.writeAttribute("background-color", "0 0 0");
		_fileWriter.writeAttribute("screen-width", "500");
		_fileWriter.writeAttribute("screen-height", "500");
		_fileWriter.writeAttribute("screen-dist", "150");

		_fileWriter.writeStartElement("ambient-light");
		_fileWriter.writeAttribute("color", "15 15 15");
		_fileWriter.writeAttribute("ka", "1");
		_fileWriter.writeEndElement();

		_fileWriter.writeStartElement("camera");
		_fileWriter.writeAttribute("p0", "0 0 0");
		_fileWriter.writeAttribute("vTo", "0 0 1");
		_fileWriter.writeAttribute("vUp", "0 -1 0");
		_fileWriter.writeEndElement();

		_fileWriter.writeStartElement("light");
		_fileWriter.writeAttribute("light-color", "255 100 100");
		_fileWriter.writeAttribute("point", "-50 50 20");
		_fileWriter.writeAttribute("direction", "-1 -1 2");
		_fileWriter.writeAttribute("kC", "1");
		_fileWriter.writeAttribute("kL", "0.0001");
		_fileWriter.writeAttribute("kQ", "0.000005");
		_fileWriter.writeEndElement();

		_fileWriter.writeStartElement("light");
		_fileWriter.writeAttribute("light-color", "255 100 100");
		// _fileWriter.writeAttribute("point", "-50 50 20");
		_fileWriter.writeAttribute("direction", "-1 -1 2");
//		_fileWriter.writeAttribute("kC", "1");
//		_fileWriter.writeAttribute("kL", "0.0001");
//		_fileWriter.writeAttribute("kQ", "0.000005");
		_fileWriter.writeEndElement();

		_fileWriter.writeStartElement("geometries");

		_fileWriter.writeStartElement("sphere");
		_fileWriter.writeAttribute("center", "0 0 150");
		_fileWriter.writeAttribute("radius", "80");
		_fileWriter.writeAttribute("emmission", "17 30 108");
		_fileWriter.writeAttribute("material", "0.5 1.5 40");
		_fileWriter.writeEndElement();

		// T1
//		_fileWriter.writeStartElement("triangle");
//		_fileWriter.writeAttribute("p0", "180 -180 145");
//		_fileWriter.writeAttribute("p1", "180 -180 150");
//		_fileWriter.writeAttribute("p2", "-250 250 150");
//		_fileWriter.writeAttribute("emmission", "150 34 177");
//		_fileWriter.writeAttribute("material", "0.3 0.1 11");
//		_fileWriter.writeEndElement();
//
//		// T2
//		_fileWriter.writeStartElement("triangle");
//		_fileWriter.writeAttribute("p0", "180 180 150");
//		_fileWriter.writeAttribute("p1", "250 250 145");
//		_fileWriter.writeAttribute("p2", "-250 250 150");
//		_fileWriter.writeAttribute("emmission", "50 130 234");
//		_fileWriter.writeAttribute("material", "0.3 0.1 11");
//		_fileWriter.writeEndElement();

		// T3
		_fileWriter.writeStartElement("triangle");
		_fileWriter.writeAttribute("p0", "10 0 -49");
		_fileWriter.writeAttribute("p1", "0 -10 -49");
		_fileWriter.writeAttribute("p2", "10 -10 -49");
		_fileWriter.writeAttribute("emmission", "50 130 176");
		_fileWriter.writeAttribute("material", "0.3 0.1 11");
		_fileWriter.writeEndElement();

		// T4
		_fileWriter.writeStartElement("triangle");
		_fileWriter.writeAttribute("p0", "-100 0 -49");
		_fileWriter.writeAttribute("p1", "0 -100 -49");
		_fileWriter.writeAttribute("p2", "-100 -100 -49");
		_fileWriter.writeAttribute("emmission", "50 134 211");
		_fileWriter.writeAttribute("material", "0.3 0.1 11");
		_fileWriter.writeEndElement();

		_fileWriter.writeEndElement();
		_fileWriter.writeEndElement();
		_fileWriter.writeEndDocument();
		_fileWriter.flush();
		_fileWriter.close();
	}
}
