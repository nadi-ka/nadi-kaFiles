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
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.SessionAtribute;
import by.epam.ts.controller.manager.MessageManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class LoginCommand implements Command {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final int ROLE_PATIENT = 3;
	
	private static final Logger log = LogManager.getLogger(LoginCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		User user = null;

		try {
			user = userService.logIn(login, password);
			if (user != null) {
				HttpSession session = request.getSession(true);
				// User stores parameters: id, login, role, userStatus;
				session.setAttribute(SessionAtribute.USER_ID, user.getId());
				session.setAttribute(SessionAtribute.USER_LOGIN, user.getLogin());
				session.setAttribute(SessionAtribute.USER_ROLE, user.getRole());
				session.setAttribute(SessionAtribute.USER_STATUS, user.isUserStatus());
				
				if (user.getRole() == ROLE_PATIENT) {
					response.sendRedirect(request.getContextPath() + "/register?command=show_patient_main_page");
				}
				else {
					response.sendRedirect(request.getContextPath() + "/register?command=show_staff_main_page");
				}
				
			} else {
				request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.ERROR_DATA);
				response.sendRedirect(request.getContextPath() + "/register?command=show_index_page&message=error_data");
			}
			
		} catch (ValidationServiceException ex) {
			log.log(Level.INFO, "Validation error during calling method logIn()", ex);
			request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.ERROR_DATA);
			response.sendRedirect(request.getContextPath() + "/register?command=show_index_page&message=error_data");

		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method logIn() from LoginCommand.", ex);
			request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.TECHNICAL_ERROR);
			response.sendRedirect(
					request.getContextPath() + "/register?command=show_error_page&message=technical_error");
		}

	}

}
