package by.epam.ts.service.parser;

import java.io.IOException;
import java.util.List;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.epam.ts.bean.Patient;
import by.epam.ts.service.util.handler.PatientSaxHandler;

public final class PatientParserSax {
	
	public List<Patient> parseDocumentSax(InputSource inputSource) throws SAXException, IOException {
		PatientSaxHandler handler = new PatientSaxHandler();
		try {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		reader.setContentHandler(handler);
		reader.parse(inputSource);
		}catch (SAXException e) {
			throw new SAXException("Error when calling parseDocumentSax(InputSource inputSource) from PatientParserSax", e);
		}catch (IOException e) {
			throw new IOException("Error when calling parseDocumentSax(InputSource inputSource) from PatientParserSax", e);	
		}
		List<Patient> patients = handler.getPatientsList();
		return patients;	
	}

}
