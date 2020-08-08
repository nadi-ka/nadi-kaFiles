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

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.treat_status.TreatmentStatus;
import by.epam.ts.bean.treat_type.TreatmentType;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.TreatmentDao;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.TestConnectionPool;
import by.epam.ts.util.DbScriptRunner;

/**
 * Test case for the class TreatmentDaoSql; The test database 'HospitalTest' is
 * used;
 */
public class TreatmentDaoSqlTest {

	private final static String absentId = "111";
	private final static String presentId = "e4a4baa0-25a5-4b60-9856-b55ec84d8c88";
	private final static String presentDoctorId = "4dc191b9-3477-423c-8493-cfa531bc2b0b";
	private final static String hospitalizationDateCorrect = "2019-07-23";
	private final static String hospitalizationDateLater = "2019-07-25";
	private final static String treatmentName = "лазеркоагуляция";
	private final static int presentIdAppointment = 2;
	private final static int absentIdAppointment = 0;
	private final static String emptyString = "";
	private final static String delimiter = ", ";

	private static Logger log = LogManager.getLogger(TreatmentDaoSqlTest.class);

	@Mock
	private static ConnectionPool moskedConnectionPool;
	private static TreatmentDao treatmentDao = DaoFactoryImpl.getInstance().getTreatmentDao();

	@BeforeClass
	public static void initTestConnectionPool() {
		moskedConnectionPool = new TestConnectionPool();
		try {
			moskedConnectionPool.initializePoolData();
		} catch (ConnectionPoolException e) {
			log.log(Level.ERROR, "Test pool wasn't initialized", e);
		}
		Whitebox.setInternalState(treatmentDao, "connectionPool", moskedConnectionPool);
	}

