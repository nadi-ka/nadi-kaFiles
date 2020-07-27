package by.epam.ts.dal.pool.factory;

import by.epam.ts.dal.pool.ConnectionPool;

public final class ConnectionPoolFactory {

	private static final ConnectionPoolFactory instance = new ConnectionPoolFactory();
	private final ConnectionPool pool = new ConnectionPool();

	private ConnectionPoolFactory() {
	}

	public static ConnectionPoolFactory getInstance() {
		return instance;
	}

	public ConnectionPool getConnectionPool() {
		return pool;
	}

}
