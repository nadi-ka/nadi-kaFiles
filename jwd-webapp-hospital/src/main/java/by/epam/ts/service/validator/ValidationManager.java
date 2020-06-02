package by.epam.ts.service.validator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import by.epam.ts.service.validator.core.DiagnosisValidator;
import by.epam.ts.service.validator.core.PersonalDataValidator;
import by.epam.ts.service.validator.core.TreatmentHospitalizationValidator;

public class ValidationManager {

	private static final String KEY_SURNAME = "surname";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_LOGIN = "login";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_DATE_OF_BIRTH = "date_of_birth";
	private static final String KEY_ID = "id";
	private static final String KEY_CODE = "code";
	private static final String KEY_CONSENT = "consent";
	private static final String KEY_SETTING_DATE = "setting_date";
	private static final String KEY_DIAGNOSIS_NAME = "diagnosis_name";
	private static final String KEY_BED_DAYS = "bed_days";
	private static final String KEY_TREATMENT_TYPE = "treatment_type";
	private static final String KEY_TREATMENT_NAME = "treatment_name";
	private static final String KEY_STAFF_ID = "staff_id";
	private static final String KEY_DATE_BEGINNING = "date_beginning";
	private static final String KEY_DATE_FINISHING = "date_finishing";
	private static final String KEY_DATE_PERFORMING = "date_performing";
	private static final String KEY_SPECIALTY = "specialty";
	private static final String KEY_MEDICAL_HISTORY = "id_history";
	private static final String KEY_ID_APPOINTMENT = "id_appointment";
	private static final String KEY_TREATMENT_STATUS = "treatment_status";

	private Set<String> checkInvalidData(Map<String, Boolean> validResults) {
		Set<String> invalidData = new HashSet<String>();
		// if during iterations boolean value==false, correspondent key will be written
		// in Set;
		for (Entry<String, Boolean> entry : validResults.entrySet()) {
			if (!entry.getValue()) {
				invalidData.add(entry.getKey());
			}
		}
		return invalidData;
	}

	public Set<String> validateSignUpData(String login, String password, String email) {
		PersonalDataValidator validator = new PersonalDataValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_LOGIN, validator.validLogin(login));
		validResults.put(KEY_PASSWORD, validator.validPassword(password));
		validResults.put(KEY_EMAIL, validator.validEmail(email));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validateLoginData(String login, String password) {
		PersonalDataValidator validator = new PersonalDataValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_LOGIN, validator.validLogin(login));
		validResults.put(KEY_PASSWORD, validator.validPassword(password));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validatePatientPersonalData(String surname, String name, String email, String dateOfBirth) {
		PersonalDataValidator validator = new PersonalDataValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_SURNAME, validator.validSurname(surname));
		validResults.put(KEY_NAME, validator.validName(name));
		validResults.put(KEY_DATE_OF_BIRTH, validator.validDateOfBirth(dateOfBirth));
		validResults.put(KEY_EMAIL, validator.validEmail(email));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validatePatientDiagnosisData(String patientId, String codeDisease, LocalDate settingDate) {
		DiagnosisValidator validator = new DiagnosisValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_ID, validator.validPatientId(patientId));
		validResults.put(KEY_CODE, validator.validCodeByICD(codeDisease));
		validResults.put(KEY_SETTING_DATE, validator.validSettindDate(settingDate));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validateNewDiagnosisData(String codeDiagnosis, String diagnosisName, String numberBedDays) {
		DiagnosisValidator validator = new DiagnosisValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_CODE, validator.validCodeByICD(codeDiagnosis));
		validResults.put(KEY_DIAGNOSIS_NAME, validator.validiagnosisName(diagnosisName));
		validResults.put(KEY_BED_DAYS, validator.validBedDays(numberBedDays));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validateTreatmentData(String idPatient, String treatmentType, String treatmentName,
			String idDoctor, String dateBegin, String dateFinish) {
		TreatmentHospitalizationValidator validator = new TreatmentHospitalizationValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_ID, validator.validId(idPatient));
		validResults.put(KEY_TREATMENT_TYPE, validator.validTreatmentType(treatmentType));
		validResults.put(KEY_TREATMENT_NAME, validator.validTreatmentName(treatmentName));
		validResults.put(KEY_STAFF_ID, validator.validId(idDoctor));
		validResults.put(KEY_DATE_BEGINNING, validator.validDate(dateBegin));
		validResults.put(KEY_DATE_FINISHING, validator.validDate(dateFinish));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;

	}

	public Set<String> validateStaffPersonalData(String specialty, String surname, String name, String email) {
		PersonalDataValidator validator = new PersonalDataValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_SPECIALTY, validator.validSpecialty(specialty));
		validResults.put(KEY_SURNAME, validator.validSurname(surname));
		validResults.put(KEY_NAME, validator.validName(name));
		validResults.put(KEY_EMAIL, validator.validEmail(email));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validateHospitalizationData(String idPatient, String entryDate) {
		TreatmentHospitalizationValidator validator = new TreatmentHospitalizationValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_ID, validator.validId(idPatient));
		validResults.put(KEY_DATE_BEGINNING, validator.validDate(entryDate));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validateDischargeData(String dischargeDate, String entryDate, String idHistory) {
		TreatmentHospitalizationValidator validator = new TreatmentHospitalizationValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_DATE_FINISHING, validator.validDate(dischargeDate));
		validResults.put(KEY_DATE_BEGINNING, validator.validDate(entryDate));
		validResults.put(KEY_MEDICAL_HISTORY, validator.validMedicalHistory(idHistory));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validateCurrentTreatmentData(String consent, String idAppointment, String datePerforming,
			String idPerformer, String status) {
		TreatmentHospitalizationValidator validator = new TreatmentHospitalizationValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_CONSENT, validator.validConsent(consent));
		validResults.put(KEY_ID_APPOINTMENT, validator.validIdAppointment(idAppointment));
		validResults.put(KEY_DATE_PERFORMING, validator.validDate(datePerforming));
		validResults.put(KEY_STAFF_ID, validator.validId(idPerformer));
		validResults.put(KEY_TREATMENT_STATUS, validator.validTreatmentStatus(status));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

	public Set<String> validateConsent(String idAppointment, String consent) {
		TreatmentHospitalizationValidator validator = new TreatmentHospitalizationValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();

		validResults.put(KEY_ID_APPOINTMENT, validator.validIdAppointment(idAppointment));
		validResults.put(KEY_CONSENT, validator.validConsent(consent));

		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

}
