package by.epam.ts.dal.factory;

import by.epam.ts.dal.DiagnosisDao;
import by.epam.ts.dal.HospitalizationDao;
import by.epam.ts.dal.TreatmentDao;
import by.epam.ts.dal.UserDao;

public interface DaoFactory {
	
	UserDao getUserDao();
	
	TreatmentDao getTreatmentDao();
	
	DiagnosisDao getDiagnosisDao();
	
	HospitalizationDao getHospitalizationDao();

}
