package by.epam.ts.dal;

import java.time.LocalDate;
import java.util.List;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Treatment;

public interface TreatmentDao {
	
	List<Treatment> findPatientsTreatmentById(String id) throws DaoException;
	
	int updateConsent(int idAppointment, boolean consent) throws DaoException;
	
	int createPatientTreatment(Treatment treatment) throws DaoException;
	
	int createCurrentTreatment(CurrentTreatment treatment) throws DaoException;
	
	List<Treatment> findCurrentHospitalizationTreatment(String idPatient, LocalDate entryDate) throws DaoException;
	
	List<CurrentTreatment> findCurrentTreatmentByAppointmentId(int idAppointment) throws DaoException;

}