	@AfterClass
	public static void destroyTestConnectionPool() {
		moskedConnectionPool.dispose();
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.TreatmentDaoSql#findPatientsTreatmentById(java.lang.String)}.
	 */
	@Test
	public void testFindPatientsTreatmentById_positiveResult() throws DaoException {

		List<Treatment> listActual = treatmentDao.findPatientsTreatmentById(presentId);
		int listSizeExpected = 2;
		int listSizeActual = listActual.size();
		String procedureName1 = "Витрэктомия";
		String procedureName2 = "Вигамокс";
		String procedureNamesExpected = procedureName1 + delimiter + procedureName2;
		String procedureNamesActual = listActual.get(0).getTreatmentName() + delimiter
				+ listActual.get(1).getTreatmentName();

		assertEquals(listSizeExpected, listSizeActual);
		assertEquals(procedureNamesExpected, procedureNamesActual);

	}

	@Test
	public void testFindPatientsTreatmentById_negativeResult() throws DaoException {

		List<Treatment> listExpected = new ArrayList<Treatment>();
		List<Treatment> listActual1 = treatmentDao.findPatientsTreatmentById(absentId);
		List<Treatment> listActual2 = treatmentDao.findPatientsTreatmentById(null);
		List<Treatment> listActual3 = treatmentDao.findPatientsTreatmentById(emptyString);

		assertEquals(listExpected, listActual1);
		assertEquals(listExpected, listActual2);
		assertEquals(listExpected, listActual3);

	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.TreatmentDaoSql#updateConsent(int, boolean)}.
	 */
	@Test
	public void testUpdateConsent_positiveResult() throws Exception {

		int updatedRowsExpected = 1;
		int updatedRowsActual = treatmentDao.updateConsent(presentIdAppointment, false);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertTrue(updatedRowsExpected == updatedRowsActual);
	}

	@Test
	public void testUpdateConsent_negativeResult_idAppointmentAbsent() throws DaoException {

		int updatedRowsExpected = 0;
		int updatedRowsActual = treatmentDao.updateConsent(absentIdAppointment, false);

		assertTrue(updatedRowsExpected == updatedRowsActual);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.TreatmentDaoSql#createPatientTreatment(by.epam.ts.bean.Treatment)}.
	 */
	@Test
	public void testCreatePatientTreatment_positiveResult() throws Exception {

		Treatment treatment = new Treatment(presentId, TreatmentType.SURGICAL, treatmentName, presentDoctorId,
				LocalDate.now(), LocalDate.now(), true);
		int insertedRowsExpected = 1;
		int insertedRowsActual = treatmentDao.createPatientTreatment(treatment);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertTrue(insertedRowsExpected == insertedRowsActual);
	}

	@Test(expected = NullPointerException.class)
	public void testCreatePatientTreatment_nullParameterValue() throws DaoException {

		Treatment treatment = null;
		treatmentDao.createPatientTreatment(treatment);
	}

	@Test(expected = NullPointerException.class)
	public void testCreatePatientTreatment_nullDateValue() throws DaoException {

		Treatment treatment = new Treatment(presentId, TreatmentType.SURGICAL, treatmentName, presentDoctorId, null,
				null, true);
		treatmentDao.createPatientTreatment(treatment);
	}

	@Test(expected = DaoException.class)
	public void testCreatePatientTreatment_nullIdValue() throws DaoException {

		Treatment treatmentNullPatientIdVal = new Treatment(null, TreatmentType.SURGICAL, treatmentName,
				presentDoctorId, LocalDate.now(), LocalDate.now(), true);
		treatmentDao.createPatientTreatment(treatmentNullPatientIdVal);
	}

	@Test(expected = DaoException.class)
	public void testCreatePatientTreatment_nullDoctorIdValue() throws DaoException {

		Treatment treatmentNullDoctorIdVal = new Treatment(presentId, TreatmentType.SURGICAL, treatmentName, null,
				LocalDate.now(), LocalDate.now(), true);
		treatmentDao.createPatientTreatment(treatmentNullDoctorIdVal);
	}

	@Test(expected = DaoException.class)
	public void testCreatePatientTreatment_idAbsent() throws DaoException {

		Treatment treatmentIdAbsent = new Treatment(absentId, TreatmentType.SURGICAL, treatmentName, presentDoctorId,
				LocalDate.now(), LocalDate.now(), true);
		treatmentDao.createPatientTreatment(treatmentIdAbsent);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.TreatmentDaoSql#createCurrentTreatment(by.epam.ts.bean.CurrentTreatment)}.
	 */
	@Test
	public void testCreateCurrentTreatment_positiveResult() throws Exception {

		CurrentTreatment treatment = new CurrentTreatment(presentIdAppointment, LocalDate.now(), presentDoctorId,
				TreatmentStatus.COMPLETED);
		int insertedRowsExpected = 1;
		int insertedRowsActual = treatmentDao.createCurrentTreatment(treatment);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertTrue(insertedRowsExpected == insertedRowsActual);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateCurrentTreatment_nullParameterValue() throws DaoException {

		CurrentTreatment treatment = null;
		treatmentDao.createCurrentTreatment(treatment);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateCurrentTreatment_nullDateValue() throws DaoException {

		CurrentTreatment treatment = new CurrentTreatment(presentIdAppointment, null, presentDoctorId,
				TreatmentStatus.COMPLETED);
		;
		treatmentDao.createCurrentTreatment(treatment);
	}

	@Test(expected = DaoException.class)
	public void testCreateCurrentTreatment_idAppointmentAbsent() throws DaoException {

		CurrentTreatment treatment = new CurrentTreatment(absentIdAppointment, LocalDate.now(), presentDoctorId,
				TreatmentStatus.COMPLETED);
		treatmentDao.createCurrentTreatment(treatment);
	}

	@Test(expected = DaoException.class)
	public void testCreateCurrentTreatment_idStaffAbsent() throws DaoException {

		CurrentTreatment treatment = new CurrentTreatment(presentIdAppointment, LocalDate.now(), absentId,
				TreatmentStatus.COMPLETED);
		treatmentDao.createCurrentTreatment(treatment);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.TreatmentDaoSql#findCurrentHospitalizationTreatment(java.lang.String, java.time.LocalDate)}.
	 */
	@Test
	public void testFindCurrentHospitalizationTreatment_positiveResult() throws DaoException {

		List<Treatment> prescriptionsActual = treatmentDao.findCurrentHospitalizationTreatment(presentId,
				LocalDate.parse(hospitalizationDateCorrect));

		int prescriptionsSizeExpected = 2;
		int prescriptionsSizeActual = prescriptionsActual.size();
		String treatmentName1 = "Витрэктомия";
		String treatmentName2 = "Вигамокс";
		String treatmentNameExpected = treatmentName1 + delimiter + treatmentName2;
		String treatmentNameActual = prescriptionsActual.get(0).getTreatmentName() + delimiter
				+ prescriptionsActual.get(1).getTreatmentName();

		assertTrue(prescriptionsSizeExpected == prescriptionsSizeActual);
		assertEquals(treatmentNameExpected, treatmentNameActual);
	}

	@Test
	public void testFindCurrentHospitalizationTreatment_negativeResult_dateLater() throws DaoException {

		List<Treatment> prescriptionsExpected = new ArrayList<Treatment>();
		List<Treatment> prescriptionsActual = treatmentDao.findCurrentHospitalizationTreatment(presentId,
				LocalDate.parse(hospitalizationDateLater));

		assertEquals(prescriptionsExpected, prescriptionsActual);
	}

	@Test(expected = NullPointerException.class)
	public void testFindCurrentHospitalizationTreatment_nullDateValue() throws DaoException {

		treatmentDao.findCurrentHospitalizationTreatment(presentId, null);
	}

	@Test
	public void testFindCurrentHospitalizationTreatment_idAbsent() throws DaoException {

		List<Treatment> prescriptionsExpected = new ArrayList<Treatment>();
		List<Treatment> prescriptionsActual_absentId = treatmentDao.findCurrentHospitalizationTreatment(absentId,
				LocalDate.parse(hospitalizationDateCorrect));
		List<Treatment> prescriptionsActual_nullId = treatmentDao.findCurrentHospitalizationTreatment(null,
				LocalDate.parse(hospitalizationDateCorrect));
		List<Treatment> prescriptionsActual_emptyId = treatmentDao.findCurrentHospitalizationTreatment(emptyString,
				LocalDate.parse(hospitalizationDateCorrect));

		assertEquals(prescriptionsExpected, prescriptionsActual_absentId);
		assertEquals(prescriptionsExpected, prescriptionsActual_emptyId);
		assertEquals(prescriptionsExpected, prescriptionsActual_nullId);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.TreatmentDaoSql#findCurrentTreatmentByAppointmentId(int)}.
	 */
	@Test
	public void testFindCurrentTreatmentByAppointmentId_positiveResult() throws DaoException {

		List<CurrentTreatment> treatmentListActual = treatmentDao
				.findCurrentTreatmentByAppointmentId(presentIdAppointment);
		int listSizeExpected = 4;
		int listSizeActual = treatmentListActual.size();

		assertTrue(listSizeExpected == listSizeActual);

	}

	@Test
	public void testFindCurrentTreatmentByAppointmentId_negativeResult_idAbsent() throws DaoException {

		List<CurrentTreatment> treatmentListExpected = new ArrayList<CurrentTreatment>();
		List<CurrentTreatment> treatmentListActual = treatmentDao
				.findCurrentTreatmentByAppointmentId(absentIdAppointment);

		assertEquals(treatmentListExpected, treatmentListActual);
	}

}
