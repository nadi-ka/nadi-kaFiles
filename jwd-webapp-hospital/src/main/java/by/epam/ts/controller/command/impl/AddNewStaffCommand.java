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
import by.epam.ts.controller.command.access_manager.AccessManager;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddNewStaffCommand implements Command, AccessManager {

	private static final Logger log = LogManager.getLogger(AddNewStaffCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Checking of the user rights;
		boolean adminRights = checkAdminRights(request);
		if (!adminRights) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}
		String specialty = request.getParameter(RequestAtribute.SPECIALTY);
		String surname = request.getParameter(RequestAtribute.SURNAME);
		String name = request.getParameter(RequestAtribute.NAME);
		String email = request.getParameter(RequestAtribute.EMAIL);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		String staffId = null;
		try {

			staffId = userService.addNewStaff(specialty, surname, name, email);

			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_STAFF_DATA_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.ADDED_SUCCESSFULY).setStaffId(staffId).getResultString());

		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewStaff() from  AddNewStaffCommand. Invalid parameters:", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_STAFF_DATA_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).setInvalidParameters(e.getMessage())
					.getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.addNewStaff() from  AddNewStaffCommand. The staff wasn't added", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());

		}

	}

}
