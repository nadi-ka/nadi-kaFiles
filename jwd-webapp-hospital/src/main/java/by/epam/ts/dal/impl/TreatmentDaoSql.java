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
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.treat_status.TreatmentStatus;
import by.epam.ts.bean.treat_type.TreatmentType;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.TreatmentDao;
import by.epam.ts.dal.constant.ColumnNameHolder;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.factory.ConnectionPoolFactory;

public class TreatmentDaoSql implements TreatmentDao {

	private final ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
	private ConnectionPool connectionPool = factory.getConnectionPool();

	private static final String sqlFindTreatmentByPatientId = "SELECT id_appointment, treatment_type, treatment_name, id_assigned_by, `date_begin/holding`, date_finish, consent, surname, name FROM treatment JOIN `medical-staff` ON treatment.id_assigned_by=`medical-staff`.id WHERE id_patient=(?) ORDER BY `date_begin/holding` DESC;";
	private static final String sqlUpdateConsent = "UPDATE treatment SET consent=(?) WHERE id_appointment=(?);";
	private static final String sqlAddPatientTreatment = "INSERT INTO treatment (id_patient, treatment_type, treatment_name, id_assigned_by, `date_begin/holding`, date_finish, consent) VALUES (?,?,?,?,?,?,?);";
	private static final String sqlAddCurrentTreatment = "INSERT INTO `current-treatment` (id_appointment, date, id_performer, status) VALUES (?,?,?,?);";
	private static final String sqlFindTreatmentDuringCurrentHospitalization = "SELECT id_appointment, treatment_type, treatment_name, id_assigned_by, `date_begin/holding`, date_finish, consent, surname, name FROM treatment JOIN `medical-staff` ON treatment.id_assigned_by=`medical-staff`.id WHERE id_patient=(?) AND `date_begin/holding` >= (?);";
	private static final String sqlFindCurrentTreatmentByAppointmentId = "SELECT id_procedure, date, id_performer, status, surname, name FROM `current-treatment` JOIN `medical-staff` ON `current-treatment`.id_performer=`medical-staff`.id WHERE id_appointment=(?) ORDER BY date DESC;";

	private static final Logger log = LogManager.getLogger(TreatmentDaoSql.class);

	public List<Treatment> findPatientsTreatmentById(String id) throws DaoException {
		Connection connection = null;
		Treatment treatment = null;
		List<Treatment> prescriptions = new ArrayList<Treatment>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		log.info("id:" + id);
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(sqlFindTreatmentByPatientId);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst() ) {    
			    log.info("RS empty"); 
			} 

			while (resultSet.next()) {
				int idAppointment = resultSet.getInt(ColumnNameHolder.TREATMENT_ID_APPOINTMENT);
				String treatmentType = resultSet.getString(ColumnNameHolder.TREATMENT_TYPE);
				String treatmentName = resultSet.getString(ColumnNameHolder.TREATMENT_NAME);
				String doctorId = resultSet.getString(ColumnNameHolder.TREATMENT_ID_ASSIGNED);
				Date dateBeggining = resultSet.getDate(ColumnNameHolder.TREATMENT_DATE_BEGINNING);
				Date dateFinishing = resultSet.getDate(ColumnNameHolder.TREATMENT_DATE_FINISHING);
				boolean consent = resultSet.getBoolean(ColumnNameHolder.TREATMENT_CONSENT);
				String doctorSurname = resultSet.getString(ColumnNameHolder.STAFF_SURNAME);
				String doctorName = resultSet.getString(ColumnNameHolder.STAFF_NAME);

				treatment = new Treatment(idAppointment, id, TreatmentType.getTreatmentType(treatmentType),
						treatmentName, doctorId, doctorSurname, doctorName, dateBeggining.toLocalDate(),
						dateFinishing.toLocalDate(), consent);
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
				int idAppointment = resultSet.getInt(ColumnNameHolder.TREATMENT_ID_APPOINTMENT);
				String treatmentType = resultSet.getString(ColumnNameHolder.TREATMENT_TYPE);
				String treatmentName = resultSet.getString(ColumnNameHolder.TREATMENT_NAME);
				String doctorId = resultSet.getString(ColumnNameHolder.TREATMENT_ID_ASSIGNED);
				Date dateBeggining = resultSet.getDate(ColumnNameHolder.TREATMENT_DATE_BEGINNING);
				Date dateFinishing = resultSet.getDate(ColumnNameHolder.TREATMENT_DATE_FINISHING);
				boolean consent = resultSet.getBoolean(ColumnNameHolder.TREATMENT_CONSENT);
				String doctorSurname = resultSet.getString(ColumnNameHolder.STAFF_SURNAME);
				String doctorName = resultSet.getString(ColumnNameHolder.STAFF_NAME);

				treatment = new Treatment(idAppointment, idPatient, TreatmentType.getTreatmentType(treatmentType),
						treatmentName, doctorId, doctorSurname, doctorName, dateBeggining.toLocalDate(),
						dateFinishing.toLocalDate(), consent);
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
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int idProcedure = resultSet.getInt(ColumnNameHolder.CURRENT_ID_PROCEDURE);
				LocalDate datePerforming = resultSet.getDate(ColumnNameHolder.CURRENT_DATE).toLocalDate();
				String idPerformer = resultSet.getString(ColumnNameHolder.CURRENT_ID_PERFORMER);
				TreatmentStatus status = TreatmentStatus
						.getTreatmentStatus(resultSet.getString(ColumnNameHolder.CURRENT_STATUS));
				String staffSurname = resultSet.getString(ColumnNameHolder.STAFF_SURNAME);
				String staffName = resultSet.getString(ColumnNameHolder.STAFF_NAME);

				treatment = new CurrentTreatment(idProcedure, idAppointment, datePerforming, idPerformer, staffSurname,
						staffName, status);
				treatmentList.add(treatment);
			}
			log.info(treatmentList.toString());
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

}
