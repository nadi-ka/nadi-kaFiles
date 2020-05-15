package by.epam.ts.bean.specialty;

public enum Specialty {
	
	DOCTOR("врач"),
	NURSE("медсестра");
	
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
		throw new IllegalArgumentException();
	}

}
