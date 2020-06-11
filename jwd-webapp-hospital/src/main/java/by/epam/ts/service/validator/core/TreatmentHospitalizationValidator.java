package by.epam.ts.service.validator.core;

public class TreatmentHospitalizationValidator {
	
	private static final String ID_PATTERN = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
	private static final String TREATMENT_TYPE_PATTERN = "^([\\p{L}\\s]){5,20}$";
	private static final String DATE_PATTERN = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
	private static final String MEDICAL_HISTORY_PATTERN = "^[1-9]\\d*$";
	private static final int MAX_LENGTH_TREATMENT = 255;
	private static final String TREATMENT_STATUS_PATTERN = "^[a-zA-Z\\s]{5,20}$";
	private static final String ID_APPOINTMENT_PATTERN = "^[1-9]\\d*$";
	private static final String POSITIVE_CONSENT = "true";
	private static final String NEGATIVE_CONSENT = "false";
	
	public boolean validId(String id) {
		return (id != null) && (id.matches(ID_PATTERN));	
	}
	
	public boolean validTreatmentType(String treatmentType) {
		return (treatmentType != null) && (treatmentType.matches(TREATMENT_TYPE_PATTERN));	
	}
	
	public boolean validTreatmentName(String treatmentName) {
		return (treatmentName != null) && !treatmentName.isEmpty() && (treatmentName.length() <= MAX_LENGTH_TREATMENT);	
	}
	
	public boolean validDate(String date) {
		return (date != null) && (date.matches(DATE_PATTERN));	
	}
	
	public boolean validMedicalHistory(String idHistory) {
		return (idHistory != null) && (idHistory.matches(MEDICAL_HISTORY_PATTERN));	
	}
	
	public boolean validTreatmentStatus(String treatmentStatus) {
		return (treatmentStatus != null) && (treatmentStatus.matches(TREATMENT_STATUS_PATTERN));	
	}
	
	public boolean validIdAppointment(String idAppointment) {
		return (idAppointment != null) && (idAppointment.matches(ID_APPOINTMENT_PATTERN));	
	}
	
	public boolean validConsent(String consent) {
		return (consent != null) && (consent.equals(POSITIVE_CONSENT) || consent.equals(NEGATIVE_CONSENT));	
	}

}
