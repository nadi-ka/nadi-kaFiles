package by.epam.ts.bean.treat_status;

public enum TreatmentStatus {
	
	COMPLETED("completed"),
	IN_PROGRESS("in progress");
	
	private final String statusValue;
	
	private TreatmentStatus(String statusValue) {
		this.statusValue = statusValue;
	}
	
	public String getStatusValue() {
		return this.statusValue;
	}
	
	public static TreatmentStatus getTreatmentStatus(String status) {
		for(TreatmentStatus item: values()) {
			if (item.statusValue.equals(status)) {
				return item;
			}
		}
		return getDefault();
	}
	
	public static TreatmentStatus getDefault() {
		return IN_PROGRESS;
	}

}
