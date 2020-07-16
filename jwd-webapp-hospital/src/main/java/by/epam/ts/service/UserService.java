package by.epam.ts.service;

import java.util.List;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.User;
import by.epam.ts.service.exception.ServiceException;

public interface UserService {

	User signUp(String email, String login, String password) throws ServiceException;

	User logIn(String login, String password) throws ServiceException;

	List<Patient> getPatientBySurname(String surname) throws ServiceException;

	Patient getPatientById(String id) throws ServiceException;

	String addNewPatient(String surname, String name, String dateBirth, String email) throws ServiceException;

	void setPatientPersonalData(String id, String surname, String name, String dateBirth, String newEmail,
			String oldEmail) throws ServiceException;

	String addNewStaff(String specialty, String surname, String name, String email) throws ServiceException;

	void setStaffPersonalData(String surname, String name, String newEmail, String oldEmail, String id)
			throws ServiceException;

	MedicalStaff getStaffById(String id) throws ServiceException;

	List<MedicalStaff> getUserStaffBySurname(String surname) throws ServiceException;

	void setStaffUserRole(String role, String id) throws ServiceException;

	void setUserStatus(String userStatus, String id) throws ServiceException;

}
