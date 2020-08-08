package by.epam.ts.dal.pool.impl;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.manager.DBParameter;
import by.epam.ts.dal.pool.manager.ResourceManager;

public final class ConnectionPoolImpl implements ConnectionPool {

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConnectionQueue;

	static final Logger log = LogManager.getLogger(ConnectionPoolImpl.class);

	private String driver;
	private String url;
	private String user;
	private String password;
	private int poolSize;

	public ConnectionPoolImpl() {
		ResourceManager resourceManager = ResourceManager.getInstance();
		this.driver = resourceManager.getValue(DBParameter.DB_DRIVER);
		this.url = resourceManager.getValue(DBParameter.DB_URL);
		this.user = resourceManager.getValue(DBParameter.DB_USER);
		this.password = resourceManager.getValue(DBParameter.DB_PASSWORD);
		try {
			String poolSize = resourceManager.getValue(DBParameter.DB_POOL_SIZE);
			int sizeInt = Integer.parseInt(poolSize);
			this.poolSize = sizeInt;
		} catch (NumberFormatException ex) {
			this.poolSize = 5;
		}
	}

	public void initializePoolData() throws ConnectionPoolException {
		try {
			Class.forName(driver);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			givenAwayConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				InnerConnection innerConnection = new InnerConnection(connection);
				connectionQueue.add(innerConnection);
			}
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Connection wasn't created", ex);
			throw new ConnectionPoolException("Connection wasn't created", ex);
		} catch (ClassNotFoundException ex) {
			log.log(Level.ERROR, "Class of the driver wasn't found", ex);
			throw new ConnectionPoolException("Class of the driver wasn't found", ex);
		}
	}

	public void dispose() {
		clearConnectionQueue();
	}

	private void clearConnectionQueue() {
		try {
			closeConnectionsQueue(connectionQueue);
			closeConnectionsQueue(givenAwayConnectionQueue);
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Error during closing the connection", ex);
		}
	}

	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;
		try {
			connection = connectionQueue.take();
			givenAwayConnectionQueue.add(connection);
		} catch (InterruptedException ex) {
			log.log(Level.ERROR, "Exception connecting to the data source", ex);
			throw new ConnectionPoolException("Exception connecting to the data source", ex);
		}
		return connection;
	}

	public boolean releaseConnection(Connection connection) {
		connectionQueue.add(connection);
		return givenAwayConnectionQueue.remove(connection);
	}

	public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException ex) {
			log.log(Level.ERROR, "ResultSet wasn't closed", ex);
		}
		try {
			statement.close();
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Statement wasn't closed", ex);
		}
		try {
			connection.close();
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Connection wasn't closed", ex);
		}
	}

	public void closeConnection(Statement statement, ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException ex) {
			log.log(Level.ERROR, "ResultSet wasn't closed", ex);
		}
		try {
			statement.close();
		} catch (SQLException ex) {
			log.log(Level.ERROR, "Statement wasn't closed", ex);
		}
	}

	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((InnerConnection) connection).reallyClose();
		}
	}

	private class InnerConnection implements Connection {
		private Connection connection;

		public InnerConnection(Connection connection) throws SQLException {
			this.connection = connection;
			this.connection.setAutoCommit(true);
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		public void close() throws SQLException {
			if (connection.isClosed()) {
				throw new SQLException("Attempting to close closed connection");
			}
		}

		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		public void commit() throws SQLException {
			connection.commit();
		}

		public void rollback() throws SQLException {
			connection.rollback();
		}

		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		public Savepoint setSavepoint(String name) throws SQLException {
			return setSavepoint(name);
		}

		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback(savepoint);
		}

		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);
		}

		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			connection.setClientInfo(properties);
		}

		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return createStruct(typeName, attributes);
		}

		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);
		}

		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);
		}

		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);
		}

		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);

		}
	}

}
