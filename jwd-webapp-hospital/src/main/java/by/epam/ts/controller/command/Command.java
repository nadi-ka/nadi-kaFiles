package by.epam.ts.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.ts.controller.constant_attribute.SessionAtribute;
import by.epam.ts.controller.manager.NavigationManager;

public interface Command {

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

	default String getUserIdFromSession(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String userId = null;
		if (session != null) {
			userId = (String) session.getAttribute(SessionAtribute.USER_ID);
		}
		return userId;
	}

}
