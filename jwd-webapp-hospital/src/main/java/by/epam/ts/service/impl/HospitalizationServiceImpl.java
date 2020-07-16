package by.epam.ts.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.HospitalizationDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.constant.ValidationConstant;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.validator.ValidationManager;

public class HospitalizationServiceImpl implements HospitalizationService {

	private DaoFactory daoFactory = DaoFactoryImpl.getInstance();
	private HospitalizationDao hospitalizationDao = daoFactory.getHospitalizationDao();

	static final Logger log = LogManager.getLogger(HospitalizationServiceImpl.class);

	public void addNewHospitalisation(String idPatient, String entryDate) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateHospitalizationData(idPatient, entryDate);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// adding of the new hospitalization;
		LocalDate date = LocalDate.parse(entryDate);
		Hospitalization hospitalization = new Hospitalization(idPatient, date);
		int effectedRows = 0;
		try {
			effectedRows = hospitalizationDao.createNewHospitalization(hospitalization);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling createNewHospitalization() from addNewHospitalisation() from UserServiceImpl new hospitalization wasn't added. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.createNewHospitalization() from addNewHospitalisation() from UserServiceImpl",
					e);
		}
	}

	public void setDischargeDate(String dischargeDate, String entryDate, String idHistory) throws ServiceException {
		// Data validation;
		ValidationManager manager = new ValidationManager();
		Set<String> invalidDataSet = manager.validateDischargeData(dischargeDate, entryDate, idHistory);
		if (!invalidDataSet.isEmpty()) {
			String invalidData = String.join(",", invalidDataSet);
			throw new ValidationServiceException(invalidData);
		}
		// setting of the discharge date;
		LocalDate dateFinishing = LocalDate.parse(dischargeDate);
		LocalDate dateBeginning = LocalDate.parse(entryDate);
		// check that dischargeDate follows entryDate, not vice versa;
		if (dateFinishing.isBefore(dateBeginning)) {
			throw new ValidationServiceException(ValidationConstant.INVALID_DISCHARGE_DATE);
		}
		int historyNum = Integer.parseInt(idHistory);
		int effectedRows = 0;
		try {
			effectedRows = hospitalizationDao.updateDischargeDate(dateFinishing, historyNum);
			if (effectedRows == 0) {
				throw new ServiceException(
						"When calling userDao.updateDischargeDate() from setDischargeDate() from UserServiceImpl discharge date wasn't set. Effected rows == 0");
			}
		} catch (DaoException e) {
			throw new ServiceException(
					"Error when calling userDao.updateDischargeDate() from setDischargeDate() from UserServiceImpl", e);
		}
	}

	public List<Hospitalization> getAllHospitalizationsById(String id) throws ServiceException {
		List<Hospitalization> hospitalizations;
		try {
			hospitalizations = hospitalizationDao.findHospitalizationsById(id);
		} catch (DaoException ex) {
			throw new ServiceException("Error when calling userDao.findHospitalizationsById(id).", ex);
		}
		return hospitalizations;
	}

	public Hospitalization getLastHospitalizationById(String id) throws ServiceException {
		Hospitalization hospitalization = null;
		try {
			hospitalization = hospitalizationDao.findLastHospitalizationById(id);
		} catch (DaoException ex) {
			throw new ServiceException(
					"Error when calling userDao.findLastHospitalizationById(id) from getLastHospitalizationById().",
					ex);
		}
		return hospitalization;
	}

}
