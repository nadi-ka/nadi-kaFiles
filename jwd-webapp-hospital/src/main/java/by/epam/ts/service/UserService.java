package by.epam.ts.service;

import java.time.LocalDate;
import java.util.List;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.Hospitalization;
import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.service.exception.ServiceException;

public interface UserService {

	int signUp(String email, String login, String password) throws ServiceException;

	User logIn(String login, String password) throws ServiceException;

	List<Treatment> getSortedPatientsTreatmentById(String id) throws ServiceException;

	List<PatientDiagnosis> getSortedPatientDiagnosisById(String id) throws ServiceException;

	List<PatientDiagnosis> getCurrentDiagnosisSorted(String id, String entryDate) throws ServiceException;

	void getPatientsConsent(String idAppointment, String consent) throws ServiceException;

	List<Patient> getPatientBySurname(String surname) throws ServiceException;

	Patient getPatientById(String id) throws ServiceException;

	String addNewPatient(String surname, String name, String dateBirth, String email) throws ServiceException;

	List<Diagnosis> getAllDiagnosisSorted() throws ServiceException;

	void addPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws ServiceException;

	void addNewDiagnosis(String codeDiagnosis, String diagnosisName, String numberBedDays) throws ServiceException;

	void addNewTreatment(String idPatient, String treatmentType, String treatmentName, String idDoctor,
			String dateBegin, String dateFinish) throws ServiceException;

	String addNewStaff(String specialty, String surname, String name, String email) throws ServiceException;

	MedicalStaff getStaffById(String id) throws ServiceException;

	void addNewHospitalisation(String idPatient, String entryDate) throws ServiceException;

	void setDischargeDate(String dischargeDate, String entryDate, String idHistory) throws ServiceException;

	List<Hospitalization> getAllHospitalizationsById(String id) throws ServiceException;

	Hospitalization getLastHospitalizationById(String id) throws ServiceException;

	void performCurrentTreatment(String consent, String idAppointment, String datePerforming, String idPerformer, String status)
			throws ServiceException;

	List<Treatment> getTreatmentDuringLastHospitalization(String idPatient, LocalDate entryDate)
			throws ServiceException;

	List<CurrentTreatment> getCurrentTreatmentByAppointmentId(int idAppointment) throws ServiceException;
	
	int getAverageHospitalizationLength(String id, LocalDate hospitalizationDate) throws ServiceException;

}
