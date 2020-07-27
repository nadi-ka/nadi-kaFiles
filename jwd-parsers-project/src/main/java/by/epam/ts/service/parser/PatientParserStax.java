package by.epam.ts.service.parser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.epam.ts.bean.Patient;
import by.epam.ts.service.util.stax.StaxDocumentProcessor;

public final class PatientParserStax {
	
	public List<Patient> parseDocumentStax(ByteArrayInputStream bais) throws XMLStreamException{
		List<Patient> patients = new ArrayList<Patient>();
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		try {
		XMLStreamReader reader = inputFactory.createXMLStreamReader(bais);
		StaxDocumentProcessor processor = new StaxDocumentProcessor();
		patients = processor.process(reader);
		}catch (XMLStreamException e) {
			throw new XMLStreamException("Error when calling parseDocumentStax(ByteArrayInputStream bais) from PatientParserSax.", e);
		}
		return patients;
	}

}
