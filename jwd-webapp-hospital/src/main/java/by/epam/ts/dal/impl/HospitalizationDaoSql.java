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

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.dal.DaoException;
import by.epam.ts.dal.HospitalizationDao;
import by.epam.ts.dal.constant.ColumnNameHolder;
import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.factory.ConnectionPoolFactory;

public class HospitalizationDaoSql implements HospitalizationDao {
	
	private final ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
	private ConnectionPool connectionPool = factory.getConnectionPool();
	
	private static final String sqlAddHospitalization = "INSERT INTO hospitalization (id_patient, entry_date) VALUES (?,?);";
	private static final String sqlFinishHospitalization = "UPDATE hospitalization SET discharge_date = (?) WHERE id_history = (?);";
	private static final String sqlFindHospitalizationsByPatientId = "SELECT * FROM hospitalization WHERE id_patient = (?) ORDER BY entry_date DESC;";
	private static final String sqlFindLastHospitalizationById = "SELECT * FROM hospitalization WHERE id_patient = (?) ORDER BY entry_date DESC LIMIT 1;";

	private static final Logger log = LogManager.getLogger(HospitalizationDaoSql.class);
	
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
				int idHistory = resultSet.getInt(ColumnNameHolder.HOSPITALIZATION_ID_HISTORY);
				Date entryDate = resultSet.getDate(ColumnNameHolder.HOSPITALIZATION_ENTRY_DATE);
				Date dischargeDate = resultSet.getDate(ColumnNameHolder.HOSPITALIZATION_DISCHARGE_DATE);
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
			int idHistory = resultSet.getInt(ColumnNameHolder.HOSPITALIZATION_ID_HISTORY);
			Date entryDate = resultSet.getDate(ColumnNameHolder.HOSPITALIZATION_ENTRY_DATE);
			Date dischargeDate = resultSet.getDate(ColumnNameHolder.HOSPITALIZATION_DISCHARGE_DATE);

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


}
