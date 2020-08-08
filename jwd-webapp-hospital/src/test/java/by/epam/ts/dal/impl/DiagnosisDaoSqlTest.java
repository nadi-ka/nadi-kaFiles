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

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.DiagnosisDao;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.TestConnectionPool;
import by.epam.ts.util.DbScriptRunner;

/**
 * Test case for the class DiagnosisDaoSql; The test database 'HospitalTest' is
 * used;
 */
public class DiagnosisDaoSqlTest {

	private final static String absentId = "111";
	private final static String presentId = "e4a4baa0-25a5-4b60-9856-b55ec84d8c88";
	private final static String hospitalizationDateCorrect = "2019-07-23";
	private final static String hospitalizationDateWrong = "2019-07-24";
	private final static String diagnosisMyopia = "миопия";
	private final static String diagnosisRetinalDetechment = "отслойка сетчатки";
	private final static String codeMyopia = "H52.13";
	private final static String codeRetinalDetechment = "H33.0";
	private final static String emptyString = "";
	private final static String separator = ", ";
	private final static int bedDaysRetinalDetechment = 10;

	private static Logger log = LogManager.getLogger(DiagnosisDaoSqlTest.class);

	@Mock
	private static ConnectionPool moskedConnectionPool;
	private static DiagnosisDao diagnosisDao = DaoFactoryImpl.getInstance().getDiagnosisDao();

	@BeforeClass
	public static void initTestConnectionPool() {
		moskedConnectionPool = new TestConnectionPool();
		try {
			moskedConnectionPool.initializePoolData();
		} catch (ConnectionPoolException e) {
			log.log(Level.ERROR, "Test pool wasn't initialized", e);
		}
		Whitebox.setInternalState(diagnosisDao, "connectionPool", moskedConnectionPool);
	}

	@AfterClass
	public static void destroyTestConnectionPool() {
		moskedConnectionPool.dispose();
	}

	@Test
	public void findPatientsDiagnosisByIdTest_negativeResult_idAbsent() throws DaoException {

		List<PatientDiagnosis> expected = new ArrayList<PatientDiagnosis>();
		List<PatientDiagnosis> actual = diagnosisDao.findPatientsDiagnosisById(absentId);
		
		assertEquals(expected, actual);
	}

	@Test
	public void findPatientsDiagnosisByIdTest_positiveResult() throws DaoException {

		List<PatientDiagnosis> actualList = diagnosisDao.findPatientsDiagnosisById(presentId);
		int diagnosisListSizeExpected = 2;
		int diagnosisListSizeActual = actualList.size();
		String diagnosisListFirstNameExpected = diagnosisRetinalDetechment;
		String diagnosisListFirstNameActual = actualList.get(0).getDiagnosisName();

		assertEquals(diagnosisListSizeExpected, diagnosisListSizeActual);
		assertEquals(diagnosisListFirstNameExpected, diagnosisListFirstNameActual);
	}

	@Test
	public void findPatientsDiagnosisByIdTest_nullAndEmptyValue() throws DaoException {

		List<PatientDiagnosis> expected = new ArrayList<PatientDiagnosis>();
		List<PatientDiagnosis> actualWithNullId = diagnosisDao.findPatientsDiagnosisById(null);
		List<PatientDiagnosis> actualWithEmptyId = diagnosisDao.findPatientsDiagnosisById(emptyString);

		assertEquals(expected, actualWithNullId);
		assertEquals(expected, actualWithEmptyId);
	}

	@Test
	public void findCurrentDiagnosisByIdTest_negativeResult() throws DaoException {

		List<PatientDiagnosis> expected = new ArrayList<PatientDiagnosis>();
		LocalDate correctDate = LocalDate.parse(hospitalizationDateCorrect);
		LocalDate dateLater = LocalDate.parse(hospitalizationDateWrong);
		List<PatientDiagnosis> actualAbsentId = diagnosisDao.findCurrentDiagnosisById(absentId, correctDate);
		List<PatientDiagnosis> actualDateLater = diagnosisDao.findCurrentDiagnosisById(presentId, dateLater);

		assertEquals(expected, actualAbsentId);
		assertEquals(expected, actualDateLater);

	}

	@Test
	public void findCurrentDiagnosisByIdTest_positiveResult() throws DaoException {

		LocalDate from = LocalDate.parse(hospitalizationDateCorrect);
		List<PatientDiagnosis> actualList = diagnosisDao.findCurrentDiagnosisById(presentId, from);
		int diagnosisListSizeExpected = 2;
		int diagnosisListSizeActual = actualList.size();
		String diagnosisNamesExpected = diagnosisRetinalDetechment + separator + diagnosisMyopia;
		String diagnosisNamesActual = actualList.get(0).getDiagnosisName() + ", "
				+ actualList.get(1).getDiagnosisName();

		assertEquals(diagnosisListSizeExpected, diagnosisListSizeActual);
		assertEquals(diagnosisNamesExpected, diagnosisNamesActual);
	}

	@Test
	public void findCurrentDiagnosisByIdTest_nullAndEmptyId() throws DaoException {

		List<PatientDiagnosis> expected = new ArrayList<PatientDiagnosis>();
		LocalDate from = LocalDate.parse(hospitalizationDateCorrect);
		List<PatientDiagnosis> actualWithNullId = diagnosisDao.findCurrentDiagnosisById(null, from);
		List<PatientDiagnosis> actualWithEmptyId = diagnosisDao.findCurrentDiagnosisById(emptyString, from);

		assertEquals(expected, actualWithNullId);
		assertEquals(expected, actualWithEmptyId);
	}

