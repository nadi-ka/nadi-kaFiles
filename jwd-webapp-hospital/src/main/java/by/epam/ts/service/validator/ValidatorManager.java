package by.epam.ts.service.validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import by.epam.ts.service.validator.core.UserDataValidator;

public class ValidatorManager {
	
	private static final String KEY_SURNAME = "surname";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_LOGIN = "login";
	private static final String KEY_PASSWORD = "password";
	
	private Set<String> checkInvalidData (Map<String, Boolean> validResults) {
		Set<String> invalidData = new HashSet<String>();
		for (Entry<String, Boolean> entry: validResults.entrySet()) {
			if (!entry.getValue()) {
				invalidData.add(entry.getKey());
			}
		}
		return invalidData;	
	}
	
	public Set<String> validSignUpData(String login, String password, String email) {
		UserDataValidator validator = new UserDataValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();
		
		validResults.put(KEY_LOGIN, validator.validLogin(login));
		validResults.put(KEY_PASSWORD, validator.validPassword(password));
		validResults.put(KEY_EMAIL, validator.validEmail(email));
		
		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}
	
	public Set<String> validLoginData(String login, String password) {
		UserDataValidator validator = new UserDataValidator();
		Map<String, Boolean> validResults = new HashMap<String, Boolean>();
		
		validResults.put(KEY_LOGIN, validator.validLogin(login));
		validResults.put(KEY_PASSWORD, validator.validPassword(password));
		
		Set<String> invalidData = checkInvalidData(validResults);
		return invalidData;
	}

}
