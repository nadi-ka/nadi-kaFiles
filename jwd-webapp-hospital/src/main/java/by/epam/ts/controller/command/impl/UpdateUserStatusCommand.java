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
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class UpdateUserStatusCommand implements Command {

	private static final String ENCODING = "UTF-8";
	private static final Logger log = LogManager.getLogger(UpdateUserStatusCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter(RequestAtribute.USER_STATUS);
		String staffId = request.getParameter(RequestAtribute.STAFF_ID);
		String querySurname = request.getParameter(RequestAtribute.QUERY_SEARCH);
		String querySurnameUTF8 = URLEncoder.encode(querySurname, ENCODING);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			userService.setUserStatus(status, staffId);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SEARCH_STAFF.toString().toLowerCase());
			response.sendRedirect(builder.setQueryString(querySurnameUTF8).getResultString());

		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method execute from UpdateUserStatusCommand", ex);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}
	}
}
