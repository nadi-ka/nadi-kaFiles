package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.SessionAtribute;

public class ChangeLanguageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String local = request.getParameter(SessionAtribute.LOCALIZATION);
		session.setAttribute(SessionAtribute.LOCALIZATION, local);

		String queryString = (String) request.getParameter(RequestAtribute.QUERY_STRING);
		if (queryString == null || queryString.isEmpty()) {
			String redirectCommand = (String) request.getParameter(RequestAtribute.REDIRECT_COMMAND);
			String patientId = (String) request.getParameter(RequestAtribute.PATIENT_ID);
			String message = (String) request.getParameter(RequestAtribute.MESSAGE);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + redirectCommand + "&" + RequestAtribute.PATIENT_ID + "=" + patientId + "&"
					+ RequestAtribute.MESSAGE + "=" + message);
		} else {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + queryString);
		}

	}
}
