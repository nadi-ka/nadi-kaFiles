package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.User;
import by.epam.ts.bean.role.UserRole;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.constant_attribute.SessionAtribute;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class LoginCommand implements Command {

	private static final Logger log = LogManager.getLogger(LoginCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(RequestAtribute.USER_LOGIN);
		String password = request.getParameter(RequestAtribute.USER_PASSWORD);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		User user = null;

		try {
			user = userService.logIn(login, password);
			if (user.getId() == null) {
				RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
						 CommandEnum.GET_INDEX_PAGE.toString().toLowerCase());
				response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).getResultString());

				return;
			}
			HttpSession session = request.getSession(true);
			UserRole role = user.getRole();
			// Session stores parameters: user's id and user's role;
			session.setAttribute(SessionAtribute.USER_ID, user.getId());
			session.setAttribute(SessionAtribute.USER_ROLE, role);

			if (role == UserRole.DOCTOR || role == UserRole.ADMINISTRATOR || role == UserRole.NURSE) {
				RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
						CommandEnum.GET_STAFF_MAIN_PAGE.toString().toLowerCase());
				response.sendRedirect(builder.getResultString());
				
			} else {
				RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
						CommandEnum.GET_PATIENT_MAIN_PAGE.toString().toLowerCase());
				response.sendRedirect(builder.getResultString());
			}
		} catch (ValidationServiceException ex) {
			log.log(Level.WARN, "Validation error when calling execute() from LoginCommand()", ex);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					 CommandEnum.GET_INDEX_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).getResultString());

		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error when calling execute() from LoginCommand.", ex);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}

}
