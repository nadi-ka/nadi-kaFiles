package by.epam.ts.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.DiagnosisDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.validator.ValidationManager;

public class DiagnosisServiceImpl implements DiagnosisService {

	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private DiagnosisDao diagnosisDao = daoFactory.getDiagnosisDao();
	private static final String delimeter = ",";

	static final Logger log = LogManager.getLogger(DiagnosisServiceImpl.class);

	public List<PatientDiagnosis> getSortedPatientDiagnosisById(String id) throws ServiceException {
		List<PatientDiagnosis> diagnosisList;
		try {
			diagnosisList = diagnosisDao.findPatientsDiagnosisById(id);
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
			String invalidData = String.join(delimeter, invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		LocalDate date = LocalDate.parse(entryDate);
		List<PatientDiagnosis> diagnosisList;
		try {
			diagnosisList = diagnosisDao.findCurrentDiagnosisById(id, date);
			Collections.sort(diagnosisList, PatientDiagnosis.primaryDiagnosisComparator);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findPatientsDiagnosisById(id)", ex);
		}
		return diagnosisList;
	}

	// Get full sorted (by disease names) List of possible diagnosis from DB;
	public List<Diagnosis> getAllDiagnosisSorted() throws ServiceException {
		List<Diagnosis> diagnosisList;
		try {
			diagnosisList = diagnosisDao.readAllDiagnosis();
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

	// Add the List of concrete patient's diagnosis;
	public void addPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws ServiceException {
		// Validation of diagnosis data;
		ValidationManager manager = new ValidationManager();
		for (PatientDiagnosis diagnosis : diagnosisList) {
			Set<String> invalidDataSet = manager.validatePatientDiagnosisData(diagnosis.getIdPatient(),
					diagnosis.getCodeByICD(), diagnosis.getSettingDate());
			if (!invalidDataSet.isEmpty()) {
				String invalidData = String.join(delimeter, invalidDataSet);
				throw new ValidationServiceException(invalidData);
			}
		}
		// adding of the List of diagnosis;
		int[] effectedRows;
		try {
			effectedRows = diagnosisDao.createPatientDiagnosis(diagnosisList);
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

	/*
	 * Add new diagnosis by IDC-10 to the list of all possible ophthalmological
	 * diagnosis;
	 */
	public void addNewDiagnosis(String codeDiagnosis, String diagnosisName, String numberBedDays)
			throws ServiceException {
		// Date validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateNewDiagnosisData(codeDiagnosis, diagnosisName, numberBedDays);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(delimeter, invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// adding of the new diagnosis;
		int bedDays = Integer.parseInt(numberBedDays);
		Diagnosis diagnosis = new Diagnosis(codeDiagnosis, diagnosisName, bedDays);
		int effectedRows = 0;
		try {
			effectedRows = diagnosisDao.createNewDiagnosis(diagnosis);
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

	public int getAverageHospitalizationLength(String id, LocalDate hospitalizationDate) throws ServiceException {
		List<Diagnosis> diagnosisList;
		int numberBedDays = 0;
		try {
			diagnosisList = diagnosisDao.findShortDiagnosisByIdAndDate(id, hospitalizationDate);
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

}
