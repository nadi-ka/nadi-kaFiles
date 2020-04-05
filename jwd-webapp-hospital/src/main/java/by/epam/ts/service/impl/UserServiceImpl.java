package by.epam.ts.service.impl;

import java.util.List;
import java.util.Set;

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
import by.epam.ts.service.UserService;
import by.epam.ts.service.constant.ValidationConstant;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.validator.ValidatorManager;

public class UserServiceImpl implements UserService {
	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private UserDao userDao = daoFactory.getUserDao();
	static final Logger log = LogManager.getLogger(UserServiceImpl.class);

	public int signUp(String email, String login, String password) throws ServiceException {
		int updatedRows = 0;
		
		ValidatorManager manager = new ValidatorManager();
		Set<String> invalidDataSet = manager.validSignUpData(login, password, email);
		if (!invalidDataSet.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (String data : invalidDataSet) {
				builder.append(data);
			}
			throw new ValidationServiceException(builder.toString());
		}
		
		//checking, if the login is unique;
		try {
		String checkedLogin = userDao.findLogin(login);
		if (checkedLogin != null) {
			log.log(Level.INFO, "Not unique login.");
			throw new ValidationServiceException(ValidationConstant.INVALID_LOGIN);
		}
		}catch (DaoException ex) {
			log.log(Level.ERROR, "Procedure of checking login, if it's unique, failed.", ex);
			throw new ServiceException("Procedure of checking login, if it's unique, failed.", ex);
		}
		
		String idStaff;
		String idPatient;
		
		//checking, if person exists in table staff or patient;
		idStaff = getStaffIdByEmail(email);
		idPatient = getPatientsIdByEmail(email);
		int roleMedicalStaff = 2;
		int rolePatient = 3;
		boolean userStatus = true;
		User user;
		
		//if id was found in the table staff;
		if (idStaff != null) {
			user = new User(idStaff, login, password, roleMedicalStaff, userStatus);
			try {
			updatedRows = userDao.addUserStaff(user);
			} catch (DaoException ex) {
				log.log(Level.ERROR, "Sign up procedure failed.", ex);
				throw new ServiceException("Sign up procedure failed.", ex);
			}
		}
		//if id was found in the table patient;
		else if (idPatient != null) {
			user = new User(idPatient, login, password, rolePatient, userStatus);
			try {
			updatedRows = userDao.addUserPatient(user);
			} catch (DaoException ex) {
				log.log(Level.ERROR, "Sign up procedure failed.", ex);
				throw new ServiceException("Sign up procedure failed.", ex);
			}
		}
		//id wasn't found in any table;
		else {
			log.log(Level.INFO, "Sign up procedure failed. Given e-mail isn't exist in DB.");
			return updatedRows;
		}	
		return updatedRows;
	}

	public User logIn(String login, String password) throws ServiceException {
		ValidatorManager manager = new ValidatorManager();
		Set<String> invalidDataSet = manager.validLoginData(login, password);
		if (!invalidDataSet.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (String data : invalidDataSet) {
				builder.append(data);
			}
			throw new ValidationServiceException(builder.toString());
		}
		
		User user = null;
		try {
			user = userDao.findUserByLoginPassword(login, password);
		} catch (DaoException ex) {
			log.log(Level.ERROR, "Error during during reading from DB.", ex);
			throw new ServiceException("Error during reading from DB.", ex);
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
		List<Treatment> prescriptions;
		try {
			prescriptions = userDao.findPatientsTreatmentById(id);
		} catch (DaoException ex) {
			log.log(Level.ERROR, "Treatment wasn't found. id=" + id, ex);
			throw new ServiceException("Error during reading from DB.", ex);
		}
		return prescriptions;
	}

}
