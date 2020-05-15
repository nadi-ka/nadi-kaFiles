package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class SignUpCommand implements Command {

	private static final Logger log = LogManager.getLogger(SignUpCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter(RequestAtribute.USER_LOGIN);
		String password = request.getParameter(RequestAtribute.USER_PASSWORD);
		String email = request.getParameter(RequestAtribute.EMAIL);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();

		int updatedRows = 0;
		try {
			updatedRows = userService.signUp(email, login, password);
			
			if (updatedRows != 0) {
				request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.SUCCESSFUL_REGISTRATION);
				response.sendRedirect(request.getContextPath() + "/register?command=show_index_page&message=successful_registration");

			} else {
				/* update rows == null means, that user with given e-mail was found neither in
				 staff- nor in patient-table;
				 */
				request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.ERROR_DATA);
				response.sendRedirect(request.getContextPath() + "/register?command=show_signup_page&message=error_data");
			}
			
		} catch (ValidationServiceException ex) {
			log.log(Level.INFO, "Validation error during calling method signUp()", ex);
			request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.ERROR_DATA);
			response.sendRedirect(request.getContextPath() + "/register?command=show_signup_page&message=error_data");
		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method signUp() from SignUpCommand", ex);
			request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.TECHNICAL_ERROR);
			response.sendRedirect(request.getContextPath() + "/register?command=show_error_page&message=technical_error");
		}

	}

}
