package src.main.java.by.epam.ts.dal;

import src.main.java.by.epam.ts.bean.MedicalStaff;
import src.main.java.by.epam.ts.bean.Patient;
import src.main.java.by.epam.ts.bean.User;

public interface UserDao {
	
	public int addUser (User user, boolean isStaff) throws DaoException;
	
	MedicalStaff findStaffByEmail(String email) throws DaoException;
	
	Patient findPatientByEmail(String email) throws DaoException;
	
	User findUserByLoginPassword(String login, String password) 
			throws DaoException;
	
	void clearConnection();
	
}
