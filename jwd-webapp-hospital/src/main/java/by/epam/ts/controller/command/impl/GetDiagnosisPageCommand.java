package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Diagnosis;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetDiagnosisPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GetDiagnosisPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		String message = request.getParameter(RequestAtribute.MESSAGE);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		List<Diagnosis> diagnosisList;

		try {
			diagnosisList = userService.getAllDiagnosisSorted();
			// adding current patient's id to the request;
			request.setAttribute(RequestAtribute.PATIENT_ID, patientId);
			request.setAttribute(RequestAtribute.LIST_DIAGNOSIS, diagnosisList);
			request.setAttribute(RequestAtribute.MESSAGE, message);
			String page = NavigationManager.getProperty("path.page.staff.diagnosis");
			goForward(request, response, page);
		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.getAllDiagnosisSorted() from GetAllPossibleDiagnosisCommand", e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.TECHNICAL_ERROR);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}
	}
}
