package by.epam.ts.dal.factory.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.factory.DaoFactory;
import by.epam.ts.dal.impl.UserDaoSQL;
import by.epam.ts.dal.pool.ConnectionPool;

public final class DaoFactoryImpl implements DaoFactory {
	
	private final static DaoFactoryImpl instance = new DaoFactoryImpl();
	private static ConnectionPool connectionPool;
	private final UserDao userDao = null;
	
	static final Logger log = LogManager.getLogger( DaoFactoryImpl.class);

	private DaoFactoryImpl() {
	}
	
	public static void setConnectionPool(ConnectionPool pool) {
		connectionPool = pool;
		log.info("setConnectionPool(pool) from DaoFactoryImpl.");
	}

	public static DaoFactoryImpl getInstance() {
		return instance;
	}

	public UserDao getUserDao() {
		return new UserDaoSQL(connectionPool);
	}
}
