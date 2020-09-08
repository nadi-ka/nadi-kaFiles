package by.epam.ts.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.treat_status.TreatmentStatus;
import by.epam.ts.bean.treat_type.TreatmentType;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.TreatmentDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.validator.ValidationManager;

public class TreatmentServiceImpl implements TreatmentService {

	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private TreatmentDao treatmentDao = daoFactory.getTreatmentDao();
	private static final String delimeter = ",";

	static final Logger log = LogManager.getLogger(TreatmentServiceImpl.class);

	public List<Treatment> getPatientTreatmentById(String id) throws ServiceException {
		List<Treatment> prescriptions;
		try {
			prescriptions = treatmentDao.findPatientsTreatmentById(id);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findPatientsTreatmentById(id).", ex);
		}
		return prescriptions;
	}

	public void getPatientsConsent(String idAppointment, String consent) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateConsent(idAppointment, consent);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(delimeter, invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		int numAppointment = Integer.parseInt(idAppointment);
		boolean consentBool = Boolean.parseBoolean(consent);
		int updatedRows = 0;
		try {
			updatedRows = treatmentDao.updateConsent(numAppointment, consentBool);
			// check if consent was updated successfully;
			if (updatedRows == 0) {
				throw new ServiceException(
						"Error when calling getPatientsConsent() from UserServiceImpl. The consent wasn't updated.");
			}
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.updateConsent(). Consent wasn't updated.", ex);
		}
	}

	public void addNewTreatment(String idPatient, String treatmentType, String treatmentName, String idDoctor,
			String dateBegin, String dateFinish) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateTreatmentData(idPatient, treatmentType, treatmentName, idDoctor,
				dateBegin, dateFinish);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(delimeter, invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// adding of the new treatment;
		LocalDate dateBeginning = LocalDate.parse(dateBegin);
		LocalDate dateFinishing = LocalDate.parse(dateFinish);
		boolean consent = false;
		Treatment treatment = new Treatment(idPatient, TreatmentType.getTreatmentType(treatmentType), treatmentName,
				idDoctor, dateBeginning, dateFinishing, consent);

		int effectedRows = 0;
		try {
			effectedRows = treatmentDao.createPatientTreatment(treatment);
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

	public void performCurrentTreatment(String consent, String idAppointment, String datePerforming, String idPerformer,
			String status) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateCurrentTreatmentData(consent, idAppointment, datePerforming,
				idPerformer, status);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(delimeter, invalidDataSet);
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
			effectedRows = treatmentDao.createCurrentTreatment(treatment);
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
			String invalidData = String.join(delimeter, invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		int numAppointment = Integer.parseInt(idAppointment);
		LocalDate now = LocalDate.now();
		TreatmentStatus status = TreatmentStatus.CANCELED;

		CurrentTreatment treatment = new CurrentTreatment(numAppointment, now, idDoctor, status);
		int effectedRows = 0;
		try {
			effectedRows = treatmentDao.createCurrentTreatment(treatment);
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
			prescriptions = treatmentDao.findCurrentHospitalizationTreatment(idPatient, entryDate);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findPatientsDiagnosisById(id)", ex);
		}
		return prescriptions;
	}

	public List<CurrentTreatment> getCurrentTreatmentByAppointmentId(int idAppointment) throws ServiceException {
		List<CurrentTreatment> performingList;
		try {
			performingList = treatmentDao.findCurrentTreatmentByAppointmentId(idAppointment);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findCurrentTreatmentByAppointmentId(id) from getCurrentTreatmentByAppointmentIdSorted from UserServiceImpl.",
					ex);
		}
		return performingList;
	}

}
