package by.epam.ts.service;

import java.time.LocalDate;
import java.util.List;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Treatment;
import by.epam.ts.service.exception.ServiceException;

public interface TreatmentService {

	List<Treatment> getPatientTreatmentById(String id) throws ServiceException;

	void getPatientsConsent(String idAppointment, String consent) throws ServiceException;

	void addNewTreatment(String idPatient, String treatmentType, String treatmentName, String idDoctor,
			String dateBegin, String dateFinish) throws ServiceException;

	void performCurrentTreatment(String consent, String idAppointment, String datePerforming, String idPerformer,
			String status) throws ServiceException;

	void cancelTreatment(String idAppointment, String idDoctor) throws ServiceException;

	List<Treatment> getTreatmentDuringLastHospitalization(String idPatient, LocalDate entryDate)
			throws ServiceException;

	List<CurrentTreatment> getCurrentTreatmentByAppointmentId(int idAppointment) throws ServiceException;

}
