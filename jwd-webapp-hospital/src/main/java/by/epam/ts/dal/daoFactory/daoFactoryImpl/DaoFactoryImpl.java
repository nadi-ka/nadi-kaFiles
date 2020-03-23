package by.epam.ts.dal.daoFactory.daoFactoryImpl;

import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.connectionPool.ConnectionPool;
import by.epam.ts.dal.daoFactory.DaoFactory;
import by.epam.ts.dal.daoImpl.UserDaoSQL;

public final class DaoFactoryImpl implements DaoFactory {
	
	private final UserDao userDao;
	
	public DaoFactoryImpl(ConnectionPool connectionPool){
		userDao = new UserDaoSQL(connectionPool);
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
