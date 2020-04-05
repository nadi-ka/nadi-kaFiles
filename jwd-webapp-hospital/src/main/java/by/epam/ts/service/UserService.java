package by.epam.ts.service;

import java.util.List;

import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.service.exception.ServiceException;

public interface UserService {
	
	int signUp(String email, String login, String password) throws ServiceException;
	
	User logIn(String login, String password) throws ServiceException;
	
	List<Treatment> getPatientsTreatmentById (String id) throws ServiceException;
	
}
