package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class UpdatePersonalDataCommand implements Command {

	private static final Logger log = LogManager.getLogger(UpdatePersonalDataCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String surname = request.getParameter(RequestAtribute.SURNAME);
		String name = request.getParameter(RequestAtribute.NAME);
		String newEmail = request.getParameter(RequestAtribute.EMAIL);
		String oldEmail = request.getParameter(RequestAtribute.OLD_EMAIL);
		String staffId = getUserIdFromSession(request);
		
		if ((staffId == null) || staffId.isEmpty()) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_INDEX_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		
		try {	
			userService.setStaffPersonalData(surname, name, newEmail, oldEmail, staffId);
			
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_UPDATE_PERSONAL_DATA_PAGE.toString().toLowerCase());
			
		} catch (ValidationServiceException e) {
			log.log(Level.WARN, "Error when calling execute() from UpdatePersonalDataCommand. Invalid parameters:", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_UPDATE_PERSONAL_DATA_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.ERROR_DATA + "&"
					+ RequestAtribute.INVALID_PARAMETERS + "=" + e.getMessage());
		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling execute() from UpdatePersonalDataCommand. The staff's personal data wasn't updated.",
					e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}
	}
}
