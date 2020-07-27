package by.epam.ts.service.parser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;

public final class PatientParserDom {
	
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
	
	public List<Patient> parseDocumentDom(InputSource inputSource) throws IOException, SAXException{
		List<Patient> patients = new ArrayList<Patient>();
		
		DOMParser parser = new DOMParser();
		try {
		parser.parse(inputSource);
		}catch (IOException e) {
			throw new IOException("Error when calling parser.parse(inputSource) from parseDocumentDom(InputSource inputSource)", e);
		}catch (SAXException e) {
			throw new SAXException("Error when calling parser.parse(inputSource) from parseDocumentDom(InputSource inputSource)", e);
		}
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
		return patients;
	}
	
	private static Element getSingleChild(Element element, String childName) {
		NodeList nodeList = element.getElementsByTagName(childName);
		Element child = (Element) nodeList.item(0);
		return child;
	}


}
