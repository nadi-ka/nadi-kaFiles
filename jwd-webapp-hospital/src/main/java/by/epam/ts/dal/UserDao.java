package by.epam.ts.dal;

import java.util.List;
import java.util.Map;

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;

public interface UserDao {
	
	int createUserPatient(User user) throws DaoException;
	
	int createUserStaff (User user) throws DaoException;
	
	MedicalStaff findStaffByEmail(String email) throws DaoException;
	
	Patient findPatientByEmail(String email) throws DaoException;
	
	User findUserByLoginPassword(String login, String password) 
			throws DaoException;
	
	List<Treatment> findPatientsTreatmentById(String id) throws DaoException;
	
	List<PatientDiagnosis> findPatientsDiagnosisById(String id) throws DaoException;
	
	String findLogin(String login) throws DaoException;
	
	int[] updateConsent(Map<Integer, Boolean> consentMap) throws DaoException;
	
	int createNewPatient(Patient patient) throws DaoException;
	
	List<Patient> findPatientBySurname(String surname) throws DaoException;
	
	Patient findPatientById(String id) throws DaoException;
	
	List<Diagnosis> readAllDiagnosis() throws DaoException;
	
	int createNewDiagnosis(Diagnosis diagnosis) throws DaoException;
	
	int[] createPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws DaoException;
	
	int createPatientTreatment(Treatment treatment) throws DaoException;
	
	int createNewStaff(MedicalStaff medicalStaff) throws DaoException;
	
	
	
	
	
}
