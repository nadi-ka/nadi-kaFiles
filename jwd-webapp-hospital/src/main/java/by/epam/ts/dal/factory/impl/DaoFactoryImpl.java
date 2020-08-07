package by.epam.ts.dal.factory.impl;

import by.epam.ts.dal.DiagnosisDao;
import by.epam.ts.dal.HospitalizationDao;
import by.epam.ts.dal.TreatmentDao;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.impl.DiagnosisDaoSql;
import by.epam.ts.dal.impl.HospitalizationDaoSql;
import by.epam.ts.dal.impl.TreatmentDaoSql;
import by.epam.ts.dal.impl.UserDaoSQL;

public final class DaoFactoryImpl implements DaoFactory {
	
	private final static DaoFactory instance = new DaoFactoryImpl();
	
	private final UserDao userDao = new UserDaoSQL();
	private final TreatmentDao treatmentDao = new TreatmentDaoSql();
	private final DiagnosisDao diagnosisDao = new DiagnosisDaoSql();
	private final HospitalizationDao hospitalizationDao = new HospitalizationDaoSql();
	
	public static DaoFactory getInstance() {
		return instance;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public TreatmentDao getTreatmentDao() {
		return treatmentDao;
	}
	
	public DiagnosisDao getDiagnosisDao() {
		return diagnosisDao;
	}
	
	public HospitalizationDao getHospitalizationDao() {
		return hospitalizationDao;
	}
	
}
