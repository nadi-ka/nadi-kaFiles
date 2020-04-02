package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.manager.NavigationManager;

public class ShowPatientMainPageCommand implements Command{
	
	private static final Logger log = LogManager.getLogger(ShowPatientMainPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(NavigationManager.getProperty("path.page.main"));
		if (requestDispatcher != null) {
			requestDispatcher.forward(request, response);
		} else {
			log.log(Level.ERROR, "requestDispatcher==null");
			response.sendRedirect(NavigationManager.getProperty("path.page.error"));
		}
		
	}

}
