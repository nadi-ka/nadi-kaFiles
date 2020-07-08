package by.epam.ts.dal;

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

public interface UserDao {

	int createUserPatient(User user) throws DaoException;

	int createUserStaff(User user) throws DaoException;

	MedicalStaff findStaffByEmail(String email) throws DaoException;

	Patient findPatientByEmail(String email) throws DaoException;

	User findUserByLogin(String login) throws DaoException;

	List<Treatment> findPatientsTreatmentById(String id) throws DaoException;

	List<PatientDiagnosis> findPatientsDiagnosisById(String id) throws DaoException;

	List<PatientDiagnosis> findCurrentDiagnosisById(String id, LocalDate entryDate) throws DaoException;

	String findLogin(String login) throws DaoException;

	int updateConsent(int idAppointment, boolean consent) throws DaoException;

	int createNewPatient(Patient patient) throws DaoException;

	int updatePatientPersonalData(Patient patient) throws DaoException;

	List<Patient> findPatientBySurname(String surname) throws DaoException;

	Patient findPatientById(String id) throws DaoException;

	List<Diagnosis> readAllDiagnosis() throws DaoException;

	int createNewDiagnosis(Diagnosis diagnosis) throws DaoException;

	int[] createPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws DaoException;

	int createPatientTreatment(Treatment treatment) throws DaoException;

	int createNewStaff(MedicalStaff medicalStaff) throws DaoException;

	int updateStaffPersonalData(String surname, String name, String email, String id) throws DaoException;

	MedicalStaff findStaffById(String id) throws DaoException;

	List<MedicalStaff> findUserStaffBySurname(String surname) throws DaoException;

	int createNewHospitalization(Hospitalization hospitalization) throws DaoException;

	int updateDischargeDate(LocalDate dischargeDate, int idHystory) throws DaoException;

	List<Hospitalization> findHospitalizationsById(String id) throws DaoException;

	Hospitalization findLastHospitalizationById(String id) throws DaoException;

	int createCurrentTreatment(CurrentTreatment treatment) throws DaoException;

	List<Treatment> findCurrentHospitalizationTreatment(String idPatient, LocalDate entryDate) throws DaoException;

	List<CurrentTreatment> findCurrentTreatmentByAppointmentId(int idAppointment) throws DaoException;

	List<Diagnosis> findDiagnosisByIdAndDate(String id, LocalDate hospitalizationDate) throws DaoException;

	int updateStaffUserRole(int newRole, String id) throws DaoException;

	int updateUserStatus(boolean newStatus, String id) throws DaoException;

}
