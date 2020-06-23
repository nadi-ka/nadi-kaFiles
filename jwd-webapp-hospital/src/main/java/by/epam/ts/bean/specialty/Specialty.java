package by.epam.ts.bean.specialty;

public enum Specialty {
	
	DOCTOR("doctor"),
	NURSE("nurse"),
	UNKNOWN("unknown");
	
	private final String specialtyValue;
	
	private Specialty(String specialtyValue) {
		this.specialtyValue = specialtyValue;
	}
	
	public String getSpecialtyValue() {
		return this.specialtyValue;
	}
	
	public static Specialty getSpecialty(String value) {
		for(Specialty item: values()) {
			if (item.specialtyValue.equals(value)) {
				return item;
			}
		}
		 return getDefault();
	}
	
	public static Specialty getDefault() {
		return UNKNOWN;
	}

}
