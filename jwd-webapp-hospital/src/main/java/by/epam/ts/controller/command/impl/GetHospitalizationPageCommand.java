package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetHospitalizationPageCommand implements Command {
	
	private static final Logger log = LogManager.getLogger(GetHospitalizationPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		String message = request.getParameter(RequestAtribute.MESSAGE);
		
		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		Hospitalization lastHospitalization;
	try {
		lastHospitalization = userService.getLastHospitalizationById(patientId);
		request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
		request.setAttribute(RequestAtribute.PATIENT_ID, patientId);
		request.setAttribute(RequestAtribute.MESSAGE, message);
		String page = NavigationManager.getProperty("path.page.staff.hospitalization");
		goForward(request, response, page);
	}catch (ServiceException e) {
		log.log(Level.ERROR,
				"Error when calling userService.getLastHospitalizationById() from GetHospitalizationPageCommand", e);
		request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.TECHNICAL_ERROR);
		response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
				+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
				+ RequestMessage.TECHNICAL_ERROR);
	}

	}

}
