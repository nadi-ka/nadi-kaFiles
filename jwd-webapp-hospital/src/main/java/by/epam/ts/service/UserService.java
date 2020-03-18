package by.epam.ts.service;

import by.epam.ts.beans.UserBean;

public interface UserService {
	
	int signUp(String email, String login, String password, boolean isStaff) 
			throws ServiceException;
	
	UserBean logIn(String login, String password) throws ServiceException;
	
	void clearConnection();

}
