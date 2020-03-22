package by.epam.ts.dal.daoFactory.daoFactoryImpl;

import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.daoFactory.DaoFactory;
import by.epam.ts.dal.daoImpl.UserDaoSQL;

public final class DaoFactoryImpl implements DaoFactory {
	private static DaoFactoryImpl instance;
	
	private final UserDao userDao;
	
	private DaoFactoryImpl() throws DaoException{
		try {
		userDao = new UserDaoSQL();
		}catch(DaoException ex) {
			throw new DaoException();
		}
	}
	
	public static DaoFactory getInstance() throws DaoException{
		if (instance == null) {
			try {
			instance = new DaoFactoryImpl();
			}catch(DaoException ex) {
				throw new DaoException("Instance of DaoFactoryImpl wasn't created.", ex);
			}
		}
		return instance;
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
