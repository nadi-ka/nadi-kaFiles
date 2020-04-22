package by.epam.ts.service.util.handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;
import by.epam.ts.service.PatientTagName;

public final class PatientSaxHandler extends DefaultHandler {

	private List<Patient> patients = new ArrayList<Patient>();
	private List<Treatment> prescriptions;
	private Patient patient;
	private Treatment treatment;
	private StringBuilder text;

	static final Logger log = LogManager.getLogger(PatientSaxHandler.class);

	public List<Patient> getPatientsList() {
		return patients;
	}

	public void startElement(String uri, String localName, String gName, Attributes attributes) throws SAXException {

		text = new StringBuilder();
		if (gName.equals("patient")) {
			patient = new Patient();
			patient.setId(attributes.getValue("id"));
		} else if (gName.equals("treatment")) {
			treatment = new Treatment();
		} else if (gName.contentEquals("prescriptions")) {
			prescriptions = new ArrayList<Treatment>();
		}
	}

	public void characters(char[] buffer, int start, int length) {
		text.append(buffer, start, length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		PatientTagName tagName = PatientTagName.valueOf(qName.toUpperCase().replace("-", "_"));

		switch (tagName) {
		case PATIENT:
			patients.add(patient);
			patient = null;
			break;
		case SURNAME:
			patient.setSurname(text.toString());
			break;
		case NAME:
			patient.setName(text.toString());
			break;
		case DATE_OF_BIRTH:
			String date = text.toString();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateOfBirth = LocalDate.parse(date, format);
			patient.setDateOfBirth(dateOfBirth);
			break;
		case PRESCRIPTIONS:
			patient.setPrescriptions(prescriptions);
			prescriptions = null;
			break;
		case TREATMENT:
			prescriptions.add(treatment);
			treatment = null;
			break;
		case ID_APPOINTMENT:
			treatment.setIdAppointment(Integer.parseInt(text.toString()));
			break;
		case TREATMENT_NAME:
			treatment.setTreatmentName(text.toString());
			break;
		case DOCTOR_SURNAME:
			treatment.setDoctorSurname(text.toString());
			break;
		}
	}

	public void warning(SAXParseException exception) {
		log.log(Level.WARN, "WARNING: line " + exception.getLineNumber() + ": " + exception);
	}

	public void error(SAXParseException exception) {
		log.log(Level.ERROR, "WARNING: line " + exception.getLineNumber() + ": " + exception);
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		log.log(Level.FATAL, "WARNING: line " + exception.getLineNumber() + ": " + exception);
		throw (exception);
	}

}
