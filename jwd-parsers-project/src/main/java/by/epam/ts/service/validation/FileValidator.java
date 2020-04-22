package by.epam.ts.service.validation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import by.epam.ts.service.exception.ServiceException;

public class FileValidator {

	private static final String xsdSchema = "C:\\Users\\Vadim\\eclipse-workspace\\jwd-parsers-project\\src\\main\\resources\\patients_schema.xsd";
	private static final Logger log = LogManager.getLogger(FileValidator.class);

	public boolean validateXmlDocument(InputStream inputStream) throws ServiceException {

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		File schemaLocation = new File(xsdSchema);
		Schema schema;
		try {
			schema = schemaFactory.newSchema(schemaLocation);
		} catch (SAXException e) {
			log.log(Level.ERROR,
					"Error when calling newSchema(schemaLocation) from validateXmlDocument(String xmlFileName). Instance of Schema wasn't created.",
					e);
			throw new ServiceException(
					"Error when calling newSchema(schemaLocation) from validateXmlDocument(String xmlFileName). Instance of Schema wasn't created.",
					e);
		}
		Validator validator = schema.newValidator();
		Source source = new StreamSource(inputStream);
		try {
			validator.validate(source);
			return true;
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			log.log(Level.ERROR,
					"Error during calling validator.validate(source). Method validateXmlDocument(InputSource inputSource)",
					e);
			throw new ServiceException(
					"Error during calling validator.validate(source). Method validateXmlDocument(InputSource inputSource)",
					e);
		}
	}
}
