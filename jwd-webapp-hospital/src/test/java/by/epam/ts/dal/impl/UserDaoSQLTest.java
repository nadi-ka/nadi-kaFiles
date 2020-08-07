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

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.User;
import by.epam.ts.bean.role.UserRole;
import by.epam.ts.bean.specialty.Specialty;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.factory.impl.DaoFactoryImpl;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.TestConnectionPool;
import by.epam.ts.util.DbScriptRunner;

/**
 * Test case for the class UserDaoSQL; The test database 'HospitalTest' is used;
 */
public class UserDaoSQLTest {

	private static final String idPresentInPatientsTable = "f5c6ece3-8131-4c5b-b055-ad683dac0526";
	private static final String idPresentInStaffTable = "5ec28331-a666-47f1-9e4a-3110cd812fd7";
	private static final String idPresentInTableUsersAsStaff = "6b076d07-87c7-4afb-8397-1b20ee624467";
	private static final String newId = "11111111-8131-4c5b-b055-ad683dac0526";
	private static final String absentId = "111";
	private static final String emailAbsentInDb = "111@gmail.com";
	private static final String newLoginUserPatient = "maximov";
	private static final String newLoginUserStaff = "karina";
	private static final String newHashedPassword = "$2a$12$ImD/c4NAGMIegCrU0HNx/u90B/IFBmZe.xRNPeAXLtE3rCWpwxlzW";
	private final static String emptyString = "";

	private static Logger log = LogManager.getLogger(UserDaoSQLTest.class);

	@Mock
	private static ConnectionPool moskedConnectionPool;
	private static UserDao userDao = DaoFactoryImpl.getInstance().getUserDao();

	@BeforeClass
	public static void initTestConnectionPool() {
		moskedConnectionPool = new TestConnectionPool();
		try {
			moskedConnectionPool.initializePoolData();
		} catch (ConnectionPoolException e) {
			log.log(Level.ERROR, "Test pool wasn't initialized", e);
		}
		Whitebox.setInternalState(userDao, "connectionPool", moskedConnectionPool);
	}

