package by.epam.ts.dal;

import java.time.LocalDate;
import java.util.List;

import by.epam.ts.bean.Hospitalization;

public interface HospitalizationDao {

	int createNewHospitalization(Hospitalization hospitalization) throws DaoException;

	int updateDischargeDate(LocalDate dischargeDate, int idHystory) throws DaoException;

	List<Hospitalization> findHospitalizationsById(String id) throws DaoException;

	Hospitalization findLastHospitalizationById(String id) throws DaoException;

}
