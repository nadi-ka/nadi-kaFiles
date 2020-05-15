package by.epam.ts.bean.role;

public enum UserRole {

	ADMINISTRATOR(1), 
	STAFF(2), 
	PATIENT(3);

	private final int roleValue;

	private UserRole(int roleValue) {
		this.roleValue = roleValue;
	}

	public int getRoleValue() {
		return this.roleValue;
	}

	public static UserRole getUserRole(int role) {
		for (UserRole one : values()) {
			if (one.roleValue == role) {
				return one;
			}
		}
		return getDefault();
	}
	
	public static UserRole getDefault() {
		return PATIENT;
	}

}
