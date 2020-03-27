package by.epam.ts.dal.factory.impl;

import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.impl.UserDaoSQL;
import by.epam.ts.dal.pool.ConnectionPool;

public final class DaoFactoryImpl implements DaoFactory {
	
	private final static DaoFactoryImpl instance = new DaoFactoryImpl();
	private static ConnectionPool connectionPool;
	private final UserDao userDao = new UserDaoSQL(connectionPool);

	private DaoFactoryImpl() {
	}
	
	public static void setConnectionPool(ConnectionPool pool) {
		connectionPool = pool;
	}

	public static DaoFactoryImpl getInstance() {
		return instance;
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
