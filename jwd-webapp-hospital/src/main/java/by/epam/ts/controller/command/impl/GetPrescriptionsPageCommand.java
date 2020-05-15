package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.manager.NavigationManager;

public final class GetPrescriptionsPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GetPrescriptionsPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		String message = request.getParameter(RequestAtribute.MESSAGE);

		request.setAttribute(RequestAtribute.PATIENT_ID, patientId);
		request.setAttribute(RequestAtribute.MESSAGE, message);
		String page = NavigationManager.getProperty("path.page.staff.prescriptions");
		goForward(request, response, page);

	}

}
