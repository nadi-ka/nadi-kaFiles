package by.epam.ts.dal.impl;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.DiagnosisDao;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.TestConnectionPool;
import by.epam.ts.dal.pool.factory.TestConnectionPoolFactory;

/**
 * Test cases for the class DiagnosisDaoSql; The test database 'HospitalTest' is
 * used;
 */
public class DiagnosisDaoSqlTest {

	private final static String SCRIPT_PATH = "src\\test\\resources\\hospital_test.sql";
	private DiagnosisDao diagnosisDao= new DiagnosisDaoSql();
	private Connection connection;
	
	private final static String ID_ABSENT = "111";

	private static final Logger log = LogManager.getLogger(DiagnosisDaoSqlTest.class);

	@BeforeClass
	public static void initTestConnectionPool() {
		TestConnectionPoolFactory factory = TestConnectionPoolFactory.getInstance();
		TestConnectionPool pool = factory.getConnectionPool();
		try {
			pool.initializePoolData();
		} catch (ConnectionPoolException e) {
			log.log(Level.ERROR, "TestConnectionPool cannot be initialized", e);
		}
	}

	@AfterClass
	public static void destroyTestConnectionPool() {
		TestConnectionPoolFactory factory = TestConnectionPoolFactory.getInstance();
		TestConnectionPool pool = factory.getConnectionPool();
		pool.dispose();
	}

	/*
	 * Test DB will be first destroyed if exists and then created again and filled
	 * with the test data;
	 */
	@Before
	public void createDataBase() {
		try (Reader reader = new BufferedReader(new FileReader(SCRIPT_PATH))) {
			TestConnectionPoolFactory factory = TestConnectionPoolFactory.getInstance();
			TestConnectionPool pool = factory.getConnectionPool();
			connection = pool.takeConnection();
			ScriptRunner scriptRunner = new ScriptRunner(connection);
			scriptRunner.runScript(reader);
		} catch (FileNotFoundException e) {
			log.log(Level.ERROR, "File .sql wasn't found by the SCRIPT_PATH", e);
		} catch (IOException e) {
			log.log(Level.ERROR, "Error when authoclosing Reader", e);
		} catch (ConnectionPoolException e) {
			log.log(Level.ERROR, "Error when taking connection from DB", e);
		}
	}

	@After
	public void releaseConnection() {
		TestConnectionPoolFactory factory = TestConnectionPoolFactory.getInstance();
		TestConnectionPool pool = factory.getConnectionPool();
		pool.releaseConnection(connection);
	}

//	@Test
//	public void findPatientsDiagnosisByIdTest_IdAbsent() throws DaoException {
//		List<PatientDiagnosis> expected = new ArrayList<PatientDiagnosis>();
//		List<PatientDiagnosis> actual = diagnosisDao.findPatientsDiagnosisById(ID_ABSENT);
//		assertEquals(expected, actual);
//	}

}
