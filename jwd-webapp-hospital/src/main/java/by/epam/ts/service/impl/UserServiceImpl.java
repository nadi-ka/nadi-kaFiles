package by.epam.ts.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.Hospitalization;
import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.bean.role.UserRole;
import by.epam.ts.bean.specialty.Specialty;
import by.epam.ts.bean.treat_status.TreatmentStatus;
import by.epam.ts.bean.treat_type.TreatmentType;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.service.UserService;
import by.epam.ts.service.constant.ValidationConstant;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.util.StaffUserRoleQualifier;
import by.epam.ts.service.validator.ValidationManager;

public class UserServiceImpl implements UserService {
	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private UserDao userDao = daoFactory.getUserDao();

	static final Logger log = LogManager.getLogger(UserServiceImpl.class);

	public int signUp(String email, String login, String password) throws ServiceException {
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
			// search the person in the table patient;
			Patient patient = userDao.findPatientByEmail(email);
			User user = null;
			boolean userStatus = true;
			int updatedRows = 0;
			if (patient != null) {
				user = new User(patient.getId(), login, password, UserRole.PATIENT, userStatus);
				updatedRows = userDao.createUserPatient(user);
				return updatedRows;
			}
			// search the person in the table medical-staff;
			MedicalStaff staff = userDao.findStaffByEmail(email);
			if (staff != null) {
				StaffUserRoleQualifier qualifier = new StaffUserRoleQualifier();
				UserRole staffRole = qualifier.qualifyStaffUserRole(staff);
				user = new User(staff.getId(), login, password, staffRole, userStatus);
				updatedRows = userDao.createUserStaff(user);
				return updatedRows;
			}
			// the person wasn't found in any table;
			throw new ValidationServiceException(ValidationConstant.NOT_UNIQUE_EMAIL);

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
			user = userDao.findUserByLoginPassword(login, password);
			if ((user != null) && !user.isUserStatus()) {
				throw new ValidationServiceException(ValidationConstant.INVALID_ACCOUNT);
			}
		} catch (DaoException ex) {
			throw new ServiceException("Error during reading from DB.", ex);
		}
		return user;
	}

	public List<Treatment> getPatientTreatmentById(String id) throws ServiceException {
		List<Treatment> prescriptions;
		try {
			prescriptions = userDao.findPatientsTreatmentById(id);
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

	public List<PatientDiagnosis> getCurrentDiagnosisSorted(String id, String entryDate) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateHospitalizationData(id, entryDate);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		LocalDate date = LocalDate.parse(entryDate);
		List<PatientDiagnosis> diagnosisList;
		try {
			diagnosisList = userDao.findCurrentDiagnosisById(id, date);
			Collections.sort(diagnosisList, PatientDiagnosis.primaryDiagnosisComparator);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findPatientsDiagnosisById(id)", ex);
		}
		return diagnosisList;
	}

	public void getPatientsConsent(String idAppointment, String consent) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateConsent(idAppointment, consent);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		int numAppointment = Integer.parseInt(idAppointment);
		boolean consentBool = Boolean.parseBoolean(consent);
		int updatedRows = 0;
		try {
			updatedRows = userDao.updateConsent(numAppointment, consentBool);
			// check if consent was updated successfully;
			if (updatedRows == 0) {
				throw new ServiceException(
						"Error when calling getPatientsConsent() from UserServiceImpl. The consent wasn't updated.");
			}
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.updateConsent(). Consent wasn't updated.", ex);
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
			throw new ValidationServiceException(ValidationConstant.NOT_UNIQUE_EMAIL);
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
				throw new ValidationServiceException(ValidationConstant.NOT_UNIQUE_EMAIL);
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
		Treatment treatment = new Treatment(idPatient, TreatmentType.getTreatmentType(treatmentType), treatmentName,
				idDoctor, dateBeginning, dateFinishing, consent);
		log.info(treatment.toString());
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
			log.info(other.toString());
			throw new ValidationServiceException(ValidationConstant.NOT_UNIQUE_EMAIL);
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
				throw new ValidationServiceException(ValidationConstant.NOT_UNIQUE_EMAIL);
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

	public void addNewHospitalisation(String idPatient, String entryDate) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateHospitalizationData(idPatient, entryDate);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// adding of the new hospitalization;
		LocalDate date = LocalDate.parse(entryDate);
		Hospitalization hospitalization = new Hospitalization(idPatient, date);
		int effectedRows = 0;
		try {
			effectedRows = userDao.createNewHospitalization(hospitalization);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling createNewHospitalization() from addNewHospitalisation() from UserServiceImpl new hospitalization wasn't added. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.createNewHospitalization() from addNewHospitalisation() from UserServiceImpl",
					e);
		}
	}

	public void setDischargeDate(String dischargeDate, String entryDate, String idHistory) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateDischargeData(dischargeDate, entryDate, idHistory);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// setting of the discharge date;
		LocalDate dateFinishing = LocalDate.parse(dischargeDate);
		LocalDate dateBeginning = LocalDate.parse(entryDate);
		// check that dischargeDate follows entryDate, not vice versa;
		if (dateFinishing.isBefore(dateBeginning)) {
			throw new ValidationServiceException(ValidationConstant.INVALID_DISCHARGE_DATE);
		}
		int historyNum = Integer.parseInt(idHistory);
		int effectedRows = 0;
		try {
			effectedRows = userDao.updateDischargeDate(dateFinishing, historyNum);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling userDao.updateDischargeDate() from setDischargeDate() from UserServiceImpl discharge date wasn't set. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.updateDischargeDate() from setDischargeDate() from UserServiceImpl", e);
		}
	}

	public List<Hospitalization> getAllHospitalizationsById(String id) throws ServiceException {
		List<Hospitalization> hospitalizations;
		try {
			hospitalizations = userDao.findHospitalizationsById(id);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findHospitalizationsById(id).", ex);
		}
		return hospitalizations;
	}

	public Hospitalization getLastHospitalizationById(String id) throws ServiceException {
		Hospitalization hospitalization = null;
		try {
			hospitalization = userDao.findLastHospitalizationById(id);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findLastHospitalizationById(id) from getLastHospitalizationById().",
					ex);
		}
		return hospitalization;
	}

	public void performCurrentTreatment(String consent, String idAppointment, String datePerforming, String idPerformer,
			String status) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateCurrentTreatmentData(consent, idAppointment, datePerforming,
				idPerformer, status);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// check consent;
		boolean isConsent = Boolean.parseBoolean(consent);
		if (!isConsent) {
			throw new ServiceException(
					"Error when calling performCurrentTreatment() from UserServiceImpl. Procedure is prohibited, the patient hasn't given his consent.");
		}
		// perform the treatment;
		int numAppointment = Integer.parseInt(idAppointment);
		LocalDate date = LocalDate.parse(datePerforming);
		TreatmentStatus treatmentStatus = TreatmentStatus.getTreatmentStatus(status);
		CurrentTreatment treatment = new CurrentTreatment(numAppointment, date, idPerformer, treatmentStatus);
		int effectedRows = 0;
		try {
			effectedRows = userDao.createCurrentTreatment(treatment);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling userDao.createCurrentTreatment(treatment) from performeCurrentTreatment() from UserServiceImpl the treatment wasn't performed. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.createCurrentTreatment(treatment) from performeCurrentTreatment() from UserServiceImpl",
					e);
		}
	}

	public void cancelTreatment(String idAppointment, String idDoctor) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateTreatCancellationData(idAppointment, idDoctor);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		int numAppointment = Integer.parseInt(idAppointment);
		LocalDate now = LocalDate.now();
		TreatmentStatus status = TreatmentStatus.CANCELED;

		CurrentTreatment treatment = new CurrentTreatment(numAppointment, now, idDoctor, status);
		int effectedRows = 0;
		try {
			effectedRows = userDao.createCurrentTreatment(treatment);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling createCurrentTreatment(treatment) from cancelTreatment() from UserServiceImpl, the treatment wasn't canceled. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when callingcreateCurrentTreatment(treatment) from cancelTreatment() from UserServiceImpl",
					e);
		}
	}

	public List<Treatment> getTreatmentDuringLastHospitalization(String idPatient, LocalDate entryDate)
			throws ServiceException {
		List<Treatment> prescriptions;
		try {
			prescriptions = userDao.findCurrentHospitalizationTreatment(idPatient, entryDate);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findPatientsDiagnosisById(id)", ex);
		}
		return prescriptions;
	}

	public List<CurrentTreatment> getCurrentTreatmentByAppointmentId(int idAppointment) throws ServiceException {
		List<CurrentTreatment> performingList;
		try {
			performingList = userDao.findCurrentTreatmentByAppointmentId(idAppointment);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findCurrentTreatmentByAppointmentId(id) from getCurrentTreatmentByAppointmentIdSorted from UserServiceImpl.",
					ex);
		}
		return performingList;
	}

	public int getAverageHospitalizationLength(String id, LocalDate hospitalizationDate) throws ServiceException {
		List<Diagnosis> diagnosisList;
		int numberBedDays = 0;
		try {
			diagnosisList = userDao.findDiagnosisByIdAndDate(id, hospitalizationDate);
			if (diagnosisList.isEmpty()) {
				return numberBedDays;
			} else {
				for (Diagnosis item : diagnosisList) {
					if (numberBedDays < item.getAverageBedDays()) {
						numberBedDays = item.getAverageBedDays();
					}
				}
			}
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling getPrimaryDiagnosis() from UserServiceImpl.", ex);
		}
		return numberBedDays;
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
