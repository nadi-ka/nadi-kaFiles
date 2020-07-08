package by.epam.ts.dal.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.Hospitalization;
import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.bean.role.UserRole;
import by.epam.ts.bean.specialty.Specialty;
import by.epam.ts.bean.treat_status.TreatmentStatus;
import by.epam.ts.bean.treat_type.TreatmentType;
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
	private static final String sqlFindUserByLogin = "SELECT * FROM users WHERE login=(?);";
	private static final String sqlFindTreatmentByPatientId = "SELECT id_appointment, treatment_type, treatment_name, id_assigned_by, `date_begin/holding`, date_finish, consent, surname, name FROM treatment JOIN `medical-staff` ON treatment.id_assigned_by=`medical-staff`.id WHERE id_patient=(?) ORDER BY `date_begin/holding` DESC;";
	private static final String sqlFindDiagnosisByPatientId = "SELECT code_diagnosis, is_primary, setting_date, diagnosis.name FROM `id-m2m-code` JOIN diagnosis ON `id-m2m-code`.code_diagnosis=diagnosis.code WHERE id_patient=(?);";
	private static final String sqlFindLogin = "SELECT login FROM users WHERE login=(?);";
	private static final String sqlUpdateConsent = "UPDATE treatment SET consent=(?) WHERE id_appointment=(?);";
	private static final String sqlAddPatient = "INSERT INTO patients VALUES (?,?,?,?,?);";
	private static final String sqlFindPatientBySurname = "SELECT * FROM patients WHERE surname=(?);";
	private static final String sqlFindPatientById = "SELECT * FROM patients WHERE id=(?);";
	private static final String sqlGetAllFromDiagnosis = "SELECT * FROM diagnosis;";
	private static final String sqlAddDiagnosis = "INSERT INTO diagnosis VALUES (?,?,?);";
	private static final String sqlAddPatientsDiagnosis = "INSERT INTO `id-m2m-code` VALUES (?,?,?,?);";
	private static final String sqlAddPatientTreatment = "INSERT INTO treatment (id_patient, treatment_type, treatment_name, id_assigned_by, `date_begin/holding`, date_finish, consent) VALUES (?,?,?,?,?,?,?);";
	private static final String sqlAddNewStaff = "INSERT INTO `medical-staff` VALUES (?,?,?,?,?);";
	private static final String sqlFindStaffById = "SELECT * FROM `medical-staff` WHERE id=(?);";
	private static final String sqlFindUserStaffBySurname = "SELECT `medical-staff`.*, users.role, users.user_status FROM `medical-staff` JOIN users ON `medical-staff`.id=users.id_medical_staff WHERE surname=(?);";
	private static final String sqlAddHospitalization = "INSERT INTO hospitalization (id_patient, entry_date) VALUES (?,?);";
	private static final String sqlFinishHospitalization = "UPDATE hospitalization SET discharge_date = (?) WHERE id_history = (?);";
	private static final String sqlFindHospitalizationsByPatientId = "SELECT * FROM hospitalization WHERE id_patient = (?) ORDER BY entry_date DESC;";
	private static final String sqlFindLastHospitalizationById = "SELECT * FROM hospitalization WHERE id_patient = (?) ORDER BY entry_date DESC LIMIT 1;";
	private static final String sqlFindCurrentDiagnosisById = "SELECT code_diagnosis, is_primary, setting_date, diagnosis.name FROM `id-m2m-code` JOIN diagnosis ON `id-m2m-code`.code_diagnosis=diagnosis.code WHERE id_patient=(?) AND setting_date >= (?);";
	private static final String sqlAddCurrentTreatment = "INSERT INTO `current-treatment` (id_appointment, date, id_performer, status) VALUES (?,?,?,?);";
	private static final String sqlFindCurrentTreatmentByAppointmentId = "SELECT id_procedure, date, id_performer, status, surname, name FROM `current-treatment` JOIN `medical-staff` ON `current-treatment`.id_performer=`medical-staff`.id WHERE id_appointment=(?) ORDER BY date DESC;";
	private static final String sqlFindTreatmentDuringCurrentHospitalization = "SELECT id_appointment, treatment_type, treatment_name, id_assigned_by, `date_begin/holding`, date_finish, consent, surname, name FROM treatment JOIN `medical-staff` ON treatment.id_assigned_by=`medical-staff`.id WHERE id_patient=(?) AND `date_begin/holding` >= (?);";
	private static final String sqlFindDiagnosisByIdAndDate = "SELECT code_diagnosis, diagnosis.name, diagnosis.bed_days FROM `id-m2m-code` JOIN diagnosis ON `id-m2m-code`.code_diagnosis=diagnosis.code WHERE id_patient=(?) AND is_primary=true AND setting_date >= (?);";
	private static final String sqlUpdateUserRole = "UPDATE users SET role = (?) WHERE id_medical_staff = (?);";
	private static final String sqlUpdateUserStatus = "UPDATE users SET user_status = (?) WHERE id_medical_staff = (?);";
	private static final String sqlUpdateStaffPersonalData = "UPDATE `medical-staff` SET surname = (?), name = (?), email = (?) WHERE id = (?);";
	private static final String sqlUpdatePatientPersonalData = "UPDATE patients SET surname = (?), name = (?), birth_date = (?), email = (?) WHERE id = (?);";

	private static final Logger log = LogManager.getLogger(UserDaoSQL.class);

	public UserDaoSQL(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

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
			String id = staffResultSet.getString(1);
			String specialtyString = staffResultSet.getString(2);
			Specialty specialty = Specialty.getSpecialty(specialtyString);
			String surname = staffResultSet.getString(3);
			String name = staffResultSet.getString(4);
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
			String id = patientResultSet.getString("id");
			String surname = patientResultSet.getString("surname");
			String name = patientResultSet.getString("name");
			Date dateOfBirth = patientResultSet.getDate("birth_date");
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
			int role = userResultSet.getInt("role");
			UserRole userRole = UserRole.getUserRole(role);
			switch (userRole) {
			case PATIENT:
				idUser = userResultSet.getString("id_patient");
				break;
			default:
				idUser = userResultSet.getString("id_medical_staff");
			}
			String hashedPassword = userResultSet.getString("password");
			boolean userStatus = userResultSet.getBoolean("user_status");

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
				String doctorId = resultSet.getString("id_assigned_by");
				Date dateBeggining = resultSet.getDate("date_begin/holding");
				Date dateFinishing = resultSet.getDate("date_finish");
				boolean consent = resultSet.getBoolean("consent");
				String doctorSurname = resultSet.getString("surname");
				String doctorName = resultSet.getString("name");

				treatment = new Treatment(idAppointment, id, TreatmentType.getTreatmentType(treatmentType), treatmentName, doctorId, doctorSurname,
						doctorName, dateBeggining.toLocalDate(), dateFinishing.toLocalDate(), consent);
				prescriptions.add(treatment);
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
		return prescriptions;
	}

	public List<PatientDiagnosis> findPatientsDiagnosisById(String id) throws DaoException {
		Connection connection = null;
		PatientDiagnosis patientDiagnosis = null;
		List<PatientDiagnosis> diagnosisList = new ArrayList<PatientDiagnosis>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindDiagnosisByPatientId);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String codeDiagnosis = resultSet.getString("code_diagnosis");
				String nameDiagnosis = resultSet.getString("name");
				boolean isPrimary = resultSet.getBoolean("is_primary");
				Date settingDate = resultSet.getDate("setting_date");

				patientDiagnosis = new PatientDiagnosis(id, codeDiagnosis, isPrimary, settingDate.toLocalDate(),
						nameDiagnosis);
				diagnosisList.add(patientDiagnosis);
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
		return diagnosisList;
	}

	public List<PatientDiagnosis> findCurrentDiagnosisById(String id, LocalDate entryDate) throws DaoException {
		Connection connection = null;
		PatientDiagnosis patientDiagnosis = null;
		List<PatientDiagnosis> diagnosisList = new ArrayList<PatientDiagnosis>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindCurrentDiagnosisById);
			preparedStatement.setString(1, id);
			preparedStatement.setDate(2, Date.valueOf(entryDate), Calendar.getInstance());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String codeDiagnosis = resultSet.getString("code_diagnosis");
				String nameDiagnosis = resultSet.getString("name");
				boolean isPrimary = resultSet.getBoolean("is_primary");
				Date setDate = resultSet.getDate("setting_date");

				patientDiagnosis = new PatientDiagnosis(id, codeDiagnosis, isPrimary, setDate.toLocalDate(),
						nameDiagnosis);
				diagnosisList.add(patientDiagnosis);
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
		return diagnosisList;
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

	public int updateConsent(int idAppointment, boolean consent) throws DaoException {
		int count;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlUpdateConsent);
			preparedStatement.setBoolean(1, consent);
			preparedStatement.setInt(2, idAppointment);
			count = preparedStatement.executeUpdate();
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
		return count;
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
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				Date dateOfBirth = resultSet.getDate("birth_date");
				String email = resultSet.getString("email");
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
			String surname = resultSet.getString("surname");
			String name = resultSet.getString("name");
			Date dateOfBirth = resultSet.getDate("birth_date");
			String email = resultSet.getString("email");
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

	public List<Diagnosis> readAllDiagnosis() throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Diagnosis> diagnosisList = new ArrayList<Diagnosis>();

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlGetAllFromDiagnosis);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String codeByICD = resultSet.getString("code");
				String diseaseName = resultSet.getString("name");
				int averageBedDays = resultSet.getInt("bed_days");
				Diagnosis diagnosis = new Diagnosis(codeByICD, diseaseName, averageBedDays);
				diagnosisList.add(diagnosis);
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
		return diagnosisList;
	}

	public int createNewDiagnosis(Diagnosis diagnosis) throws DaoException {
		int insertedRows = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlAddDiagnosis);
			preparedStatement.setString(1, diagnosis.getCodeByICD());
			preparedStatement.setString(2, diagnosis.getDiseaseName());
			int averageBedDays = diagnosis.getAverageBedDays();
			if (averageBedDays != 0) {
				preparedStatement.setInt(3, averageBedDays);
			} else {
				preparedStatement.setInt(3, 0);
			}
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

	public int[] createPatientDiagnosis(List<PatientDiagnosis> diagnosisList) throws DaoException {
		int[] insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();

			preparedStatement = connection.prepareStatement(sqlAddPatientsDiagnosis);
			connection.setAutoCommit(false);
			for (PatientDiagnosis diagnosis : diagnosisList) {
				preparedStatement.setString(1, diagnosis.getIdPatient());
				preparedStatement.setString(2, diagnosis.getCodeByICD());
				preparedStatement.setBoolean(3, diagnosis.isPrimary());
				preparedStatement.setDate(4, Date.valueOf(diagnosis.getSettingDate()), Calendar.getInstance());
				preparedStatement.addBatch();
			}

			insertedRows = preparedStatement.executeBatch();
			connection.commit();

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

	public int createPatientTreatment(Treatment treatment) throws DaoException {

		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlAddPatientTreatment);

			preparedStatement.setString(1, treatment.getIdPatient());
			preparedStatement.setString(2, treatment.getTreatmentType().getTypeValue());
			preparedStatement.setString(3, treatment.getTreatmentName());
			preparedStatement.setString(4, treatment.getDoctorId());
			preparedStatement.setDate(5, Date.valueOf(treatment.getDateBeginning()), Calendar.getInstance());
			preparedStatement.setDate(6, Date.valueOf(treatment.getDateFinishing()), Calendar.getInstance());
			preparedStatement.setBoolean(7, treatment.isConsent());
			preparedStatement.addBatch();

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
			String specialty = resultSet.getString("specialty");
			String surname = resultSet.getString("surname");
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
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
				String id = resultSet.getString("id");
				String specialty = resultSet.getString("specialty");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				int role = resultSet.getInt("role");
				System.out.println("intRole:" + role);
				System.out.println("UserRole:" + UserRole.getUserRole(role));
				boolean userStatus = resultSet.getBoolean("user_status");
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

	public int createNewHospitalization(Hospitalization hospitalization) throws DaoException {
		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlAddHospitalization);
			preparedStatement.setString(1, hospitalization.getIdPatient());
			preparedStatement.setDate(2, Date.valueOf(hospitalization.getEntryDate()), Calendar.getInstance());

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

	public int updateDischargeDate(LocalDate dischargeDate, int idHystory) throws DaoException {
		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFinishHospitalization);
			preparedStatement.setDate(1, Date.valueOf(dischargeDate), Calendar.getInstance());
			preparedStatement.setInt(2, idHystory);

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

	public List<Hospitalization> findHospitalizationsById(String id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Hospitalization> hospitalizations = new ArrayList<Hospitalization>();

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindHospitalizationsByPatientId);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int idHistory = resultSet.getInt("id_history");
				Date entryDate = resultSet.getDate("entry_date");
				Date dischargeDate = resultSet.getDate("discharge_date");
				// As the field discharge_date is allowed to be null, it's necessary to check
				// coming value;
				LocalDate endDate = ((dischargeDate == null) ? null : dischargeDate.toLocalDate());
				Hospitalization hospitalization = new Hospitalization(idHistory, id, entryDate.toLocalDate(), endDate);
				hospitalizations.add(hospitalization);
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
		return hospitalizations;
	}

	public Hospitalization findLastHospitalizationById(String id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Hospitalization hospitalization = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindLastHospitalizationById);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return hospitalization;
			}
			int idHistory = resultSet.getInt("id_history");
			Date entryDate = resultSet.getDate("entry_date");
			Date dischargeDate = resultSet.getDate("discharge_date");

			// As the field discharge_date is allowed to be null, it's necessary to check
			// coming value;
			LocalDate endDate = ((dischargeDate == null) ? null : dischargeDate.toLocalDate());
			hospitalization = new Hospitalization(idHistory, id, entryDate.toLocalDate(), endDate);
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
		return hospitalization;
	}

	public int createCurrentTreatment(CurrentTreatment treatment) throws DaoException {
		int insertedRows;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlAddCurrentTreatment);
			preparedStatement.setInt(1, treatment.getIdAppointment());
			preparedStatement.setDate(2, Date.valueOf(treatment.getDatePerforming()), Calendar.getInstance());
			preparedStatement.setString(3, treatment.getIdPerformer());
			preparedStatement.setString(4, treatment.getStatus().getStatusValue());

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

	// search all prescribed treatment from the last hospitalization's date;
	public List<Treatment> findCurrentHospitalizationTreatment(String idPatient, LocalDate entryDate)
			throws DaoException {
		Connection connection = null;
		Treatment treatment = null;
		List<Treatment> prescriptions = new ArrayList<Treatment>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindTreatmentDuringCurrentHospitalization);
			preparedStatement.setString(1, idPatient);
			preparedStatement.setDate(2, Date.valueOf(entryDate), Calendar.getInstance());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int idAppointment = resultSet.getInt("id_appointment");
				String treatmentType = resultSet.getString("treatment_type");
				String treatmentName = resultSet.getString("treatment_name");
				String doctorId = resultSet.getString("id_assigned_by");
				Date dateBeggining = resultSet.getDate("date_begin/holding");
				Date dateFinishing = resultSet.getDate("date_finish");
				boolean consent = resultSet.getBoolean("consent");
				String doctorSurname = resultSet.getString("surname");
				String doctorName = resultSet.getString("name");

				treatment = new Treatment(idAppointment, idPatient, TreatmentType.getTreatmentType(treatmentType), treatmentName, doctorId,
						doctorSurname, doctorName, dateBeggining.toLocalDate(), dateFinishing.toLocalDate(), consent);
				prescriptions.add(treatment);
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
		return prescriptions;
	}

	public List<CurrentTreatment> findCurrentTreatmentByAppointmentId(int idAppointment) throws DaoException {
		Connection connection = null;
		CurrentTreatment treatment = null;
		List<CurrentTreatment> treatmentList = new ArrayList<CurrentTreatment>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindCurrentTreatmentByAppointmentId);
			preparedStatement.setInt(1, idAppointment);
			;
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int idProcedure = resultSet.getInt("id_procedure");
				LocalDate datePerforming = resultSet.getDate("date").toLocalDate();
				String idPerformer = resultSet.getString("id_performer");
				TreatmentStatus status = TreatmentStatus.getTreatmentStatus(resultSet.getString("status"));
				String staffSurname = resultSet.getString("surname");
				String staffName = resultSet.getString("name");

				treatment = new CurrentTreatment(idProcedure, idAppointment, datePerforming, idPerformer, staffSurname,
						staffName, status);
				treatmentList.add(treatment);
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
		return treatmentList;
	}

	public List<Diagnosis> findDiagnosisByIdAndDate(String id, LocalDate hospitalizationDate) throws DaoException {
		Connection connection = null;
		Diagnosis diagnosis = null;
		List<Diagnosis> diagnosisList = new ArrayList<Diagnosis>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindDiagnosisByIdAndDate);
			preparedStatement.setString(1, id);
			preparedStatement.setDate(2, Date.valueOf(hospitalizationDate), Calendar.getInstance());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String codeDiagnosis = resultSet.getString("code_diagnosis");
				String nameDiagnosis = resultSet.getString("name");
				int bedDays = resultSet.getInt("bed_days");

				diagnosis = new Diagnosis(codeDiagnosis, nameDiagnosis, bedDays);
				diagnosisList.add(diagnosis);
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
		return diagnosisList;
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
