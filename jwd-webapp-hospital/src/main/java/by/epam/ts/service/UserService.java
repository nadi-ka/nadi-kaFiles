package by.epam.ts.service;

import java.util.List;
import java.util.Map;

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.service.exception.ServiceException;

public interface UserService {

	int signUp(String email, String login, String password) throws ServiceException;

	User logIn(String login, String password) throws ServiceException;

	List<Treatment> getPatientsTreatmentById(String id) throws ServiceException;

	List<PatientDiagnosis> getSortedPatientDiagnosisById(String id) throws ServiceException;

	void getPatientsConsent(Map<Integer, Boolean> consentMap) throws ServiceException;

	List<Patient> getPatientBySurname(String surname) throws ServiceException;

	Patient getPatientById(String id) throws ServiceException;

	String addNewPatient(String surname, String name, String dateBirth, String email) throws ServiceException;

	List<Diagnosis> getAllDiagnosisSorted() throws ServiceException;

	void addPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws ServiceException;

	void addNewDiagnosis(String codeDiagnosis, String diagnosisName, String numberBedDays) throws ServiceException;

	void addNewTreatment(String idPatient, String treatmentType, String treatmentName, String idDoctor,
			String dateBegin, String dateFinish) throws ServiceException;
	
	String addNewStaff(String specialty, String surname, String name, String email) throws ServiceException;

}
