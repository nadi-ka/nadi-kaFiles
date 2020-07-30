package by.epam.ts.dal.pool.factory;

import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.impl.ConnectionPoolImpl;

public final class ConnectionPoolFactory {

	private static final ConnectionPoolFactory instance = new ConnectionPoolFactory();
	private final ConnectionPool pool = new ConnectionPoolImpl();

	private ConnectionPoolFactory() {
	}

	public static ConnectionPoolFactory getInstance() {
		return instance;
	}

	public ConnectionPool getConnectionPool() {
		return pool;
	}

}
