package by.epam.ts.dal;

import by.epam.ts.beans.MedicalStaffBean;
import by.epam.ts.beans.PatientBean;
import by.epam.ts.beans.UserBean;

public interface UserDao {
	
	public int addUser (UserBean user, boolean isStaff) throws DaoException;
	
	MedicalStaffBean findStaffByEmail(String email) throws DaoException;
	
	PatientBean findPatientByEmail(String email) throws DaoException;
	
	UserBean findUserByLoginPassword(String login, String password) 
			throws DaoException;
	
	void clearConnection();
	
}
