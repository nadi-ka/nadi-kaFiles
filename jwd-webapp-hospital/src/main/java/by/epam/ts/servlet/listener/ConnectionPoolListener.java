package by.epam.ts.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.epam.ts.dal.connectionPool.ConnectionPool;
import by.epam.ts.dal.connectionPool.ConnectionPoolException;

public class ConnectionPoolListener implements ServletContextListener {
	
	private ConnectionPool connectionPool;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		connectionPool = new ConnectionPool();
		try {
		connectionPool.initializePoolData();
		}catch (ConnectionPoolException e) {
			// log
		}
		context.setAttribute("connectionPool", connectionPool);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context= sce.getServletContext();
		connectionPool.dispose();
		context.removeAttribute("connectionPool");
	}

}
