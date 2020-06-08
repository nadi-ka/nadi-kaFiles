package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class SearchStaffCommand implements Command {

	private static final Logger log = LogManager.getLogger(SearchStaffCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Checking of the user rights;
		boolean adminRights = checkAdminRights(request, response);
		if (!adminRights) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		String querySurname = request.getParameter(RequestAtribute.QUERY_SEARCH);
		// If query incorrect - show current page again with the message;
		if (querySurname == null || querySurname.isEmpty()) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_STAFF_DATA_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE
					+ "=" + RequestMessage.NOT_FOUND);
			return;
		}
		log.info("Query:" + querySurname);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			List<MedicalStaff> staffList = userService.getUserStaffBySurname(querySurname);
			if (staffList.isEmpty()) {
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT
						+ RequestAtribute.COMMAND + "=" + CommandEnum.GET_STAFF_DATA_PAGE.toString().toLowerCase() + "&"
						+ RequestAtribute.MESSAGE + "=" + RequestMessage.NOT_FOUND);
			} else {
				request.setAttribute(RequestAtribute.STAFF_LIST, staffList);
				request.setAttribute(RequestAtribute.QUERY_SEARCH, querySurname);
				String page = NavigationManager.getProperty("path.page.staff.staff_data");
				goForward(request, response, page);
			}
		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling userService.getStaffBySurname(query) from SearchStaffCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
