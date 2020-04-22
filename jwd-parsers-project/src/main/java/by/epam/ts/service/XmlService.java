package by.epam.ts.service;

import java.util.List;

import by.epam.ts.bean.Patient;
import by.epam.ts.service.exception.ServiceException;

public interface XmlService {
	
	List<Patient> getPatientsListSAX(String xmlFileName) throws ServiceException;
	
	List<Patient> getPatientsListStaX(String xmlFileName) throws ServiceException;
	
	List<Patient> getPatientsListDOM(String xmlFileName) throws ServiceException;

}
