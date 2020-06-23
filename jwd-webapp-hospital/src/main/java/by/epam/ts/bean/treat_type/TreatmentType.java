package by.epam.ts.bean.treat_type;

public enum TreatmentType {
	
	SURGICAL("surgical"),
	PROCEDURES("procedures"),
	CONSERVATIVE("conservative"),
	UNKNOWN("unknown");
	
	private final String typeValue;
	
	private TreatmentType(String typeValue) {
		this.typeValue = typeValue;
	}
	
	public String getTypeValue() {
		return this.typeValue;
	}
	
	public static TreatmentType getTreatmentType(String value) {
		for(TreatmentType item: values()) {
			if (item.typeValue.equals(value)) {
				return item;
			}
		}
		return getDefault();
	}
	
	public static TreatmentType getDefault() {
		return UNKNOWN;
	}

}
