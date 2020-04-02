package by.epam.ts.service.validator.core;

public class UserDataValidator {
	
	 private static final String LOGIN_PATTERN = "^[a-zA-Z0-9_-]{3,20}$";
	 private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9_-]{5,20}$";
	 private static final String EMAIL_PATTERN = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";
	 private static final String NAME_PATTERN = "^([a-zA-Z-]|[а-яА-ЯёЁ-]){2,30}$";
	 private static final String SURNAME_PATTERN = "^([a-zA-Z-]|[а-яА-ЯёЁ-]){2,30}$";
	
	public boolean validLogin(String login) {
		return (login != null) && (login.matches(LOGIN_PATTERN));	
	}
	
	public boolean validPassword(String password) {
		return (password != null) && (password.matches(PASSWORD_PATTERN));
	}
	
	public boolean validEmail(String email) {
		return (email != null) && (email.matches(EMAIL_PATTERN));
	}
	
	public boolean validName(String name) {
		return (name != null) && (name.matches(NAME_PATTERN));
	}
	
	public boolean validSurname(String surname) {
		return (surname != null) && (surname.matches(SURNAME_PATTERN));
	}
	

}
