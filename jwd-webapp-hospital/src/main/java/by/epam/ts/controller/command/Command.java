package by.epam.ts.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.role.UserRole;
import by.epam.ts.controller.constant_attribute.SessionAtribute;
import by.epam.ts.controller.manager.NavigationManager;

public interface Command {

	static final Logger log = LogManager.getLogger(Command.class);

	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	default void goForward(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
		if (requestDispatcher != null) {
			requestDispatcher.forward(request, response);
		} else {
			response.sendRedirect(NavigationManager.getProperty("path.page.error"));
		}
	}

	default boolean checkStaffRights(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		UserRole userRole = (UserRole) session.getAttribute(SessionAtribute.USER_ROLE);
		if (userRole == null
				|| (userRole != null && (userRole != UserRole.ADMINISTRATOR && userRole != UserRole.STAFF))) {
			return false;
		}
		return true;
	}
	
	default boolean checkAdminRights(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		UserRole userRole = (UserRole) session.getAttribute(SessionAtribute.USER_ROLE);
		if (userRole == null
				|| (userRole != null && (userRole != UserRole.ADMINISTRATOR))) {
			return false;
		}
		return true;
	}

}
