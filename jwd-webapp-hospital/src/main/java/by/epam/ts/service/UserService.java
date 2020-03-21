package src.main.java.by.epam.ts.service;

import src.main.java.by.epam.ts.bean.User;

public interface UserService {
	
	int signUp(String email, String login, String password, boolean isStaff) 
			throws ServiceException;
	
	User logIn(String login, String password) throws ServiceException;
	
	void clearConnection();

}
