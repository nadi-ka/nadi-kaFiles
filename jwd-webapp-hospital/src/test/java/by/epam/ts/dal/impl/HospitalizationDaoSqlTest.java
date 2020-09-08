package by.epam.ts.dal.impl;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.HospitalizationDao;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.TestConnectionPool;
import by.epam.ts.util.DbScriptRunner;

/**
 * Test case for the class HospitalizationDaoSql; The test database
 * 'HospitalTest' is used;
 */
public class HospitalizationDaoSqlTest {

	private final static String absentId = "111";
	private final static String presentId = "e4a4baa0-25a5-4b60-9856-b55ec84d8c88";
	private final static int idHistoryPresent = 2;
	private final static String dateStartHospitalizationPresent = "2019-07-23";
	private final static String dateEndHospitalizationPresent = "2019-08-02";
	private final static String emptyString = "";

	private static Logger log = LogManager.getLogger(HospitalizationDaoSqlTest.class);

	@Mock
	private static ConnectionPool moskedConnectionPool;
	private static HospitalizationDao hospitalizationDao = DaoFactoryImpl.getInstance().getHospitalizationDao();

	@BeforeClass
	public static void initTestConnectionPool() throws Exception {
		moskedConnectionPool = new TestConnectionPool();
		try {
			moskedConnectionPool.initializePoolData();
		} catch (ConnectionPoolException e) {
			log.log(Level.ERROR, "Test pool wasn't initialized", e);
		}
		Whitebox.setInternalState(hospitalizationDao, "connectionPool", moskedConnectionPool);
	}

	@AfterClass
	public static void destroyTestConnectionPool() {
		moskedConnectionPool.dispose();
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.HospitalizationDaoSql#createNewHospitalization(by.epam.ts.bean.Hospitalization)}.
	 */
	@Test
	public void testCreateNewHospitalization_positiveResult() throws Exception {

		Hospitalization hospitalization = new Hospitalization(presentId, LocalDate.now());
		int insertedRowsExpected = 1;
		int insertedRowsActual = hospitalizationDao.createNewHospitalization(hospitalization);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertEquals(insertedRowsExpected, insertedRowsActual);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNewHospitalization_nullParameterValue() throws DaoException {

		Hospitalization hospitalization = null;
		hospitalizationDao.createNewHospitalization(hospitalization);

	}

	@Test(expected = DaoException.class)
	public void testCreateNewHospitalization_idAbsent() throws DaoException {

		Hospitalization idAbsentHospital = new Hospitalization(absentId, LocalDate.now());
		hospitalizationDao.createNewHospitalization(idAbsentHospital);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNewHospitalization_nullDateValue() throws DaoException {

		Hospitalization nullDateHospital = new Hospitalization(presentId, null);
		hospitalizationDao.createNewHospitalization(nullDateHospital);
	}

	@Test(expected = DaoException.class)
	public void testCreateNewHospitalization_nullIdValue() throws DaoException {

		Hospitalization nullIdHospital = new Hospitalization(null, LocalDate.now());
		hospitalizationDao.createNewHospitalization(nullIdHospital);
	}

	@Test(expected = DaoException.class)
	public void testCreateNewHospitalization_EmptyIdValue() throws DaoException {

		Hospitalization emptyIdHospital = new Hospitalization(emptyString, LocalDate.now());
		hospitalizationDao.createNewHospitalization(emptyIdHospital);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.HospitalizationDaoSql#updateDischargeDate(java.time.LocalDate, int)}.
	 */

	@Test(expected = NullPointerException.class)
	public void testUpdateDischargeDate_nullDateValue() throws DaoException {

		hospitalizationDao.updateDischargeDate(null, idHistoryPresent);
	}

	@Test
	public void testUpdateDischargeDate_negativeResult_idAbsent() throws DaoException {

		int insertedRowsExpected = 0;
		int idHystoryAbsent = -1;
		int insertedRowsActual = hospitalizationDao.updateDischargeDate(LocalDate.now(), idHystoryAbsent);

		assertEquals(insertedRowsExpected, insertedRowsActual);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.HospitalizationDaoSql#findHospitalizationsById(java.lang.String)}.
	 */
	@Test
	public void testFindHospitalizationsById_positiveResult() throws DaoException {

		List<Hospitalization> hospitalizationsExpected = new ArrayList<Hospitalization>();
		Hospitalization hospitalization = new Hospitalization(idHistoryPresent, presentId,
				LocalDate.parse(dateStartHospitalizationPresent), LocalDate.parse(dateEndHospitalizationPresent));
		hospitalizationsExpected.add(hospitalization);
		List<Hospitalization> hospitalizationsActual = hospitalizationDao.findHospitalizationsById(presentId);

		assertEquals(hospitalizationsExpected, hospitalizationsActual);
	}

	@Test
	public void testFindHospitalizationsById_negativeResult() throws DaoException {

		List<Hospitalization> hospitalizationsExpected = new ArrayList<Hospitalization>();
		List<Hospitalization> hospitalizationsActual1 = hospitalizationDao.findHospitalizationsById(absentId);
		List<Hospitalization> hospitalizationsActual2 = hospitalizationDao.findHospitalizationsById(null);
		List<Hospitalization> hospitalizationsActual3 = hospitalizationDao.findHospitalizationsById(emptyString);

		assertEquals(hospitalizationsExpected, hospitalizationsActual1);
		assertEquals(hospitalizationsExpected, hospitalizationsActual2);
		assertEquals(hospitalizationsExpected, hospitalizationsActual3);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.HospitalizationDaoSql#findLastHospitalizationById(java.lang.String)}.
	 */
	@Test
	public void testFindLastHospitalizationById_positiveResult() throws DaoException {

		Hospitalization hospitalizationExpected = new Hospitalization(idHistoryPresent, presentId,
				LocalDate.parse(dateStartHospitalizationPresent), LocalDate.parse(dateEndHospitalizationPresent));
		Hospitalization hospitalizationActual = hospitalizationDao.findLastHospitalizationById(presentId);

		assertEquals(hospitalizationExpected, hospitalizationActual);

	}

	@Test
	public void testFindLastHospitalizationById_negativeResult() throws DaoException {

		Hospitalization hospitalization1 = hospitalizationDao.findLastHospitalizationById(absentId);
		Hospitalization hospitalization2 = hospitalizationDao.findLastHospitalizationById(null);
		Hospitalization hospitalization3 = hospitalizationDao.findLastHospitalizationById(emptyString);

		assertNull(hospitalization1);
		assertNull(hospitalization2);
		assertNull(hospitalization3);

	}

}
