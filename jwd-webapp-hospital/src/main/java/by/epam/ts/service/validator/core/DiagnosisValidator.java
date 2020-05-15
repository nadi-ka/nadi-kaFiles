package by.epam.ts.service.validator.core;

import java.time.LocalDate;

public class DiagnosisValidator {
	
	private static final String PATIENT_ID_PATTERN = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
	private static final String CODE_BY_ICD_PATTERN = "[A-Za-z0-9.]{3,7}";
	private static final String SETTING_DATE_PATTERN = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
	private static final String DIAGNOSIS_NAME_PATTERN = "^([\\p{L}]|[\\p{N}]|[-.,:\\s]){3,45}$";
	private static final String BED_DAYS_PATTERN = "\\d\\d?";
	
	public boolean validPatientId(String id) {
		return (id != null) && (id.matches(PATIENT_ID_PATTERN));	
	}
	
	public boolean validCodeByICD(String code) {
		return (code != null) && (code.matches(CODE_BY_ICD_PATTERN));	
	}
	
	public boolean validSettindDate(LocalDate date) {
		return (date != null) && (date.toString().matches(SETTING_DATE_PATTERN));	
	}
	
	public boolean validiagnosisName(String diagnosisName) {
		return (diagnosisName != null) && (diagnosisName.toString().matches(DIAGNOSIS_NAME_PATTERN));	
	}
	
	public boolean validBedDays(String bedDays) {
		return (bedDays != null) && (bedDays.matches(BED_DAYS_PATTERN));	
	}

}
