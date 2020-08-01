package by.epam.ts.dal.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.User;
import by.epam.ts.bean.role.UserRole;
import by.epam.ts.bean.specialty.Specialty;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.constant.ColumnNameHolder;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.factory.ConnectionPoolFactory;

public class UserDaoSQL implements UserDao {
	
	private final ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
	private ConnectionPool connectionPool = factory.getConnectionPool();

	private static final String sqlAddUserStaff = "INSERT INTO users (id_medical_staff, login, password, role, user_status) VALUES (?,?,?,?,?);";
	private static final String sqlAddUserPatient = "INSERT INTO users (id_patient, login, password, role, user_status) VALUES (?,?,?,?,?);";
	private static final String sqlFindStaffByEmail = "SELECT * FROM `medical-staff` WHERE email=(?);";
	private static final String sqlFindPatientByEmail = "SELECT * FROM patients WHERE email=(?);";
	private static final String sqlFindUserByLogin = "SELECT * FROM users WHERE login=(?);";
	private static final String sqlFindLogin = "SELECT login FROM users WHERE login=(?);";
	private static final String sqlAddPatient = "INSERT INTO patients VALUES (?,?,?,?,?);";
	private static final String sqlFindPatientBySurname = "SELECT * FROM patients WHERE surname=(?);";
	private static final String sqlFindPatientById = "SELECT * FROM patients WHERE id=(?);";
	private static final String sqlAddNewStaff = "INSERT INTO `medical-staff` VALUES (?,?,?,?,?);";
	private static final String sqlFindStaffById = "SELECT * FROM `medical-staff` WHERE id=(?);";
	private static final String sqlFindUserStaffBySurname = "SELECT `medical-staff`.*, users.role, users.user_status FROM `medical-staff` JOIN users ON `medical-staff`.id=users.id_medical_staff WHERE surname=(?);";
	private static final String sqlUpdateUserRole = "UPDATE users SET role = (?) WHERE id_medical_staff = (?);";
	private static final String sqlUpdateUserStatus = "UPDATE users SET user_status = (?) WHERE id_medical_staff = (?);";
	private static final String sqlUpdateStaffPersonalData = "UPDATE `medical-staff` SET surname = (?), name = (?), email = (?) WHERE id = (?);";
	private static final String sqlUpdatePatientPersonalData = "UPDATE patients SET surname = (?), name = (?), birth_date = (?), email = (?) WHERE id = (?);";

	private static final Logger log = LogManager.getLogger(UserDaoSQL.class);

	public int createUserPatient(User user) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement signUpPatient = null;

