package by.epam.ts.service.impl;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;

public class UserServiceImpl implements UserService {
	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private UserDao userDao = daoFactory.getUserDao();
	static final Logger log = LogManager.getLogger( UserServiceImpl.class);

	public int signUp(String email, String login, String password) throws ServiceException {
		int updatedRows = 0;
		String idStaff;
		String idPatient;
		idStaff = getStaffIdByEmail(email);
		idPatient = getPatientsIdByEmail(email);
		int roleMedicalStaff = 2;
		int rolePatient = 3;
		boolean userStatus = true;
		User user;
		if (idStaff != null) {
			user = new User(idStaff, login, password, roleMedicalStaff, userStatus);
			try {
			updatedRows = userDao.addUserStaff(user);
			} catch (DaoException ex) {
				log.log(Level.ERROR, "Sign up procedure failed.", ex);
				throw new ServiceException("Sign up procedure failed.", ex);
			}
		}
		else if (idPatient != null) {
			user = new User(idPatient, login, password, rolePatient, userStatus);
			try {
			updatedRows = userDao.addUserPatient(user);
			} catch (DaoException ex) {
				log.log(Level.ERROR, "Sign up procedure failed.", ex);
				throw new ServiceException("Sign up procedure failed.", ex);
			}
		}
		else {
			return updatedRows;
		}	
		return updatedRows;
	}

	public User logIn(String login, String password) throws ServiceException {
		User user = null;
		try {
			user = userDao.findUserByLoginPassword(login, password);
		} catch (DaoException ex) {
			log.log(Level.ERROR, "Error during calling method findUserByLoginPassword()", ex);
			throw new ServiceException("Error during reading from DB.", ex);
		}
		if (user == null) {
			log.info("User == null. login=" + login );
			throw new ServiceException("User wasn't found. login = " + login);
		}
		return user;
	}

	private String getStaffIdByEmail(String email) throws ServiceException {
		MedicalStaff medicalStaff = null;
		String id = null;
		try {
			medicalStaff = userDao.findStaffByEmail(email);
		} catch (DaoException ex) {
			log.log(Level.ERROR, "Staff wasn't found: " + email, ex);
			throw new ServiceException("Error during reading from DB.", ex);
		}
		if (medicalStaff != null) {
			id = medicalStaff.getId();
		}
		return id;
	}

	private String getPatientsIdByEmail(String email) throws ServiceException {
		Patient patient = null;
		String id = null;
		try {
			patient = userDao.findPatientByEmail(email);
		} catch (DaoException ex) {
			log.log(Level.ERROR, "Patient wasn't found: " + email, ex);
			throw new ServiceException("Error during reading from DB.", ex);
		}
		if (patient != null) {
			id = patient.getId();
		}
		return id;
	}

	public List<Treatment> getPatientsTreatmentById(String id) throws ServiceException {
		List<Treatment> prescriptions = null;
		try {
			prescriptions = userDao.findPatientsTreatmentById(id);
		} catch (DaoException ex) {
			log.log(Level.ERROR, "Treatment by id wasn't found. id: " + id, ex);
			throw new ServiceException("Error during reading from DB.", ex);
		}
		return prescriptions;
	}

}
