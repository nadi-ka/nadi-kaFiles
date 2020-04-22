package by.epam.ts.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.XmlDao;
import by.epam.ts.dal.factory.XmlDaoFactory;
import by.epam.ts.service.XmlService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ServiceValidationException;
import by.epam.ts.service.util.handler.PatientSaxHandler;
import by.epam.ts.service.util.stax.StaxDocumentProcessor;
import by.epam.ts.service.validation.FileValidator;

public class XmlServiceImpl implements XmlService {

	private final static String patient = "patient";
	private final static String surname = "surname";
	private final static String name = "name";
	private final static String dateOfBirth = "date-of-birth";
	private final static String id = "id";
	private final static String prescriptions = "prescriptions";
	private final static String treatment = "treatment";
	private final static String idAppoint = "id-appointment";
	private final static String doctorSurname = "doctor-surname";
	private final static String treatmentName = "treatment-name";

	private static final Logger log = LogManager.getLogger(XmlServiceImpl.class);

	@Override
	public List<Patient> getPatientsListSAX(String xmlFileName) throws ServiceException {
		XmlDaoFactory xmlDaoFactory = XmlDaoFactory.getInstance();
		XmlDao xmlDao = xmlDaoFactory.getXmlDao();
		PatientSaxHandler handler = new PatientSaxHandler();
		try {
			byte[] bytes = xmlDao.readFromXmlFile(xmlFileName);
			ByteArrayInputStream bais1 = new ByteArrayInputStream(bytes);

			// Document validation;
			FileValidator fileValidator = new FileValidator();
			boolean isValid = fileValidator.validateXmlDocument(bais1);
			if (!isValid) {
				log.log(Level.ERROR,
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
				throw new ServiceValidationException(
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
			}
			// Parsing;
			ByteArrayInputStream bais2 = new ByteArrayInputStream(bytes);
			InputSource inputSource = new InputSource(bais2);
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
			reader.parse(inputSource);
		} catch (DaoException e) {
			log.log(Level.ERROR,
					"Error during calling getPatientsListSAX(String xmlFileName) from  XmlServiceImpl. InputSource wasn't created",
					e);
			throw new ServiceException(
					"Error during calling getPatientsListSAX(String xmlFileName) from  XmlServiceImpl. InputSource wasn't created",
					e);
		} catch (SAXException e) {
			log.log(Level.ERROR,
					"Error during calling getPatientsListSAX(String xmlFileName) from  XmlServiceImpl. Reader wasn't created",
					e);
			throw new ServiceException(
					"Error during calling getPatientsListSAX(String xmlFileName) from  XmlServiceImpl. Reader wasn't created",
					e);
		} catch (IOException e) {
			log.log(Level.ERROR,
					"Error during calling reader.parse(inputSource) in getPatientsListSAX(String xmlFileName) from  XmlServiceImpl.",
					e);
			throw new ServiceException(
					"Error during calling reader.parse(inputSource) in getPatientsListSAX(String xmlFileName) from  XmlServiceImpl.",
					e);
		}
		List<Patient> patients = handler.getPatientsList();
		return patients;
	}

	public List<Patient> getPatientsListStaX(String xmlFileName) throws ServiceException {
		XmlDaoFactory xmlDaoFactory = XmlDaoFactory.getInstance();
		XmlDao xmlDao = xmlDaoFactory.getXmlDao();
		List<Patient> patients = new ArrayList<Patient>();
		try {
			byte[] bytes = xmlDao.readFromXmlFile(xmlFileName);
			ByteArrayInputStream bais1 = new ByteArrayInputStream(bytes);

			// Document validation;
			FileValidator fileValidator = new FileValidator();
			boolean isValid = fileValidator.validateXmlDocument(bais1);
			if (!isValid) {
				log.log(Level.ERROR,
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
				throw new ServiceValidationException(
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
			}
			// Parsing;
			ByteArrayInputStream bais2 = new ByteArrayInputStream(bytes);
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLStreamReader reader = inputFactory.createXMLStreamReader(bais2);
			StaxDocumentProcessor processor = new StaxDocumentProcessor();
			patients = processor.process(reader);

		} catch (XMLStreamException e) {
			log.log(Level.ERROR, "Error when parsing in method getPatientsListStaX(String xmlFileName)", e);
			throw new ServiceException("Error when parsing in method getPatientsListStaX(String xmlFileName)", e);
		} catch (DaoException e) {
			log.log(Level.ERROR, "Error when calling xmlDao.readFromXmlFile(xmlFileName)", e);
			throw new ServiceException("Error when calling xmlDao.readFromXmlFile(xmlFileName)", e);
		}
		return patients;

	}

	public List<Patient> getPatientsListDOM(String xmlFileName) throws ServiceException {
		XmlDaoFactory xmlDaoFactory = XmlDaoFactory.getInstance();
		XmlDao xmlDao = xmlDaoFactory.getXmlDao();
		List<Patient> patients = new ArrayList<Patient>();
		try {
			byte[] bytes = xmlDao.readFromXmlFile(xmlFileName);
			ByteArrayInputStream bais1 = new ByteArrayInputStream(bytes);

			// Document validation;
			FileValidator fileValidator = new FileValidator();
			boolean isValid = fileValidator.validateXmlDocument(bais1);
			if (!isValid) {
				log.log(Level.ERROR,
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
				throw new ServiceValidationException(
						"Error when calling validateXmlDocument(reader) from getPatientsListSAX(String xmlFileName). The document is not valid: "
								+ xmlFileName);
			}
			// Parsing;
			ByteArrayInputStream bais2 = new ByteArrayInputStream(bytes);
			InputSource inputSource = new InputSource(bais2);
			DOMParser parser = new DOMParser();
			parser.parse(inputSource);
			Document document = parser.getDocument();

			Element root = document.getDocumentElement();
			NodeList patientNodes = root.getElementsByTagName(patient);
			Patient patient = null;
			for (int i = 0; i < patientNodes.getLength(); i++) {
				patient = new Patient();
				Element patientElement = (Element) patientNodes.item(i);
				patient.setId(patientElement.getAttribute(id));
				patient.setSurname(getSingleChild(patientElement, surname).getTextContent().trim());
				patient.setName(getSingleChild(patientElement, name).getTextContent().trim());
				String date = getSingleChild(patientElement, dateOfBirth).getTextContent().trim();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateOfBirth = LocalDate.parse(date, format);
				patient.setDateOfBirth(dateOfBirth);

				Element prescriptionsElement = getSingleChild(patientElement, prescriptions);

				List<Treatment> prescriptions = new ArrayList<Treatment>();
				NodeList treatmentNodes = prescriptionsElement.getElementsByTagName(treatment);
				Treatment treatment = null;
				for (int j = 0; j < treatmentNodes.getLength(); j++) {
					treatment = new Treatment();
					Element treatmentElement = (Element) treatmentNodes.item(j);
					String idAppointment = getSingleChild(treatmentElement, idAppoint).getTextContent().trim();
					treatment.setIdAppointment(Integer.parseInt(idAppointment));
					treatment.setTreatmentName(getSingleChild(treatmentElement, treatmentName).getTextContent().trim());
					treatment.setDoctorSurname(getSingleChild(treatmentElement, doctorSurname).getTextContent().trim());
					prescriptions.add(treatment);
				}
				patient.setPrescriptions(prescriptions);
				patients.add(patient);

			}
		} catch (IOException e) {
			log.log(Level.ERROR, "Error when parsing in method getPatientsListDOM(String xmlFileName)", e);
			throw new ServiceException("Error when parsing in method getPatientsListDOM(String xmlFileName)", e);
		} catch (DaoException e) {
			log.log(Level.ERROR, "Error when calling xmlDao.readFromXmlFile(xmlFileName)", e);
			throw new ServiceException("Error when calling xmlDao.readFromXmlFile(xmlFileName)", e);
		} catch (SAXException e) {
			log.log(Level.ERROR, "Error when parsing in method getPatientsListDOM(String xmlFileName)", e);
			throw new ServiceException("Error when parsing in method getPatientsListDOM(String xmlFileName)", e);
		}
		return patients;
	}

	private static Element getSingleChild(Element element, String childName) {
		NodeList nodeList = element.getElementsByTagName(childName);
		Element child = (Element) nodeList.item(0);
		return child;
	}

}
