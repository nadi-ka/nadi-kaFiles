package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.constant_attribute.SessionAtribute;

public class ChangeLanguageCommand implements Command {

	private static final Logger log = LogManager.getLogger(ChangeLanguageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			log.log(Level.ERROR, "When calling getSession() from ChangeLanguageCommand, session == null;");
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
			return;
		}

		String local = request.getParameter(SessionAtribute.LOCALIZATION);
		session.setAttribute(SessionAtribute.LOCALIZATION, local);

		String queryString = request.getParameter(RequestAtribute.QUERY_STRING);
		
		if (queryString == null || queryString.isEmpty()) {
			String redirectCommand = request.getParameter(RequestAtribute.REDIRECT_COMMAND);
			String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
			String message = request.getParameter(RequestAtribute.MESSAGE);

			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + redirectCommand + "&" + RequestAtribute.PATIENT_ID + "=" + patientId + "&"
					+ RequestAtribute.MESSAGE + "=" + message);
		} else {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + queryString);
		}

	}
}
