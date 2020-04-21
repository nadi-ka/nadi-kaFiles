package by.epam.ts.dal;

import java.util.List;
import java.util.Map;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;

public interface UserDao {
	
	int addUserPatient(User user) throws DaoException;
	
	int addUserStaff (User user) throws DaoException;
	
	MedicalStaff findStaffByEmail(String email) throws DaoException;
	
	Patient findPatientByEmail(String email) throws DaoException;
	
	User findUserByLoginPassword(String login, String password) 
			throws DaoException;
	
	List<Treatment> findPatientsTreatmentById(String id) throws DaoException;
	
	String findLogin(String login) throws DaoException;
	
	int[] updateConsent(Map<Integer, Boolean> consentMap) throws DaoException;
	
}
