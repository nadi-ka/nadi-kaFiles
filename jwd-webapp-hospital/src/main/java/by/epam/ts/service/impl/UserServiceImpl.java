package by.epam.ts.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.User;
import by.epam.ts.bean.role.UserRole;
import by.epam.ts.bean.specialty.Specialty;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.service.UserService;
import by.epam.ts.service.constant.ValidationConstant;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.util.StaffUserRoleQualifier;
import by.epam.ts.service.util.hash_manager.HashManager;
import by.epam.ts.service.validator.ValidationManager;

public class UserServiceImpl implements UserService {

	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private UserDao userDao = daoFactory.getUserDao();

	static final Logger log = LogManager.getLogger(UserServiceImpl.class);

	public User signUp(String email, String login, String password) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateSignUpData(login, password, email);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		try {
			// check, if the login is unique;
			String checkedLogin = userDao.findLogin(login);
			if (checkedLogin != null) {
				throw new ValidationServiceException(ValidationConstant.INVALID_LOGIN);
			}
			// hashing the password, using BCryptPasswordEncoder;
			String hashedPassword = HashManager.hashPassword(password);

			// search the person in the table patient;
			Patient patient = userDao.findPatientByEmail(email);
			User user = null;
			boolean userStatus = true;
			int updatedRows = 0;
			if (patient != null) {
				user = new User(patient.getId(), login, hashedPassword, UserRole.PATIENT, userStatus);
				updatedRows = userDao.createUserPatient(user);
				return (updatedRows != 0) ? user : new User();
			}
			// search the person in the table medical-staff;
			MedicalStaff staff = userDao.findStaffByEmail(email);
			if (staff != null) {
				StaffUserRoleQualifier qualifier = new StaffUserRoleQualifier();
				UserRole staffRole = qualifier.qualifyStaffUserRole(staff);
				user = new User(staff.getId(), login, hashedPassword, staffRole, userStatus);
				updatedRows = userDao.createUserStaff(user);
				return (updatedRows != 0) ? user : new User();
			}
			// the person wasn't found in any table;
			throw new ValidationServiceException(ValidationConstant.NOT_ACTUAL_EMAIL);

		} catch (DaoException ex) {
			throw new ServiceException("Error when calling signUp() from UserServiceImpl", ex);
		}
	}

	public User logIn(String login, String password) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateLoginData(login, password);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		User user = null;
		try {
			user = userDao.findUserByLogin(login);
			boolean checkResult = false;
			boolean userStatus = false;
			if (user != null) {
				// check password;
				checkResult = HashManager.checkPassword(password, user.getPassword());
				// check user's status
				userStatus = user.isUserStatus();
			}
			if (user == null || !checkResult || !userStatus) {
				return new User();
			}
		} catch (DaoException ex) {
			throw new ServiceException("Error during reading from DB.", ex);
		}
		return user;
	}

	public List<Patient> getPatientBySurname(String surname) throws ServiceException {
		/*
		 * checking if the patient with such surname already exists. If not or parameter
		 * surname is not valid, will return empty List;
		 */
		List<Patient> patients = new ArrayList<Patient>();
		if (surname == null || surname.isEmpty()) {
			return patients;
		}
		try {
			patients = userDao.findPatientBySurname(surname);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findPatientBySurname(patient.getSurname()) from method addNewPatient(Patient patient)",
					ex);
		}
		return patients;
	}

	public Patient getPatientById(String id) throws ServiceException {
		if (id == null || id.isEmpty()) {
			throw new ServiceException("Error when calling getPatientById(String id) from UserServiceImp. id=" + id);
		}
		Patient patient;

		try {
			patient = userDao.findPatientById(id);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findPatientById(id) from method getPatientById(String id) from UserServiseImpl",
					ex);
		}
		if (patient == null) {
			throw new ServiceException(
					"When calling userDao.findPatientById(id) from method getPatientById from UserServiseImpl - patient  wasn't found");
		}
		return patient;
	}

	public String addNewPatient(String surname, String name, String dateBirth, String email) throws ServiceException {
		// Validation of personal data;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validatePatientPersonalData(surname, name, email, dateBirth);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		/*
		 * checking if patient's e-mail is unique. In other case it's not allowed to add
		 * the patient, e-mail address should be actual, valid and unique;
		 */
		Patient otherPatient;
		try {
			otherPatient = userDao.findPatientByEmail(email);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findPatientByEmail(email) from method addNewPatient(Patient patient)");
		}
		// (otherPatient != null) means that the e-mail already exists in DB;
		if (otherPatient != null) {
			throw new ValidationServiceException(ValidationConstant.NOT_ACTUAL_EMAIL);
		}
		// adding of new patient. Generating of unique ID;
		String id = UUID.randomUUID().toString();
		LocalDate birthday = LocalDate.parse(dateBirth);
		Patient patient = new Patient(id, surname, name, birthday, email);
		int effectedRows = 0;
		try {
			effectedRows = userDao.createNewPatient(patient);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.createNewPatient(patient) from method addNewPatient(Patient patient) from UserServiceImpl.");
		}
		if (effectedRows == 0) {
			throw new ServiceException(
					"The patient wasn't added. EffectedRows, when calling userDao.createNewPatient(patient) from UserServiceImpl, == 0");
		}
		return id;
	}

	public void setPatientPersonalData(String id, String surname, String name, String dateBirth, String newEmail,
			String oldEmail) throws ServiceException {
		// Validation of personal data;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validatePatientPersonalData(surname, name, newEmail, dateBirth);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// check, if the new e-mail is unique in case if old and new e-mails are
		// different;
		if (!newEmail.equals(oldEmail)) {
			Patient other;
			try {
				other = userDao.findPatientByEmail(newEmail);
			} catch (DaoException ex) {
				throw new ServiceException(
						"Error when calling userDao.findPatientByEmail(email) from method setPatientPersonalData() from UserServiceImpl.");
			}
			// (other != null) means that the e-mail already exists in DB;
			if (other != null) {
				throw new ValidationServiceException(ValidationConstant.NOT_ACTUAL_EMAIL);
			}
		}
		// update patient's personal data;
		LocalDate birthday = LocalDate.parse(dateBirth);
		Patient patient = new Patient(id, surname, name, birthday, newEmail);
		int effectedRows = 0;
		try {
			effectedRows = userDao.updatePatientPersonalData(patient);
			if (effectedRows == 0) {
				throw new ServiceException(
						"The patient wasn't updated. EffectedRows, when calling userDao.updatePatientPersonalData() from UserServiceImpl == 0");
			}
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.updatePatientPersonalData(patient) from method setPatientPersonalData() from UserServiceImpl.");
		}
	}

	public String addNewStaff(String specialty, String surname, String name, String email) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateStaffPersonalData(specialty, surname, name, email);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		/*
		 * checking if e-mail is unique. In other case it's not allowed to add the
		 * person, e-mail address should be actual, valid and unique;
		 */
		MedicalStaff other;
		try {
			other = userDao.findStaffByEmail(email);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findStaffByEmail(email) from method addNewStaff() from UserServiceImpl.");
		}
		// (other != null) means that the e-mail already exists in DB;
		if (other != null) {
			log.info(other.toString());
			throw new ValidationServiceException(ValidationConstant.NOT_ACTUAL_EMAIL);
		}
		// Adding of the new staff;
		Specialty spec = Specialty.getSpecialty(specialty);
		String id = UUID.randomUUID().toString();
		MedicalStaff medicalStaff = new MedicalStaff(id, spec, surname, name, email);

		int effectedRows = 0;
		try {
			effectedRows = userDao.createNewStaff(medicalStaff);
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.createNewStaff(medicalStaff) from addNewStaff() from UserServiceImpl",
					e);
		}
		if (effectedRows == 0) {
			throw new ServiceException(
					"When calling userDao.createNewStaff(medicalStaff) from addNewStaff() from UserServiceImpl new staff wasn't added. Effected rows == 0");
		}
		return id;
	}

	public void setStaffPersonalData(String surname, String name, String newEmail, String oldEmail, String id)
			throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateStaffPersonalData(surname, name, newEmail);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// check, if the new e-mail is unique in case if old and new e-mails are
		// different;
		if (!newEmail.equals(oldEmail)) {
			MedicalStaff other;
			try {
				other = userDao.findStaffByEmail(newEmail);
			} catch (DaoException ex) {
				throw new ServiceException(
						"Error when calling userDao.findStaffByEmail(email) from method setStaffPersonalData() from UserServiceImpl.");
			}
			// (other != null) means that the e-mail already exists in DB;
			if (other != null) {
				throw new ValidationServiceException(ValidationConstant.NOT_ACTUAL_EMAIL);
			}
		}
		// Update staff's personal data;
		int effectedRows = 0;
		try {
			effectedRows = userDao.updateStaffPersonalData(surname, name, newEmail, id);
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.updateStaffPersonalData() from the method setStaffPersonalData() from UserServiceImpl",
					e);
		}
		if (effectedRows == 0) {
			throw new ServiceException(
					"When calling setStaffPersonalData() from UserServiceImpl staff's personal data wasn't updated. Effected rows == 0");
		}
	}

	public MedicalStaff getStaffById(String id) throws ServiceException {

		if (id == null || id.isEmpty()) {
			throw new ServiceException("Error when calling getStaffById(String id) from UserServiceImp. id=" + id);
		}
		MedicalStaff staff = null;
		try {
			staff = userDao.findStaffById(id);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findStaffById(id) from method getStaffById(String id) from UserServiseImpl",
					ex);
		}
		if (staff == null) {
			throw new ServiceException(
					"When calling userDao.findStaffById(id) from method getStaffById(String id) from UserServiseImpl - staff wasn't found.");
		}
		return staff;
	}

	public List<MedicalStaff> getUserStaffBySurname(String surname) throws ServiceException {
		List<MedicalStaff> staffList;
		try {
			staffList = userDao.findUserStaffBySurname(surname);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling getStaffBySurname() from UserServiseImpl", ex);
		}
		return staffList;
	}

	public void setStaffUserRole(String role, String id) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateUserRole(role, id);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		int userRole = UserRole.valueOf(role).getRoleValue();
		int effectedRows = 0;
		try {
			effectedRows = userDao.updateStaffUserRole(userRole, id);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling userDao.updateStaffUserRole() from setStaffUserRole() from UserServiceImpl, the role wasn't updated. Effected rows == 0.");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.updateStaffUserRole() from setStaffUserRole() from UserServiceImpl.",
					e);
		}
	}

	public void setUserStatus(String userStatus, String id) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateUserStatus(userStatus, id);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		boolean status = Boolean.parseBoolean(userStatus);
		int effectedRows = 0;
		try {
			effectedRows = userDao.updateUserStatus(status, id);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling userDao.updateUserStatus() from setUserStatus() from UserServiceImpl status wasn't updated. Effected rows == 0.");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.updateUserStatus() from setUserStatus() from UserServiceImpl.", e);
		}
	}
}
