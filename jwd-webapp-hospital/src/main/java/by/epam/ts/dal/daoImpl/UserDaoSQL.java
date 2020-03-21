package src.main.java.by.epam.ts.dal.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import src.main.java.by.epam.ts.bean.MedicalStaff;
import src.main.java.by.epam.ts.bean.Patient;
import src.main.java.by.epam.ts.bean.User;
import src.main.java.by.epam.ts.dal.DaoException;
import src.main.java.by.epam.ts.dal.UserDao;
import src.main.java.by.epam.ts.dal.connectionPool.ConnectionPool;
import src.main.java.by.epam.ts.dal.connectionPool.ConnectionPoolException;

public class UserDaoSQL implements UserDao {

	ConnectionPool connectionPool;

	private String sqlAddUserStaff = 
			"INSERT INTO users (id_medical_staff, login, password, role, user_status) VALUES (?,?,?,?,?);";
	private String sqlAddUserPatient = 
			"INSERT INTO users (id_patient, login, password, role, user_status) VALUES (?,?,?,?,?);";
	private String sqlFindStaffByEmail = "SELECT * FROM `medical-staff` WHERE email=(?);";
	private String sqlFindPatientByEmail = "SELECT * FROM patients WHERE email=(?);";
	private String sqlFindUserByLoginPassword = "SELECT * FROM users WHERE " + "login=(?) AND password=(?);";

	public UserDaoSQL() throws DaoException {
		connectionPool = new ConnectionPool();

		try {
			connectionPool.initializePoolData();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during initialisation of connection pool", ex);
		}
	}

	private Connection getConnection() throws DaoException {
		Connection connection = null;
		try {
			connection = connectionPool.takeConnection();
		} catch (ConnectionPoolException ex) {
			throw new DaoException("Error during taking the connection", ex);
		}
		return connection;
	}

	public int addUser(User user, boolean isStaff) throws DaoException {
		int insertedRows = 0;
		Connection connection = getConnection();
		PreparedStatement signUpStaff = null;
		PreparedStatement signUpPatient = null;

		try {
			if (isStaff) {
				signUpStaff = connection.prepareStatement(sqlAddUserStaff);
				signUpStaff.setString(1, user.getId());
				signUpStaff.setString(2, user.getLogin());
				signUpStaff.setString(3, user.getPassword());
				signUpStaff.setInt(4, user.getRole());
				signUpStaff.setBoolean(5, user.isUserStatus());
				insertedRows = signUpStaff.executeUpdate();
			} else {
				signUpPatient = connection.prepareStatement(sqlAddUserPatient);
				signUpPatient.setString(1, user.getId());
				signUpPatient.setString(2, user.getLogin());
				signUpPatient.setString(3, user.getPassword());
				signUpPatient.setInt(4, user.getRole());
				signUpPatient.setBoolean(5, user.isUserStatus());
				insertedRows = signUpPatient.executeUpdate();
			}
		} catch (SQLException ex) {
			throw new DaoException("Error during preparing/executing INSERT Statement", ex);
		} finally {
			if (signUpStaff != null) {
				try {
					signUpStaff.close();
				} catch (SQLException ex) {
					throw new DaoException("Error during closing the statement.", ex);
				}
			} else if (signUpPatient != null) {
				try {
					signUpPatient.close();
				} catch (SQLException ex) {
					throw new DaoException("Error during closing the statement.", ex);
				}
			}
		}
		connectionPool.releaseConnection(connection);

		return insertedRows;
	}

	public MedicalStaff findStaffByEmail(String email) throws DaoException {
		Connection connection = getConnection();
		MedicalStaff medicalStaff = null;
		PreparedStatement findStaffByEmail = null;

		try {
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
			
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (findStaffByEmail != null) {
				try {
					findStaffByEmail.close();
				} catch (SQLException ex) {
					throw new DaoException("Error during closing the statement.", ex);
				}
			}
		}
		connectionPool.releaseConnection(connection);
		return medicalStaff;
	}

	public Patient findPatientByEmail(String email) throws DaoException {
		Connection connection = getConnection();
		Patient patient = null;
		PreparedStatement findPatientByEmail = null;

		try {
			findPatientByEmail = connection.prepareStatement(sqlFindPatientByEmail);
			findPatientByEmail.setString(1, email);
			ResultSet patientResultSet = findPatientByEmail.executeQuery();

			if (!patientResultSet.next()) {
				return patient;
			}

				String id = patientResultSet.getString(1);
				String surname = patientResultSet.getString(2);
				String name = patientResultSet.getString(3);
				int age = patientResultSet.getInt(4);
				Date entryDate = patientResultSet.getDate(5);
				Date dischargeDate = patientResultSet.getDate(6);
				patient = new Patient(id, surname, name, age, entryDate, dischargeDate, email);
			
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (findPatientByEmail != null) {
				try {
					findPatientByEmail.close();
				} catch (SQLException ex) {
					throw new DaoException("Error during closing the statement.", ex);
				}
			}
		}
		connectionPool.releaseConnection(connection);
		return patient;
	}

	public User findUserByLoginPassword(String login, String password) throws DaoException {
		Connection connection = getConnection();
		User user = null;
		PreparedStatement preparedStatement = null;
		ResultSet userResultSet = null;
		try {
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
				
			
		} catch (SQLException ex) {
			throw new DaoException("Error during reading from DB.", ex);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					throw new DaoException("Error during closing the statement.", ex);
				}
			}
		}
		connectionPool.releaseConnection(connection);
		return user;
	}

	public void clearConnection() {
		connectionPool.dispose();
	}
}
