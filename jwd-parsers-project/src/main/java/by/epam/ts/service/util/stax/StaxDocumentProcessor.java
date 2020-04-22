package by.epam.ts.service.util.stax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;
import by.epam.ts.service.PatientTagName;

public final class StaxDocumentProcessor {

	public List<Patient> process(XMLStreamReader reader) throws XMLStreamException {
		List<Patient> patients = new ArrayList<Patient>();
		List<Treatment> prescriptions = null;
		Patient patient = null;
		Treatment treatment = null;
		PatientTagName elementName = null;

		while (reader.hasNext()) {
			// definition of the element's type
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				elementName = PatientTagName.getPatientTagName(reader.getLocalName());
				switch (elementName) {
				case PATIENT:
					patient = new Patient();
					String id = reader.getAttributeValue(null, "id");
					patient.setId(id);
					break;
				case TREATMENT:
					treatment = new Treatment();
					break;
				case PRESCRIPTIONS:
					prescriptions = new ArrayList<Treatment>();
				}
				break;

			case XMLStreamConstants.CHARACTERS:
				String text = reader.getText().trim();
				if (text.isEmpty()) {
					break;
				}
				switch (elementName) {
				case SURNAME:
					patient.setSurname(text);
					break;
				case NAME:
					patient.setName(text);
					break;
				case DATE_OF_BIRTH:
					String date = text.toString();
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate dateOfBirth = LocalDate.parse(date, format);
					patient.setDateOfBirth(dateOfBirth);
					break;
				case ID_APPOINTMENT:
					treatment.setIdAppointment(Integer.parseInt(text));
					break;
				case TREATMENT_NAME:
					treatment.setTreatmentName(text);
					break;
				case DOCTOR_SURNAME:
					treatment.setDoctorSurname(text);
					break;
				}
				break;

			case XMLStreamConstants.END_ELEMENT:
				elementName = PatientTagName.getPatientTagName(reader.getLocalName());
				switch (elementName) {
				case PATIENT:
					patients.add(patient);
					break;
				case TREATMENT:
					prescriptions.add(treatment);
					break;
				case PRESCRIPTIONS:
					patient.setPrescriptions(prescriptions);
				}

			}

		}
		return patients;
	}

}
