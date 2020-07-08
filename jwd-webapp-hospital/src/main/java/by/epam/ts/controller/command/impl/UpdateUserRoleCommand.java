package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

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
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class UpdateUserRoleCommand implements Command {

	private static final String ENCODING = "UTF-8";
	private static final Logger log = LogManager.getLogger(UpdateUserRoleCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String role = request.getParameter(RequestAtribute.USER_ROLE);
		String staffId = request.getParameter(RequestAtribute.STAFF_ID);
		String querySurname = request.getParameter(RequestAtribute.QUERY_SEARCH);
		String querySurnameUTF8 = URLEncoder.encode(querySurname, ENCODING);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			userService.setStaffUserRole(role, staffId);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SEARCH_STAFF.toString().toLowerCase() + "&" + RequestAtribute.QUERY_SEARCH + "="
					+ querySurnameUTF8);
			
		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method execute from UpdateUserRoleCommand", ex);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}
	}

}
