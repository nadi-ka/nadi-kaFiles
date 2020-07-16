package by.epam.ts.service;

import java.util.List;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.service.exception.ServiceException;

public interface HospitalizationService {
	
	void addNewHospitalisation(String idPatient, String entryDate) throws ServiceException;

	void setDischargeDate(String dischargeDate, String entryDate, String idHistory) throws ServiceException;

	List<Hospitalization> getAllHospitalizationsById(String id) throws ServiceException;

	Hospitalization getLastHospitalizationById(String id) throws ServiceException;
	

}