	@Test(expected = NullPointerException.class)
	public void findCurrentDiagnosisByIdTest_nullDateValue() throws DaoException {

		diagnosisDao.findCurrentDiagnosisById(presentId, null);
	}

	@Test
	public void createNewDiagnosisTest_positiveResult()
			throws Exception {

		String newDiagnosisCode = "H77.77";
		String newDiagnosisName = "ретинопатия";
		Diagnosis diagnosis = new Diagnosis(newDiagnosisCode, newDiagnosisName);
		int insertedRowsExpected = 1;
		int insertedRowsActual = diagnosisDao.createNewDiagnosis(diagnosis);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertEquals(insertedRowsExpected, insertedRowsActual);
	}
	
	@Test(expected = NullPointerException.class)
	public void createNewDiagnosisTest_nullParameterValue() throws DaoException {
		
		Diagnosis diagnosis = null;
		diagnosisDao.createNewDiagnosis(diagnosis);
	}

	@Test(expected = DaoException.class)
	public void createNewDiagnosisTest_diagnosisAlreadyExists() throws DaoException {

		Diagnosis diagnosisAlreadyPresent = new Diagnosis(codeRetinalDetechment, diagnosisRetinalDetechment,
				bedDaysRetinalDetechment);
		diagnosisDao.createNewDiagnosis(diagnosisAlreadyPresent);

	}

	@Test(expected = DaoException.class)
	public void createNewDiagnosisTest_nullValuesDiagnosisNameAndCode() throws DaoException {

		Diagnosis diagnosisNullValues = new Diagnosis(null, null, bedDaysRetinalDetechment);
		diagnosisDao.createNewDiagnosis(diagnosisNullValues);
	}

//	@Test
//	public void createPatientDiagnosisTest_positiveResult()
//			throws Exception {
//
//		List<PatientDiagnosis> diagnosisList = new ArrayList<PatientDiagnosis>();
//		PatientDiagnosis primaryDiagnosis = new PatientDiagnosis(presentId, codeRetinalDetechment,
//				true, LocalDate.now());
//		PatientDiagnosis secondaryDiagnosis = new PatientDiagnosis(presentId, codeMyopia, false,
//				LocalDate.now());
//		diagnosisList.add(primaryDiagnosis);
//		diagnosisList.add(secondaryDiagnosis);
//
//		int[] insertedRowsExpected = { 1, 1 };
//		int[] insertedRowsActual = diagnosisDao.createPatientDiagnosis(diagnosisList);
//
//		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);
//
//		assertArrayEquals(insertedRowsExpected, insertedRowsActual);
//	}
	
	@Test(expected = NullPointerException.class)
	public void createPatientDiagnosisTest_nullParameterValue()
			throws DaoException {
		
		List<PatientDiagnosis> diagnosisList = null;
		diagnosisDao.createPatientDiagnosis(diagnosisList);
		
	}

	@Test(expected = DaoException.class)
	public void createPatientDiagnosisTest_patientIdNotExists() throws DaoException {

		List<PatientDiagnosis> diagnosisList = new ArrayList<PatientDiagnosis>();
		PatientDiagnosis diagnosis = new PatientDiagnosis(absentId, codeRetinalDetechment, true, LocalDate.now());
		diagnosisList.add(diagnosis);
		diagnosisDao.createPatientDiagnosis(diagnosisList);
	}

	@Test
	public void readAllDiagnosisTest() throws DaoException {

		List<Diagnosis> actualList = diagnosisDao.readAllDiagnosis();
		int amountExpected = 8;
		int amountActual = actualList.size();

		assertEquals(amountExpected, amountActual);
	}

	@Test
	public void findShortDiagnosisByIdAndDateTest_negativeResult() throws DaoException {

		List<Diagnosis> expected = new ArrayList<Diagnosis>();
		LocalDate correctDate = LocalDate.parse(hospitalizationDateCorrect);
		LocalDate dateLater = LocalDate.parse(hospitalizationDateWrong);
		List<Diagnosis> actualAbsentId = diagnosisDao.findShortDiagnosisByIdAndDate(absentId, correctDate);
		List<Diagnosis> actualDateLater = diagnosisDao.findShortDiagnosisByIdAndDate(presentId, dateLater);

		assertEquals(expected, actualAbsentId);
		assertEquals(expected, actualDateLater);
	}

	@Test
	public void findShortDiagnosisByIdAndDateTest_positiveResult() throws DaoException {

		List<Diagnosis> expectedList = new ArrayList<Diagnosis>();
		Diagnosis diagnosis = new Diagnosis(codeRetinalDetechment, diagnosisRetinalDetechment,
				bedDaysRetinalDetechment);
		expectedList.add(diagnosis);

		LocalDate from = LocalDate.parse(hospitalizationDateCorrect);
		List<Diagnosis> actualList = diagnosisDao.findShortDiagnosisByIdAndDate(presentId, from);

		assertEquals(expectedList, actualList);
	}

	@Test
	public void findShortDiagnosisByIdAndDateTest_nullAndEmptyId() throws DaoException {

		List<Diagnosis> expected = new ArrayList<Diagnosis>();
		LocalDate from = LocalDate.parse(hospitalizationDateCorrect);
		List<Diagnosis> actualWithNullId = diagnosisDao.findShortDiagnosisByIdAndDate(null, from);
		List<Diagnosis> actualWithEmptyId = diagnosisDao.findShortDiagnosisByIdAndDate(emptyString, from);

		assertEquals(expected, actualWithNullId);
		assertEquals(expected, actualWithEmptyId);
	}

	@Test(expected = NullPointerException.class)
	public void findShortDiagnosisByIdAndDateTest_nullDateValue() throws DaoException {

		diagnosisDao.findShortDiagnosisByIdAndDate(presentId, null);
	}

}
