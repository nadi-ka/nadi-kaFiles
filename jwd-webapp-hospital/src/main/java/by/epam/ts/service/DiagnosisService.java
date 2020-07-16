package by.epam.ts.service;

import java.time.LocalDate;
import java.util.List;

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.service.exception.ServiceException;

public interface DiagnosisService {
	
	List<PatientDiagnosis> getSortedPatientDiagnosisById(String id) throws ServiceException;

	List<PatientDiagnosis> getCurrentDiagnosisSorted(String id, String entryDate) throws ServiceException;
	
	List<Diagnosis> getAllDiagnosisSorted() throws ServiceException;

	void addPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws ServiceException;

	void addNewDiagnosis(String codeDiagnosis, String diagnosisName, String numberBedDays) throws ServiceException;
	
	int getAverageHospitalizationLength(String id, LocalDate hospitalizationDate) throws ServiceException;

}
