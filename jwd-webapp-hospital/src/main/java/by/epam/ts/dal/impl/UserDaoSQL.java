package by.epam.ts.dal.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.UserDao;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;

public class UserDaoSQL implements UserDao {

	private ConnectionPool connectionPool;

	private static final String sqlAddUserStaff = "INSERT INTO users (id_medical_staff, login, password, role, user_status) VALUES (?,?,?,?,?);";
	private static final String sqlAddUserPatient = "INSERT INTO users (id_patient, login, password, role, user_status) VALUES (?,?,?,?,?);";
	private static final String sqlFindStaffByEmail = "SELECT * FROM `medical-staff` WHERE email=(?);";
	private static final String sqlFindPatientByEmail = "SELECT * FROM patients WHERE email=(?);";
	private static final String sqlFindUserByLoginPassword = "SELECT * FROM users WHERE login=(?) AND password=(?);";
	private static final String sqlFindTreatmentByPatientId = "SELECT id_appointment, treatment_type, treatment_name, `data_begin/holding`, date_finish, consent, surname, name FROM treatment JOIN `medical-staff` ON treatment.id_assigned_by=`medical-staff`.id WHERE id_patient=(?);";
	private static final String sqlFindLogin = "SELECT login FROM users WHERE login=(?);";
	private static final String sqlUpdateConsent = "UPDATE treatment SET consent=(?) WHERE id_appointment=(?);";
	private static final String sqlAddPatient = "INSERT INTO patients VALUES (?,?,?,?,?);";
	private static final String sqlFindPatientBySurname = "SELECT * FROM patients WHERE surname=(?);";

	private static final Logger log = LogManager.getLogger(UserDaoSQL.class);

	public UserDaoSQL(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public int addUserPatient(User user) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement signUpPatient = null;

		try {
			connection = connectionPool.takeConnection();

			signUpPatient = connection.prepareStatement(sqlAddUserPatient);
			signUpPatient.setString(1, user.getId());
			signUpPatient.setString(2, user.getLogin());
			signUpPatient.setString(3, user.getPassword());
			signUpPatient.setInt(4, user.getRole());
			signUpPatient.setBoolean(5, user.isUserStatus());
			insertedRows = signUpPatient.executeUpdate();
		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during preparing/executing INSERT Statement", ex);
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

	public int addUserStaff(User user) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement signUpStaff = null;

		try {
			connection = connectionPool.takeConnection();
			signUpStaff = connection.prepareStatement(sqlAddUserStaff);
			signUpStaff.setString(1, user.getId());
			signUpStaff.setString(2, user.getLogin());
			signUpStaff.setString(3, user.getPassword());
			signUpStaff.setInt(4, user.getRole());
			signUpStaff.setBoolean(5, user.isUserStatus());
			insertedRows = signUpStaff.executeUpdate();
		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during preparing/executing INSERT Statement", ex);
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

			String id = staffResultSet.getString(1);
			String specialty = staffResultSet.getString(2);
			String surname = staffResultSet.getString(3);
			String name = staffResultSet.getString(4);
			medicalStaff = new MedicalStaff(id, specialty, surname, name, email);
		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during reading from DB.", ex);
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

			String id = patientResultSet.getString("id");
			String surname = patientResultSet.getString("surname");
			String name = patientResultSet.getString("name");
			Date dateOfBirth = patientResultSet.getDate("birth_date");
			patient = new Patient(id, surname, name, dateOfBirth, email);
		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during reading from DB.", ex);
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

	public User findUserByLoginPassword(String login, String password) throws DaoException {
		Connection connection = null;
		User user = null;
		PreparedStatement preparedStatement = null;
		ResultSet userResultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindUserByLoginPassword);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			userResultSet = preparedStatement.executeQuery();

			if (!userResultSet.next()) {
				return user;
			}
			String idStaff = userResultSet.getString(2);
			String idPatient = userResultSet.getString(3);
			String idUser = null;
			if (idStaff != null) {
				idUser = idStaff;
			} else {
				idUser = idPatient;
			}
			int role = userResultSet.getInt(6);
			boolean userStatus = userResultSet.getBoolean(7);

			user = new User(idUser, login, role, userStatus);

		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during reading from DB.", ex);
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

	public List<Treatment> findPatientsTreatmentById(String id) throws DaoException {
		Connection connection = null;
		Treatment treatment = null;
		List<Treatment> prescriptions = new ArrayList<Treatment>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindTreatmentByPatientId);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int idAppointment = resultSet.getInt("id_appointment");
				String treatmentType = resultSet.getString("treatment_type");
				String treatmentName = resultSet.getString("treatment_name");
				Date dateBeggining = resultSet.getDate("data_begin/holding");
				Date dateFinishing = resultSet.getDate("date_finish");
				boolean consent = resultSet.getBoolean("consent");
				String doctorSurname = resultSet.getString("surname");
				String doctorName = resultSet.getString("name");

				treatment = new Treatment(idAppointment, id, treatmentType, treatmentName, doctorSurname, doctorName,
						dateBeggining, dateFinishing, consent);
				prescriptions.add(treatment);
			}
		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during reading from DB.", ex);
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
		return prescriptions;
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
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during reading from DB.", ex);
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

	public int[] updateConsent(Map<Integer, Boolean> consentMap) throws DaoException {
		int[] count;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlUpdateConsent);
			connection.setAutoCommit(false);

			for (Map.Entry<Integer, Boolean> entry : consentMap.entrySet()) {
				preparedStatement.setBoolean(1, entry.getValue().booleanValue());
				preparedStatement.setInt(2, entry.getKey());
				preparedStatement.addBatch();
			}
			count = preparedStatement.executeBatch();
			connection.commit();
		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during reading from DB.", ex);
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
		return count;
	}

	public int addNewPatient(Patient patient) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();

			preparedStatement = connection.prepareStatement(sqlAddPatient);
			String id = UUID.randomUUID().toString();
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, patient.getSurname());
			preparedStatement.setString(3, patient.getName());
			preparedStatement.setDate(4, patient.getDateOfBirth(),Calendar.getInstance());
			preparedStatement.setString(5, patient.getEmail());

			insertedRows = preparedStatement.executeUpdate();

		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from  pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during preparing/executing INSERT Statement", ex);
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
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				Date dateOfBirth = resultSet.getDate("birth_date");
				String email = resultSet.getString("email");
				Patient patient = new Patient(id, surname, name, dateOfBirth, email);
				patients.add(patient);
			}
		} catch (ConnectionPoolException ex) {
			log.log(Level.ERROR, "Error during taking connection from pool", ex);
			throw new DaoException("Error during taking connection from pool", ex);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during reading from DB.", ex);
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
	
}
