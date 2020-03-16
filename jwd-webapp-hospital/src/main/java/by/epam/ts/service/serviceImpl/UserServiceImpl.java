package by.epam.ts.service.serviceImpl;

import by.epam.ts.beans.MedicalStaffBean;
import by.epam.ts.beans.PatientBean;
import by.epam.ts.beans.UserBean;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.daoFactory.DaoFactory;
import by.epam.ts.dal.daoFactory.daoFactoryImpl.DaoFactoryImpl;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;

public class UserServiceImpl implements UserService {
	private DaoFactory daoFactory;
	private UserDao userDao;

	public UserServiceImpl() throws ServiceException {
		try {
			daoFactory = DaoFactoryImpl.getInstance();
			userDao = daoFactory.getUserDao();
		} catch (DaoException ex) {
			throw new ServiceException("The instance of daoFactory wasn't created.");
		}
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
		UserBean user = new UserBean(id, login, password, role, userStatus);
		try {
			updatedRows = userDao.addUser(user, isStaff);
		} catch (DaoException ex) {
			throw new ServiceException("Sign up procedure failed", ex);
		}
		return updatedRows;
	}

	public UserBean logIn(String login, String password) throws ServiceException {
		UserBean user;
		try {
			user = userDao.findUserByLoginPassword(login, password);
		} catch (DaoException ex) {
			throw new ServiceException("Error during reading from DB.", ex);
		}
		if (user == null) {
			throw new ServiceException("The user wasn't found. " + "login=" + login + ", password=" + password);
		}
		return user;
	}

	private String getStaffIdByEmail(String email) throws ServiceException {
		MedicalStaffBean medicalStaffBean;
		try {
			medicalStaffBean = userDao.findStaffByEmail(email);
		} catch (DaoException ex) {
			throw new ServiceException("Error during reading from DB.", ex);
		}
		if (medicalStaffBean == null) {
			throw new ServiceException("Person with this email wasn't found." + email);
		}
		return medicalStaffBean.getId();
	}

	private String getPatientsIdByEmail(String email) throws ServiceException {
		PatientBean patientBean;
		try {
			patientBean = userDao.findPatientByEmail(email);
		} catch (DaoException ex) {
			throw new ServiceException("Error during reading from DB.", ex);
		}
		if (patientBean == null) {
			throw new ServiceException("Person with this email wasn't found." + email);
		}
		return patientBean.getId();
	}

	public static void main(String[] args) {
		try {
			UserServiceImpl userServiceImpl = new UserServiceImpl();
			System.out.println(userServiceImpl.logIn("бета", "бета222").toString());
		} catch (ServiceException ex) {
			ex.printStackTrace();
		}
	}

}
