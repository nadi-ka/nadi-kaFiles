package by.epam.ts.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
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
import by.epam.ts.service.validator.ValidationManager;
import by.epam.ts.service.validator.core.PersonalDataValidator;

public class UserServiceImpl implements UserService {
	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private UserDao userDao = daoFactory.getUserDao();

	static final Logger log = LogManager.getLogger(UserServiceImpl.class);

	public int signUp(String email, String login, String password) throws ServiceException {
		int updatedRows = 0;

		// checking, if all coming data are valid;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateSignUpData(login, password, email);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		String idStaff;
		String idPatient;
		try {
			// checking, if the login is unique;
			String checkedLogin = userDao.findLogin(login);
			if (checkedLogin != null) {
				throw new ValidationServiceException(ValidationConstant.INVALID_LOGIN);
			}
			// checking, if person exists in table staff or patient. In other case there's
			// not allowed to sign up.;
			idStaff = getStaffIdByEmail(email);
			idPatient = getPatientsIdByEmail(email);
		} catch (DaoException ex) {
			throw new ServiceException("Procedure of checking login, if it's unique, failed.", ex);
		}

		boolean userStatus = true;
		User user;

		// if id was found in the table staff;
		if (idStaff != null) {
			user = new User(idStaff, login, password, UserRole.STAFF, userStatus);
			try {
				updatedRows = userDao.createUserStaff(user);
			} catch (DaoException ex) {
				throw new ServiceException("Sign up procedure failed.", ex);
			}
		}
		// if id was found in the table patient;
		else if (idPatient != null) {
			user = new User(idPatient, login, password, UserRole.PATIENT, userStatus);
			try {
				updatedRows = userDao.createUserPatient(user);
			} catch (DaoException ex) {
				throw new ServiceException("Sign up procedure failed.", ex);
			}
		}
		// id wasn't found in any table;
		else {
			log.log(Level.INFO, "Sign up procedure failed. Given e-mail isn't exist in DB.");
			return updatedRows;
		}
		return updatedRows;
	}

	public User logIn(String login, String password) throws ServiceException {
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateLoginData(login, password);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		User user = null;
		try {
			user = userDao.findUserByLoginPassword(login, password);
		} catch (DaoException ex) {
			throw new ServiceException("Error during reading from DB.", ex);
		}
		return user;
	}

	private String getStaffIdByEmail(String email) throws DaoException {
		MedicalStaff medicalStaff = null;
		String id = null;
		try {
			medicalStaff = userDao.findStaffByEmail(email);
		} catch (DaoException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		}
		if (medicalStaff != null) {
			id = medicalStaff.getId();
		}
		return id;
	}

	private String getPatientsIdByEmail(String email) throws DaoException {
		Patient patient = null;
		String id = null;
		try {
			patient = userDao.findPatientByEmail(email);
		} catch (DaoException ex) {
			throw new DaoException("Error during reading from DB.", ex);
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
			Collections.sort(prescriptions, Treatment.treatmentDateComparator);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findPatientsTreatmentById(id).", ex);
		}
		return prescriptions;
	}

	public List<PatientDiagnosis> getSortedPatientDiagnosisById(String id) throws ServiceException {
		List<PatientDiagnosis> diagnosisList;
		try {
			diagnosisList = userDao.findPatientsDiagnosisById(id);
			Collections.sort(diagnosisList, PatientDiagnosis.primaryDiagnosisComparator);
			Collections.sort(diagnosisList, PatientDiagnosis.diagnosisDateComparator);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findPatientsDiagnosisById(id)", ex);
		}
		return diagnosisList;
	}

