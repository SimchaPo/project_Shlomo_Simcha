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

public class XMLBuilder {
//	XMLOutputFactory _outFactory;
//	XMLStreamWriter _fileWriter;
	public String fileName;

	public XMLBuilder() {
		fileName = "NewXMLFile.xml";
	}

	public XMLBuilder(String str) {
		fileName = str;
	}

	public void WriteToFile(String fName) throws TransformerException, ParserConfigurationException, SAXException,
			IOException, FileNotFoundException, XMLStreamException {
		XMLOutputFactory _outFactory = XMLOutputFactory.newFactory();
		XMLStreamWriter _fileWriter = _outFactory.createXMLStreamWriter(new FileOutputStream(fName));
		_fileWriter.writeStartDocument();
		// public static void WriteToFile() {
		_fileWriter.writeStartElement("scene");
		_fileWriter.writeAttribute("background-color", "75 127 190");
		_fileWriter.writeAttribute("screen-width", "500");
		_fileWriter.writeAttribute("screen-height", "500");
		_fileWriter.writeAttribute("screen-dist", "50");

		_fileWriter.writeStartElement("ambient-light");
		_fileWriter.writeAttribute("color", "255 255 255");
		_fileWriter.writeAttribute("K", "1");
		_fileWriter.writeEndElement();

		_fileWriter.writeStartElement("camera");
		_fileWriter.writeAttribute("p0", "0 0 0");
		_fileWriter.writeAttribute("vTo", "0 0 -1");
		_fileWriter.writeAttribute("pUp", "0 -1 0");
		_fileWriter.writeEndElement();

		_fileWriter.writeStartElement("geometries");

		_fileWriter.writeStartElement("sphere");
		_fileWriter.writeAttribute("center", "0 0 -50");
		_fileWriter.writeAttribute("radius", "50");
		_fileWriter.writeEndElement();

		// T1
		_fileWriter.writeStartElement("triangle");
		_fileWriter.writeAttribute("p0", "100 0 -49");
		_fileWriter.writeAttribute("p1", "0 100 -49");
		_fileWriter.writeAttribute("p2", "100 100 -49");
		_fileWriter.writeEndElement();

		// T2
		_fileWriter.writeStartElement("triangle");
		_fileWriter.writeAttribute("p0", "-100 0 -49");
		_fileWriter.writeAttribute("p1", "0 100 -49");
		_fileWriter.writeAttribute("p2", "-100 100 -49");
		_fileWriter.writeEndElement();

		// T3
		_fileWriter.writeStartElement("triangle");
		_fileWriter.writeAttribute("p0", "100 0 -49");
		_fileWriter.writeAttribute("p1", "0 -100 -49");
		_fileWriter.writeAttribute("p2", "100 -100 -49");
		_fileWriter.writeEndElement();

		// T4
		_fileWriter.writeStartElement("triangle");
		_fileWriter.writeAttribute("p0", "-100 0 -49");
		_fileWriter.writeAttribute("p1", "0 -100 -49");
		_fileWriter.writeAttribute("p2", "-100 -100 -49");
		_fileWriter.writeEndElement();

		_fileWriter.writeEndElement();
		_fileWriter.writeEndElement();
		_fileWriter.writeEndDocument();
		_fileWriter.flush();
		_fileWriter.close();
	}

}
