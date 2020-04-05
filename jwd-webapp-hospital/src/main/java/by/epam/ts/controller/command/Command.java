package by.epam.ts.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.impl.LoginCommand;
import by.epam.ts.controller.manager.NavigationManager;

public interface Command {
	
	static final Logger log = LogManager.getLogger(LoginCommand.class);

	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	default void goForward(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        } else {
        	log.log(Level.ERROR, "requestDispatcher==null");
			response.sendRedirect(NavigationManager.getProperty("path.page.error"));
        }
    }

}
