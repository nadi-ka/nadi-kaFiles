package by.epam.ts.dal.pool.factory;

import by.epam.ts.dal.pool.TestConnectionPool;

public final class TestConnectionPoolFactory {
	
	private static final TestConnectionPoolFactory instance = new TestConnectionPoolFactory();
	private final TestConnectionPool pool = new TestConnectionPool();

	private TestConnectionPoolFactory() {
	}

	public static TestConnectionPoolFactory getInstance() {
		return instance;
	}

	public TestConnectionPool getConnectionPool() {
		return pool;
	}


}