	public void getPatientsConsent(Map<Integer, Boolean> consentMap) throws ServiceException {
		int[] consent;
		try {
			consent = userDao.updateConsent(consentMap);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.updateConsent(consentMap). Consent wasn't updated.",
					ex);
		}
		// check if consent for every procedure was changed successfully;
		for (int i = 0; i < consent.length; i++) {
			if (consent[i] == 0) {
				throw new ServiceException(
						"Error during calling getPatientsConsent() from UserServiceImpl. Consent for one of procedures wasn't changed.");
			}
		}
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
		Patient patient;
		PersonalDataValidator validator = new PersonalDataValidator();
		if (!validator.validID(id)) {
			throw new ServiceException(
					"Error when calling validator.validID(id) from getPatientById from UserServiseImpl");
		}
		try {
			patient = userDao.findPatientById(id);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findPatientById(id) from method getPatientById(String id) from UserServiseImpl",
					ex);
		}
		if (patient == null) {
			throw new ServiceException(
					"When calling userDao.findPatientById(id) from getPatientById from UserServiseImpl - patient with shown id wasn't found");
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
			throw new ValidationServiceException(ValidationConstant.INVALID_EMAIL);
		}
		// adding of new patient. Generating of unique ID;
		String id = UUID.randomUUID().toString();
		LocalDate birthday = LocalDate.parse(dateBirth);
		Patient patient = new Patient(id, surname, name, birthday, email);
		patient.setId(id);
		int effectedRows = 0;
		try {
			effectedRows = userDao.createNewPatient(patient);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.createNewPatient(patient) from method addNewPatient(Patient patient)");
		}
		if (effectedRows == 0) {
			throw new ServiceException(
					"The patient wasn't added. EffectedRows, when calling userDao.createNewPatient(patient), == 0");
		}
		return id;
	}

	// Getting full sorted by disease names List of possible diagnosis from DB;
	public List<Diagnosis> getAllDiagnosisSorted() throws ServiceException {
		List<Diagnosis> diagnosisList;
		try {
			diagnosisList = userDao.readAllDiagnosis();
			if (diagnosisList.isEmpty()) {
				throw new ServiceException("Error when calling getAllDiagnosis() from UserServiceImpl. Empty List.");
			}
			Collections.sort(diagnosisList, Diagnosis.diseaseNameComparator);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.readAllDiagnosis() from method getAllDiagnosis().",
					ex);
		}
		return diagnosisList;
	}

	// This method adds the List of concrete patient's diagnosis;
	public void addPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws ServiceException {
		// Validation of diagnosis data;
		ValidationManager manager = new ValidationManager();
		for (PatientDiagnosis diagnosis : diagnosisList) {
			Set<String> invalidDataSet = manager.validatePatientDiagnosisData(diagnosis.getIdPatient(),
					diagnosis.getCodeByICD(), diagnosis.getSettingDate());
			if (!invalidDataSet.isEmpty()) {
				String invalidData = String.join(",", invalidDataSet);
				throw new ValidationServiceException(invalidData);
			}
		}
		// adding of the List of diagnosis;
		int[] effectedRows;
		try {
			effectedRows = userDao.createPatientDiagnosis(diagnosisList);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.createNewPatient(patient) from method addNewPatient(Patient patient)");
		}
		for (int i = 0; i < effectedRows.length; i++) {
			if (effectedRows[i] == 0) {
				throw new ServiceException(
						"Error during calling addPatientDiagnosis() from UserServiceImpl. One/all of diagnosis wasn't added.");
			}
		}
	}

	// this method adds diagnosis by IDC-10 to the list of all possible diagnosis;
	public void addNewDiagnosis(String codeDiagnosis, String diagnosisName, String numberBedDays)
			throws ServiceException {
		// Date validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateNewDiagnosisData(codeDiagnosis, diagnosisName, numberBedDays);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// adding of the new diagnosis;
		int bedDays = Integer.parseInt(numberBedDays);
		Diagnosis diagnosis = new Diagnosis(codeDiagnosis, diagnosisName, bedDays);
		int effectedRows = 0;
		try {
			effectedRows = userDao.createNewDiagnosis(diagnosis);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling userDao.createNewDiagnosis(diagnosis) from addNewDiagnosis() from UserServiceImpl new diagnosis wasn't added. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.createNewDiagnosis(diagnosis) from addNewDiagnosis() from UserServiceImpl",
					e);
		}
	}

	public void addNewTreatment(String idPatient, String treatmentType, String treatmentName, String idDoctor,
			String dateBegin, String dateFinish) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateTreatmentData(idPatient, treatmentType, treatmentName, idDoctor,
				dateBegin, dateFinish);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// adding of the new treatment;
		LocalDate dateBeginning = LocalDate.parse(dateBegin);
		LocalDate dateFinishing = LocalDate.parse(dateFinish);
		boolean consent = false;
		Treatment treatment = new Treatment(idPatient, treatmentType, treatmentName, idDoctor, dateBeginning,
				dateFinishing, consent);
		int effectedRows = 0;
		try {
			effectedRows = userDao.createPatientTreatment(treatment);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling userDao.createPatientTreatment(treatment) from addNewTreatment() from UserServiceImpl new treatment wasn't added. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.createPatientTreatment(treatment) from addNewTreatment() from UserServiceImpl",
					e);
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
			throw new ValidationServiceException(ValidationConstant.INVALID_EMAIL);
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

//	public static void main(String[] args) {
//		ConnectionPool connectionPool = null;
//		try {
//			connectionPool = new ConnectionPool();
//			connectionPool.initializePoolData();
//			DaoFactoryImpl.setConnectionPool(connectionPool);
//			
//			UserServiceImpl userService = new UserServiceImpl();
//			List<PatientDiagnosis> list = userService.getSortedPatientDiagnosisById("3d9c83ee-194e-4ebc-a80f-fe1487a237f9");
//			for(PatientDiagnosis di: list) {
//				System.out.println(di.toString());
//			}
//		} catch (ConnectionPoolException e) {
//			e.printStackTrace();
//
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		} finally {
//			connectionPool.dispose();
//		}
//
//	}

}
