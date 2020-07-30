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

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.DiagnosisDao;
import by.epam.ts.dal.constant.ColumnNameHolder;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.factory.ConnectionPoolFactory;

public class DiagnosisDaoSql implements DiagnosisDao {

	private final ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
	private ConnectionPool connectionPool = factory.getConnectionPool();

	private static final String sqlFindDiagnosisByPatientId = "SELECT code_diagnosis, is_primary, setting_date, diagnosis.name FROM `id-m2m-code` JOIN diagnosis ON `id-m2m-code`.code_diagnosis=diagnosis.code WHERE id_patient=(?);";
	private static final String sqlGetAllFromDiagnosis = "SELECT * FROM diagnosis;";
	private static final String sqlAddDiagnosis = "INSERT INTO diagnosis VALUES (?,?,?);";
	private static final String sqlAddPatientsDiagnosis = "INSERT INTO `id-m2m-code` VALUES (?,?,?,?);";
	private static final String sqlFindCurrentDiagnosisById = "SELECT code_diagnosis, is_primary, setting_date, diagnosis.name FROM `id-m2m-code` JOIN diagnosis ON `id-m2m-code`.code_diagnosis=diagnosis.code WHERE id_patient=(?) AND setting_date >= (?);";
	private static final String sqlFindDiagnosisByIdAndDate = "SELECT code_diagnosis, diagnosis.name, diagnosis.bed_days FROM `id-m2m-code` JOIN diagnosis ON `id-m2m-code`.code_diagnosis=diagnosis.code WHERE id_patient=(?) AND is_primary=true AND setting_date >= (?);";

	private static final Logger log = LogManager.getLogger(DiagnosisDaoSql.class);

	/*
	 * Find the list of primary and secondary diagnosis of the concrete person for
	 * the period of all hospitalizations; If diagnosis is absent or id wasn't found
	 * the function returns empty list;
	 */
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
				String codeDiagnosis = resultSet.getString(ColumnNameHolder.M2M_CODE_DIAGNOSIS);
				String nameDiagnosis = resultSet.getString(ColumnNameHolder.DIAGNOSIS_NAME);
				boolean isPrimary = resultSet.getBoolean(ColumnNameHolder.M2M_PRIMARY);
				Date settingDate = resultSet.getDate(ColumnNameHolder.M2M_SETTING_DATE);

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
				String codeDiagnosis = resultSet.getString(ColumnNameHolder.M2M_CODE_DIAGNOSIS);
				String nameDiagnosis = resultSet.getString(ColumnNameHolder.DIAGNOSIS_NAME);
				boolean isPrimary = resultSet.getBoolean(ColumnNameHolder.M2M_PRIMARY);
				Date setDate = resultSet.getDate(ColumnNameHolder.M2M_SETTING_DATE);

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

	/*
	 * Add new diagnosis to the 'choose from' list which contains all possible
	 * ophthalmological diagnosis;
	 */
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

	/*
	 * Get the list of all possible diagnosis;
	 */
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
				String codeByICD = resultSet.getString(ColumnNameHolder.DIAGNOSIS_CODE);
				String diseaseName = resultSet.getString(ColumnNameHolder.DIAGNOSIS_NAME);
				int averageBedDays = resultSet.getInt(ColumnNameHolder.DIAGNOSIS_BED_DAYS);
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

	/*
	 * Add the list of the concrete patient's diagnosis;
	 */
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

	/*
	 * This method is used to calculate the expected period of current patient's
	 * hospitalization; 
	 * It returns the list of short patient's diagnosis including
	 * average number of bed days by the primary disease;
	 */
	public List<Diagnosis> findShortDiagnosisByIdAndDate(String id, LocalDate hospitalizationDate) throws DaoException {
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
				String codeDiagnosis = resultSet.getString(ColumnNameHolder.M2M_CODE_DIAGNOSIS);
				String nameDiagnosis = resultSet.getString(ColumnNameHolder.DIAGNOSIS_NAME);
				int bedDays = resultSet.getInt(ColumnNameHolder.DIAGNOSIS_BED_DAYS);

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
	
	public static void main(String[] args) {
		
		ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
		ConnectionPool connectionPool = factory.getConnectionPool();

		try {
			connectionPool.initializePoolData();
			DiagnosisDao diagnosisDao = new DiagnosisDaoSql();
			
			List<Diagnosis> list = diagnosisDao.findShortDiagnosisByIdAndDate("e4a4baa0-25a5-4b60-9856-b55ec84d8c88", LocalDate.parse("2019-07-23"));
			System.out.println(list);
			
		} catch (ConnectionPoolException e) {
			e.printStackTrace();

		} catch (DaoException e) {
			e.printStackTrace();
		} finally {
			connectionPool.dispose();
		}
	}

}