	@AfterClass
	public static void destroyTestConnectionPool() {
		moskedConnectionPool.dispose();
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#createUserPatient(by.epam.ts.bean.User)}.
	 */
//	@Test
//	public void testCreateUserPatient_positiveResult() throws Exception {
//
//		User user = new User(idPresentInPatientsTable, newLoginUserPatient, newHashedPassword, UserRole.PATIENT, true);
//		int insertedRowsExpected = 1;
//		int insertedRowsActual = userDao.createUserPatient(user);
//
//		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);
//
//		assertTrue(insertedRowsExpected == insertedRowsActual);
//	}

	@Test(expected = DaoException.class)
	public void testCreateUserPatient_idAbsent() throws DaoException {

		User user = new User(absentId, newLoginUserPatient, newHashedPassword, UserRole.PATIENT, true);
		userDao.createUserPatient(user);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateUserPatient_nullParameterValue() throws DaoException {

		User user = null;
		userDao.createUserPatient(user);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#createUserStaff(by.epam.ts.bean.User)}.
	 */
//	@Test
//	public void testCreateUserStaff_positiveResult() throws Exception {
//
//		User user = new User(idPresentInStaffTable, newLoginUserStaff, newHashedPassword, UserRole.NURSE, true);
//		int insertedRowsExpected = 1;
//		int insertedRowsActual = userDao.createUserStaff(user);
//
//		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);
//
//		assertTrue(insertedRowsExpected == insertedRowsActual);
//
//	}

	@Test(expected = DaoException.class)
	public void testCreateUserStaff_idAbsent() throws DaoException {

		User user = new User(absentId, newLoginUserStaff, newHashedPassword, UserRole.NURSE, true);
		userDao.createUserStaff(user);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateUserStaff_nullParameterValue() throws DaoException {

		User user = null;
		userDao.createUserStaff(user);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findStaffByEmail(java.lang.String)}.
	 */
	@Test
	public void testFindStaffByEmail_positiveResult() throws DaoException {

		String surname = "Григорян";
		String name = "Карина";
		String email = "grigorjan@gmail.com";

		MedicalStaff medicalStaffExpected = new MedicalStaff(idPresentInStaffTable, Specialty.NURSE, surname, name,
				email);
		MedicalStaff medicalStaffActual = userDao.findStaffByEmail(email);

		assertEquals(medicalStaffExpected, medicalStaffActual);

	}

	@Test
	public void testFindStaffByEmail_emailAbsent() throws DaoException {

		MedicalStaff medicalStaffActual_emailAbsent = userDao.findStaffByEmail(emailAbsentInDb);
		MedicalStaff medicalStaffActual_nullValueEmail = userDao.findStaffByEmail(null);
		MedicalStaff medicalStaffActual_emptyEmail = userDao.findStaffByEmail(emptyString);

		assertNull(medicalStaffActual_emailAbsent);
		assertNull(medicalStaffActual_nullValueEmail);
		assertNull(medicalStaffActual_emptyEmail);

	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findPatientByEmail(java.lang.String)}.
	 */
	@Test
	public void testFindPatientByEmail_positiveResult() throws DaoException {

		String surname = "Максимов";
		String name = "Мирон";
		String dateOfBirth = "1948-12-03";
		String email = "maximov@gmail.com";
		Patient patientExpected = new Patient(idPresentInPatientsTable, surname, name, LocalDate.parse(dateOfBirth),
				email);
		Patient patientActual = userDao.findPatientByEmail(email);

		assertEquals(patientExpected, patientActual);
	}

	@Test
	public void testFindPatientByEmail_emailAbsent() throws DaoException {

		Patient paient_emailAbsent = userDao.findPatientByEmail(emailAbsentInDb);
		Patient paient_nullValueEmail = userDao.findPatientByEmail(null);
		Patient paient_emptyEmail = userDao.findPatientByEmail(emptyString);

		assertNull(paient_emailAbsent);
		assertNull(paient_nullValueEmail);
		assertNull(paient_emptyEmail);
	}

	/**
	 * Test method for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findUserByLogin(java.lang.String)}.
	 */
	@Test
	public void testFindUserByLogin_positiveResult_userPatientFound() throws DaoException {

		String id = "e4a4baa0-25a5-4b60-9856-b55ec84d8c88";
		String login = "alfa";
		String hashedPassword = "$2a$12$phEo4stRjZ8QBGb4RMisAObHdTGdfru.nQFqpxTfRp5Wx2JM2T6VK";
		User userExpected = new User(id, login, hashedPassword, UserRole.PATIENT, true);
		User userActual = userDao.findUserByLogin(login);

		assertEquals(userExpected, userActual);
	}

	@Test
	public void testFindUserByLogin_positiveResult_userStaffFound() throws DaoException {

		String id = "6b076d07-87c7-4afb-8397-1b20ee624467";
		String login = "germini";
		String hashedPassword = "$2a$12$TV1uLRAYwCFXWnTNkCIpW.ZW9dT/Qxrndw1TyX0DgKJUnDEUPlUFi";
		User userExpected = new User(id, login, hashedPassword, UserRole.DOCTOR, true);
		User userActual = userDao.findUserByLogin(login);

		assertEquals(userExpected, userActual);
	}

	@Test
	public void testFindUserByLogin_negativeResult_loginAbsent() throws DaoException {

		String absentLogin = "a1a1";
		User userActual_absentId = userDao.findUserByLogin(absentLogin);
		User userActual_nullLoginValue = userDao.findUserByLogin(null);
		User userActual_emptyLogin = userDao.findUserByLogin(emptyString);

		assertNull(userActual_absentId);
		assertNull(userActual_nullLoginValue);
		assertNull(userActual_emptyLogin);

	}

	/**
	 * Test method for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findLogin(java.lang.String)}.
	 */
	@Test
	public void testFindLogin_positiveResult() throws DaoException {

		String newUniqueLogin = "scorpio";
		String loginIsUnique = userDao.findLogin(newUniqueLogin);

		assertNull(loginIsUnique);
	}

	@Test
	public void testFindLogin_negativeResult_alreadyPresent() throws DaoException {

		String newNotUniqueLoginExpected = "germini";
		String loginActual = userDao.findLogin(newNotUniqueLoginExpected);

		assertEquals(newNotUniqueLoginExpected, loginActual);
	}

	@Test
	public void testFindLogin_negativeResult_nullAndEmptyParameter() throws DaoException {

		String loginActual_nullValueParameter = userDao.findLogin(null);
		String loginActual_emptyParameter = userDao.findLogin(emptyString);

		assertNull(loginActual_emptyParameter);
		assertNull(loginActual_nullValueParameter);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#createNewPatient(by.epam.ts.bean.Patient)}.
	 */
	@Test
	public void testCreateNewPatient_positiveResult() throws Exception {

		String newSurname = "Сергеев";
		String newName = "Сергей";
		String dateOfBirth = "1950-05-05";
		Patient patient = new Patient(newId, newSurname, newName, LocalDate.parse(dateOfBirth), emailAbsentInDb);
		int insertedRowsExpected = 1;
		int insertedRowsActual = userDao.createNewPatient(patient);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertTrue(insertedRowsExpected == insertedRowsActual);
	}

	@Test(expected = DaoException.class)
	public void testCreateNewPatient_negativeResult_idAlreadyPresent() throws DaoException {

		String newSurname = "Сергеев";
		String newName = "Сергей";
		String dateOfBirth = "1950-05-05";
		Patient patient = new Patient(idPresentInPatientsTable, newSurname, newName, LocalDate.parse(dateOfBirth),
				emailAbsentInDb);

		userDao.createNewPatient(patient);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNewPatient_nullParameterValue() throws DaoException {

		Patient patient = null;
		userDao.createNewPatient(patient);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNewPatient_nullDateValue() throws DaoException {

		String newSurname = "Сергеев";
		String newName = "Сергей";
		Patient patient = new Patient(newId, newSurname, newName, null, emailAbsentInDb);
		userDao.createNewPatient(patient);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#updatePatientPersonalData(by.epam.ts.bean.Patient)}.
	 */
	@Test
	public void testUpdatePatientPersonalData_positiveResult() throws Exception {

		String newSurname = "Сергеев";
		String newName = "Сергей";
		String dateOfBirth = "1950-05-05";
		Patient patient = new Patient(idPresentInPatientsTable, newSurname, newName, LocalDate.parse(dateOfBirth),
				emailAbsentInDb);
		int updatedRowsExpected = 1;
		int updatedRowsActual = userDao.updatePatientPersonalData(patient);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertTrue(updatedRowsExpected == updatedRowsActual);
	}

	@Test
	public void testUpdatePatientPersonalData_idAbsent() throws DaoException {

		String newSurname = "Сергеев";
		String newName = "Сергей";
		String dateOfBirth = "1950-05-05";
		Patient patient_idAbsent = new Patient(absentId, newSurname, newName, LocalDate.parse(dateOfBirth),
				emailAbsentInDb);
		Patient patient_nullIdValue = new Patient(null, newSurname, newName, LocalDate.parse(dateOfBirth),
				emailAbsentInDb);
		Patient patient_emptyId = new Patient(emptyString, newSurname, newName, LocalDate.parse(dateOfBirth),
				emailAbsentInDb);
		int updatedRowsExpected = 0;
		int updatedRowsActual_idAbsent = userDao.updatePatientPersonalData(patient_idAbsent);
		int updatedRowsActual_nullIdValue = userDao.updatePatientPersonalData(patient_nullIdValue);
		int updatedRowsActual_emptyId = userDao.updatePatientPersonalData(patient_emptyId);

		assertTrue(updatedRowsExpected == updatedRowsActual_idAbsent);
		assertTrue(updatedRowsExpected == updatedRowsActual_nullIdValue);
		assertTrue(updatedRowsExpected == updatedRowsActual_emptyId);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdatePatientPersonalData_nullParameterValue() throws DaoException {

		Patient patient = null;
		userDao.updatePatientPersonalData(patient);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findPatientBySurname(java.lang.String)}.
	 */
	@Test
	public void testFindPatientBySurname_positiveResult() throws DaoException {

		String surname = "Максимов";
		String name = "Мирон";
		String dateOfBirth = "1948-12-03";
		String email = "maximov@gmail.com";
		List<Patient> patientsExpected = new ArrayList<Patient>();
		Patient patient = new Patient(idPresentInPatientsTable, surname, name, LocalDate.parse(dateOfBirth), email);
		patientsExpected.add(patient);

		List<Patient> patientsActual = userDao.findPatientBySurname(surname);

		assertEquals(patientsExpected, patientsActual);
	}

	@Test
	public void testFindPatientBySurname_patientAbsent() throws DaoException {

		String surnameAbsent = "Нахимов";
		List<Patient> patientsExpected = new ArrayList<Patient>();
		List<Patient> patientsActual_surnameAbsent = userDao.findPatientBySurname(surnameAbsent);
		List<Patient> patientsActual_nullSurnameValue = userDao.findPatientBySurname(null);
		List<Patient> patientsActual_emptySurnameValue = userDao.findPatientBySurname(emptyString);

		assertEquals(patientsExpected, patientsActual_surnameAbsent);
		assertEquals(patientsExpected, patientsActual_nullSurnameValue);
		assertEquals(patientsExpected, patientsActual_emptySurnameValue);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findPatientById(java.lang.String)}.
	 */
	@Test
	public void testFindPatientById_positiveResult() throws DaoException {

		String surname = "Максимов";
		String name = "Мирон";
		String dateOfBirth = "1948-12-03";
		String email = "maximov@gmail.com";
		Patient patientExpected = new Patient(idPresentInPatientsTable, surname, name, LocalDate.parse(dateOfBirth),
				email);

		Patient patientActual = userDao.findPatientById(idPresentInPatientsTable);

		assertEquals(patientExpected, patientActual);
	}

	@Test
	public void testFindPatientById_patientAbsent() throws DaoException {

		Patient patientActual_idAbsent = userDao.findPatientById(absentId);
		Patient patientActual_nullIdValue = userDao.findPatientById(null);
		Patient patientActual_emptyIdValue = userDao.findPatientById(emptyString);

		assertNull(patientActual_idAbsent);
		assertNull(patientActual_nullIdValue);
		assertNull(patientActual_emptyIdValue);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#createNewStaff(by.epam.ts.bean.MedicalStaff)}.
	 */
//	@Test
//	public void testCreateNewStaff_positiveResult() throws Exception {
//
//		String newSurname = "Сергеев";
//		String newName = "Сергей";
//		MedicalStaff medicalStaff = new MedicalStaff(newId, Specialty.DOCTOR, newSurname, newName, emailAbsentInDb);
//		int insertedRowsExpected = 1;
//		int insertedRowsActual = userDao.createNewStaff(medicalStaff);
//
//		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);
//
//		assertTrue(insertedRowsExpected == insertedRowsActual);
//	}

	@Test(expected = DaoException.class)
	public void testCreateNewStaff_idAlreadyExists() throws DaoException {

		String newSurname = "Сергеев";
		String newName = "Сергей";
		MedicalStaff medicalStaff = new MedicalStaff(idPresentInStaffTable, Specialty.DOCTOR, newSurname, newName,
				emailAbsentInDb);
		userDao.createNewStaff(medicalStaff);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNewStaff_nullParameterValue() throws DaoException {

		userDao.createNewStaff(null);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#updateStaffPersonalData(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
//	@Test
//	public void testUpdateStaffPersonalData_positiveResult() throws Exception {
//
//		String surname = "Антонова";
//		String name = "Антонина";
//		int updatedRowsExpected = 1;
//		int updatedRowsActual = userDao.updateStaffPersonalData(surname, name, emailAbsentInDb, idPresentInStaffTable);
//
//		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);
//
//		assertTrue(updatedRowsExpected == updatedRowsActual);
//	}

	@Test
	public void testUpdateStaffPersonalData_idAbsent() throws DaoException {

		String surname = "Антонова";
		String name = "Антонина";
		int updatedRowsExpected = 0;
		int updatedRowsActual_idAbsent = userDao.updateStaffPersonalData(surname, name, emailAbsentInDb, absentId);
		int updatedRowsActual_nullIdValue = userDao.updateStaffPersonalData(surname, name, emailAbsentInDb, null);
		int updatedRowsActual_emptyIdValue = userDao.updateStaffPersonalData(surname, name, emailAbsentInDb,
				emptyString);

		assertTrue(updatedRowsExpected == updatedRowsActual_idAbsent);
		assertTrue(updatedRowsExpected == updatedRowsActual_nullIdValue);
		assertTrue(updatedRowsExpected == updatedRowsActual_emptyIdValue);
	}

	@Test(expected = DaoException.class)
	public void testUpdateStaffPersonalData_nullValueSurname() throws DaoException {

		String name = "Антонина";
		userDao.updateStaffPersonalData(null, name, emailAbsentInDb, idPresentInStaffTable);
	}

	@Test(expected = DaoException.class)
	public void testUpdateStaffPersonalData_nullValueName() throws DaoException {

		String surname = "Антонова";
		userDao.updateStaffPersonalData(surname, null, emailAbsentInDb, idPresentInStaffTable);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findStaffById(java.lang.String)}.
	 */
	@Test
	public void testFindStaffById_positiveResult() throws DaoException {

		String surname = "Григорян";
		String name = "Карина";
		String email = "grigorjan@gmail.com";

		MedicalStaff staffExpected = new MedicalStaff(idPresentInStaffTable, Specialty.NURSE, surname, name, email);
		MedicalStaff staffActual = userDao.findStaffById(idPresentInStaffTable);

		assertEquals(staffExpected, staffActual);
	}

	@Test
	public void testFindStaffById_idAbsent() throws DaoException {

		MedicalStaff staffActual_idAbsent = userDao.findStaffById(absentId);
		MedicalStaff staffActual_nullIdValue = userDao.findStaffById(null);
		MedicalStaff staffActual_emptyId = userDao.findStaffById(emptyString);

		assertNull(staffActual_idAbsent);
		assertNull(staffActual_nullIdValue);
		assertNull(staffActual_emptyId);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#findUserStaffBySurname(java.lang.String)}.
	 */
	@Test
	public void testFindUserStaffBySurname_positiveResult() throws DaoException {

		String surname = "Давыдов";
		String name = "Давид";
		String email = "davydov@gmail.com";
		List<MedicalStaff> staffListExpected = new ArrayList<MedicalStaff>();
		MedicalStaff staff = new MedicalStaff(idPresentInTableUsersAsStaff, Specialty.DOCTOR, surname, name, email);
		staff.setRole(UserRole.DOCTOR);
		staff.setUserStatus(true);
		staffListExpected.add(staff);

		List<MedicalStaff> staffListActual = userDao.findUserStaffBySurname(surname);

		assertEquals(staffListExpected, staffListActual);
	}

	@Test
	public void testFindUserStaffBySurname_surnameAbsent() throws DaoException {

		String surnameAbsentInDb = "Нахимов";

		List<MedicalStaff> staffListExpected = new ArrayList<MedicalStaff>();
		List<MedicalStaff> staffListActual_surnameAbsent = userDao.findUserStaffBySurname(surnameAbsentInDb);
		List<MedicalStaff> staffListActual_nullSurnameValue = userDao.findUserStaffBySurname(null);
		List<MedicalStaff> staffListActual_emptySurname = userDao.findUserStaffBySurname(emptyString);

		assertEquals(staffListExpected, staffListActual_surnameAbsent);
		assertEquals(staffListExpected, staffListActual_nullSurnameValue);
		assertEquals(staffListExpected, staffListActual_emptySurname);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#updateStaffUserRole(int, java.lang.String)}.
	 */
//	@Test
//	public void testUpdateStaffUserRole_positiveResult() throws Exception {
//
//		int updatedRowsExpected = 1;
//		int updatedRowsActual = userDao.updateStaffUserRole(UserRole.ADMINISTRATOR.getRoleValue(),
//				idPresentInTableUsersAsStaff);
//
//		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);
//
//		assertTrue(updatedRowsExpected == updatedRowsActual);
//	}

	@Test
	public void testUpdateStaffUserRole_idAbsent() throws DaoException {

		int updatedRowsExpected = 0;
		int updatedRowsActual_idAbsent = userDao.updateStaffUserRole(UserRole.ADMINISTRATOR.getRoleValue(), absentId);
		int updatedRowsActual_nullIdValue = userDao.updateStaffUserRole(UserRole.ADMINISTRATOR.getRoleValue(), null);
		int updatedRowsActual_emptyId = userDao.updateStaffUserRole(UserRole.ADMINISTRATOR.getRoleValue(), emptyString);

		assertTrue(updatedRowsExpected == updatedRowsActual_idAbsent);
		assertTrue(updatedRowsExpected == updatedRowsActual_nullIdValue);
		assertTrue(updatedRowsExpected == updatedRowsActual_emptyId);
	}

	@Test(expected = DaoException.class)
	public void testUpdateStaffUserRole_roleValueAbsent() throws DaoException {

		int userRoleAbsentInDb = -1;
		userDao.updateStaffUserRole(userRoleAbsentInDb, idPresentInTableUsersAsStaff);
	}

	/**
	 * Test methods for
	 * {@link by.epam.ts.dal.impl.UserDaoSQL#updateUserStatus(boolean, java.lang.String)}.
	 */
	@Test
	public void testUpdateUserStatus_positiveResult() throws Exception {

		int updatedRowsExpected = 1;
		int updatedRowsActual = userDao.updateUserStatus(false, idPresentInTableUsersAsStaff);

		DbScriptRunner.dropAndRestoreTestDB(moskedConnectionPool);

		assertTrue(updatedRowsExpected == updatedRowsActual);
	}

	@Test
	public void testUpdateUserStatus_idAbsent() throws DaoException {

		int updatedRowsExpected = 0;
		int updatedRowsActual_idAbsent = userDao.updateUserStatus(false, absentId);
		int updatedRowsActual_nullIdValue = userDao.updateUserStatus(false, null);
		int updatedRowsActual_emptyId = userDao.updateUserStatus(false, emptyString);

		assertTrue(updatedRowsExpected == updatedRowsActual_idAbsent);
		assertTrue(updatedRowsExpected == updatedRowsActual_nullIdValue);
		assertTrue(updatedRowsExpected == updatedRowsActual_emptyId);
	}

}
