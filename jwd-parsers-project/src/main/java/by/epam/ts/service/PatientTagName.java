package by.epam.ts.service;

public enum PatientTagName {

	PATIENTS, 
	PATIENT, 
	SURNAME, 
	NAME, 
	DATE_OF_BIRTH, 
	PRESCRIPTIONS, 
	TREATMENT, 
	ID_APPOINTMENT, 
	TREATMENT_NAME,
	DOCTOR_SURNAME;

	public static PatientTagName getPatientTagName(String element) {
		switch (element) {
		case "patients":
			return PATIENTS;
		case "patient":
			return PATIENT;
		case "surname":
			return SURNAME;
		case "name":
			return NAME;
		case "date-of-birth":
			return DATE_OF_BIRTH;
		case "prescriptions":
			return PRESCRIPTIONS;
		case "treatment":
			return TREATMENT;
		case "id-appointment":
			return ID_APPOINTMENT;
		case "treatment-name":
			return TREATMENT_NAME;
		case "doctor-surname":
			return DOCTOR_SURNAME;
		default:
			throw new EnumConstantNotPresentException(PatientTagName.class, element);

		}

	}

}
