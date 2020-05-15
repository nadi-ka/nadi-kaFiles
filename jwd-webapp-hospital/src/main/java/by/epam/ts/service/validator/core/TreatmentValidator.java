package by.epam.ts.service.validator.core;

public class TreatmentValidator {
	
	private static final String ID_PATTERN = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
	private static final String TREATMENT_TYPE_PATTERN = "^([\\p{L}]){5,20}$";
	private static final String DATE_PATTERN = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
	private static final int MAX_LENGTH = 255;
	
	public boolean validId(String id) {
		return (id != null) && (id.matches(ID_PATTERN));	
	}
	
	public boolean validTreatmentType(String treatmentType) {
		return (treatmentType != null) && (treatmentType.matches(TREATMENT_TYPE_PATTERN));	
	}
	
	public boolean validTreatmentName(String treatmentName) {
		return (treatmentName != null) && !treatmentName.isEmpty() && (treatmentName.length() <= MAX_LENGTH);	
	}
	
	public boolean validDate(String date) {
		return (date != null) && (date.matches(DATE_PATTERN));	
	}

}
