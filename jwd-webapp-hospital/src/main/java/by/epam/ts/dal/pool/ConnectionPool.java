package by.epam.ts.dal.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ConnectionPool {

	void initializePoolData() throws ConnectionPoolException;

	void dispose();

	Connection takeConnection() throws ConnectionPoolException;

	boolean releaseConnection(Connection connection);

	void closeConnection(Connection connection, Statement statement, ResultSet resultSet);

	void closeConnection(Statement statement, ResultSet resultSet);

}
