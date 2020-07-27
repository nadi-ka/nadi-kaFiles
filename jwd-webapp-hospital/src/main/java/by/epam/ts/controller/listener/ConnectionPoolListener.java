package by.epam.ts.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.dal.pool.ConnectionPool;
import by.epam.ts.dal.pool.ConnectionPoolException;
import by.epam.ts.dal.pool.factory.ConnectionPoolFactory;

public class ConnectionPoolListener implements ServletContextListener {

	static final Logger log = LogManager.getLogger(ConnectionPoolListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
		ConnectionPool pool = factory.getConnectionPool();
		try {
			pool.initializePoolData();
		} catch (ConnectionPoolException e) {
			log.log(Level.ERROR, "ConnectionPool cannot be initialized", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
		ConnectionPool pool = factory.getConnectionPool();
		pool.dispose();
	}

}
