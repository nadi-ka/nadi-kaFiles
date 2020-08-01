package by.epam.ts.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.jdbc.ScriptRunner;

import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;

public class DbScriptRunner {

	private final static String SCRIPT_PATH = "src\\main\\resources\\hospital_test.sql";

	/*
	 * Utility method which delete and then create again the test DB; Used in tests
	 * with INSERT statement;
	 */
	public static void dropAndRestoreTestDB(ConnectionPool moskedConnectionPool)
			throws FileNotFoundException, IOException, ConnectionPoolException {

		Connection connection = null;
		try (Reader reader = new BufferedReader(new FileReader(SCRIPT_PATH))) {
			connection = moskedConnectionPool.takeConnection();
			ScriptRunner scriptRunner = new ScriptRunner(connection);
			scriptRunner.runScript(reader);
		} finally {
			moskedConnectionPool.releaseConnection(connection);
		}
	}

}