		try {
			connection = connectionPool.takeConnection();

			signUpPatient = connection.prepareStatement(sqlAddUserPatient);
			signUpPatient.setString(1, user.getId());
			signUpPatient.setString(2, user.getLogin());
			signUpPatient.setString(3, user.getPassword());
			signUpPatient.setInt(4, user.getRole().getRoleValue());
			signUpPatient.setBoolean(5, user.isUserStatus());
			insertedRows = signUpPatient.executeUpdate();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (signUpPatient != null) {
				try {
					signUpPatient.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

	public int createUserStaff(User user) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement signUpStaff = null;

		try {
			connection = connectionPool.takeConnection();
			signUpStaff = connection.prepareStatement(sqlAddUserStaff);
			signUpStaff.setString(1, user.getId());
			signUpStaff.setString(2, user.getLogin());
			signUpStaff.setString(3, user.getPassword());
			signUpStaff.setInt(4, user.getRole().getRoleValue());
			signUpStaff.setBoolean(5, user.isUserStatus());
			insertedRows = signUpStaff.executeUpdate();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (signUpStaff != null) {
				try {
					signUpStaff.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

	public MedicalStaff findStaffByEmail(String email) throws DaoException {
		Connection connection = null;
		MedicalStaff medicalStaff = null;
		PreparedStatement findStaffByEmail = null;
		try {
			connection = connectionPool.takeConnection();
			findStaffByEmail = connection.prepareStatement(sqlFindStaffByEmail);
			findStaffByEmail.setString(1, email);
			ResultSet staffResultSet = findStaffByEmail.executeQuery();
			if (!staffResultSet.next()) {
				return medicalStaff;
			}
			String id = staffResultSet.getString(ColumnNameHolder.STAFF_ID);
			String specialtyString = staffResultSet.getString(ColumnNameHolder.STAFF_SPECIALTY);
			Specialty specialty = Specialty.getSpecialty(specialtyString);
			String surname = staffResultSet.getString(ColumnNameHolder.STAFF_SURNAME);
			String name = staffResultSet.getString(ColumnNameHolder.STAFF_NAME);
			medicalStaff = new MedicalStaff(id, specialty, surname, name, email);
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (findStaffByEmail != null) {
				try {
					findStaffByEmail.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return medicalStaff;
	}

	public Patient findPatientByEmail(String email) throws DaoException {
		Connection connection = null;
		Patient patient = null;
		PreparedStatement findPatientByEmail = null;
		try {
			connection = connectionPool.takeConnection();
			findPatientByEmail = connection.prepareStatement(sqlFindPatientByEmail);
			findPatientByEmail.setString(1, email);
			ResultSet patientResultSet = findPatientByEmail.executeQuery();

			if (!patientResultSet.next()) {
				return patient;
			}
			String id = patientResultSet.getString(ColumnNameHolder.PATIENT_ID);
			String surname = patientResultSet.getString(ColumnNameHolder.PATIENT_SURNAME);
			String name = patientResultSet.getString(ColumnNameHolder.PATIENT_NAME);
			Date dateOfBirth = patientResultSet.getDate(ColumnNameHolder.PATIENT_BIRTH_DATE);
			patient = new Patient(id, surname, name, dateOfBirth.toLocalDate(), email);
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (findPatientByEmail != null) {
				try {
					findPatientByEmail.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return patient;
	}

	public User findUserByLogin(String login) throws DaoException {
		Connection connection = null;
		User user = null;
		PreparedStatement preparedStatement = null;
		ResultSet userResultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindUserByLogin);
			preparedStatement.setString(1, login);
			userResultSet = preparedStatement.executeQuery();

			if (!userResultSet.next()) {
				return user;
			}
			String idUser = null;
			int role = userResultSet.getInt(ColumnNameHolder.USERS_ROLE);
			UserRole userRole = UserRole.getUserRole(role);
			switch (userRole) {
			case PATIENT:
				idUser = userResultSet.getString(ColumnNameHolder.USERS_ID_PATIENT);
				break;
			default:
				idUser = userResultSet.getString(ColumnNameHolder.USERS_ID_STAFF);
			}
			String hashedPassword = userResultSet.getString(ColumnNameHolder.USERS_PASSWORD);
			boolean userStatus = userResultSet.getBoolean(ColumnNameHolder.USERS_STATUS);

			user = new User(idUser, login, hashedPassword, userRole, userStatus);

		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return user;
	}

	public String findLogin(String login) throws DaoException {
		Connection connection = null;
		String resultLogin = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindLogin);
			preparedStatement.setString(1, login);
			resultSet = preparedStatement.executeQuery();

			// if set is empty, users login is unique;
			if (!resultSet.next()) {
				return resultLogin;
			}
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		// returning the login means that users login isn't unique;
		return login;
	}

	public int createNewPatient(Patient patient) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();

			preparedStatement = connection.prepareStatement(sqlAddPatient);
			preparedStatement.setString(1, patient.getId());
			preparedStatement.setString(2, patient.getSurname());
			preparedStatement.setString(3, patient.getName());
			preparedStatement.setDate(4, Date.valueOf(patient.getDateOfBirth()), Calendar.getInstance());
			preparedStatement.setString(5, patient.getEmail());

			insertedRows = preparedStatement.executeUpdate();

		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

	public int updatePatientPersonalData(Patient patient) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();

			preparedStatement = connection.prepareStatement(sqlUpdatePatientPersonalData);
			preparedStatement.setString(1, patient.getSurname());
			preparedStatement.setString(2, patient.getName());
			preparedStatement.setDate(3, Date.valueOf(patient.getDateOfBirth()), Calendar.getInstance());
			preparedStatement.setString(4, patient.getEmail());
			preparedStatement.setString(5, patient.getId());

			insertedRows = preparedStatement.executeUpdate();

		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

	public List<Patient> findPatientBySurname(String surname) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Patient> patients = new ArrayList<Patient>();

		try {
			connection = connectionPool.takeConnection();

			preparedStatement = connection.prepareStatement(sqlFindPatientBySurname);
			preparedStatement.setString(1, surname);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString(ColumnNameHolder.PATIENT_ID);
				String name = resultSet.getString(ColumnNameHolder.PATIENT_NAME);
				Date dateOfBirth = resultSet.getDate(ColumnNameHolder.PATIENT_BIRTH_DATE);
				String email = resultSet.getString(ColumnNameHolder.PATIENT_EMAIL);
				Patient patient = new Patient(id, surname, name, dateOfBirth.toLocalDate(), email);
				patients.add(patient);
			}
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return patients;
	}

	public Patient findPatientById(String id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Patient patient = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindPatientById);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return patient;
			}
			String surname = resultSet.getString(ColumnNameHolder.PATIENT_SURNAME);
			String name = resultSet.getString(ColumnNameHolder.PATIENT_NAME);
			Date dateOfBirth = resultSet.getDate(ColumnNameHolder.PATIENT_BIRTH_DATE);
			String email = resultSet.getString(ColumnNameHolder.PATIENT_EMAIL);
			patient = new Patient(id, surname, name, dateOfBirth.toLocalDate(), email);

		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return patient;
	}

	public int createNewStaff(MedicalStaff medicalStaff) throws DaoException {
		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlAddNewStaff);
			preparedStatement.setString(1, medicalStaff.getId());
			preparedStatement.setString(2, medicalStaff.getSpecialty().getSpecialtyValue());
			preparedStatement.setString(3, medicalStaff.getSurname());
			preparedStatement.setString(4, medicalStaff.getName());
			preparedStatement.setString(5, medicalStaff.getEmail());

			insertedRows = preparedStatement.executeUpdate();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

	public int updateStaffPersonalData(String surname, String name, String email, String id) throws DaoException {
		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlUpdateStaffPersonalData);
			preparedStatement.setString(1, surname);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, id);

			insertedRows = preparedStatement.executeUpdate();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

	public MedicalStaff findStaffById(String id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		MedicalStaff staff = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindStaffById);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return staff;
			}
			String specialty = resultSet.getString(ColumnNameHolder.STAFF_SPECIALTY);
			String surname = resultSet.getString(ColumnNameHolder.STAFF_SURNAME);
			String name = resultSet.getString(ColumnNameHolder.STAFF_NAME);
			String email = resultSet.getString(ColumnNameHolder.STAFF_EMAIL);
			staff = new MedicalStaff(id, Specialty.getSpecialty(specialty), surname, name, email);

		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return staff;
	}

	public List<MedicalStaff> findUserStaffBySurname(String surname) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		MedicalStaff staff = null;
		List<MedicalStaff> staffList = new ArrayList<MedicalStaff>();

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindUserStaffBySurname);
			preparedStatement.setString(1, surname);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString(ColumnNameHolder.STAFF_ID);
				String specialty = resultSet.getString(ColumnNameHolder.STAFF_SPECIALTY);
				String name = resultSet.getString(ColumnNameHolder.STAFF_NAME);
				String email = resultSet.getString(ColumnNameHolder.STAFF_EMAIL);
				int role = resultSet.getInt(ColumnNameHolder.USERS_ROLE);
				boolean userStatus = resultSet.getBoolean(ColumnNameHolder.USERS_STATUS);
				staff = new MedicalStaff(id, Specialty.getSpecialty(specialty), surname, name, email);
				staff.setRole(UserRole.getUserRole(role));
				staff.setUserStatus(userStatus);
				staffList.add(staff);
			}
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return staffList;
	}

	public int updateStaffUserRole(int newRole, String id) throws DaoException {
		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlUpdateUserRole);
			preparedStatement.setInt(1, newRole);
			preparedStatement.setString(2, id);

			insertedRows = preparedStatement.executeUpdate();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

	public int updateUserStatus(boolean newStatus, String id) throws DaoException {
		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlUpdateUserStatus);
			preparedStatement.setBoolean(1, newStatus);
			preparedStatement.setString(2, id);

			insertedRows = preparedStatement.executeUpdate();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					log.log(Level.ERROR, "Error during closing the statement", ex);
				}
			}
			connectionPool.releaseConnection(connection);
		}
		return insertedRows;
	}

//	public static void main(String[] args) {
//		ConnectionPool connectionPool = new ConnectionPool();
//
//		try {
//			connectionPool.initializePoolData();
//			UserDaoSQL userDaoSQL = new UserDaoSQL(connectionPool);
//			
//			List<MedicalStaff> staffs = userDaoSQL.findUserStaffBySurname("Хаддад");
//			for (MedicalStaff st: staffs) {
//				System.out.println(st.getRole());
//			}
//			
//		} catch (ConnectionPoolException e) {
//			e.printStackTrace();
//
//		} catch (DaoException e) {
//			e.printStackTrace();
//		} finally {
//			connectionPool.dispose();
//		}
//	}
}
