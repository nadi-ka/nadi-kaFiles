package src.main.java.by.epam.ts.dal.daoFactory.daoFactoryImpl;

import src.main.java.by.epam.ts.dal.DaoException;
import src.main.java.by.epam.ts.dal.UserDao;
import src.main.java.by.epam.ts.dal.connectionPool.ConnectionPool;
import src.main.java.by.epam.ts.dal.daoFactory.DaoFactory;
import src.main.java.by.epam.ts.dal.daoImpl.UserDaoSQL;

public final class DaoFactoryImpl implements DaoFactory {
	private static DaoFactoryImpl instance;
	private final ConnectionPool connectionPool = new ConnectionPool();
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
