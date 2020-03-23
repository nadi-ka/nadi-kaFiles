package by.epam.ts.service.serviceImpl;

import java.util.List;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.daoFactory.DaoFactory;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;

public class UserServiceImpl implements UserService {
	private DaoFactory daoFactory;
	private UserDao userDao;

	public UserServiceImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
		userDao = daoFactory.getUserDao();
	}

	public int signUp(String email, String login, String password, boolean isStaff) throws ServiceException {
		int updatedRows = 0;
		String id;
		int role;
		boolean userStatus = true;
		if (isStaff) {
			id = getStaffIdByEmail(email);
			role = 2;
		} else {
			id = getPatientsIdByEmail(email);
			role = 3;
		}

		if (id == null) {
			return updatedRows;
		}
		User user = new User(id, login, password, role, userStatus);
		try {
			updatedRows = userDao.addUser(user, isStaff);
		} catch (DaoException ex) {
			throw new ServiceException("Sign up procedure failed.", ex);
		}
		return updatedRows;
	}

	public User logIn(String login, String password) throws ServiceException {
		User user = null;
		try {
			user = userDao.findUserByLoginPassword(login, password);
		} catch (DaoException ex) {
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
			throw new ServiceException("Error during reading from DB.", ex);
		}
		return prescriptions;
	}

}
