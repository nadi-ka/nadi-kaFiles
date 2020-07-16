package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.User;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.mailer.Mailer;
import by.epam.ts.controller.command.util.mailer.MailerException;
import by.epam.ts.controller.constant_attribute.MailAttributes;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
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

		User user;
		try {
			user = userService.signUp(email, login, password);

			if (user.getId() == null) {
				// The person with given e-mail was found in base, but wasn't registered as
				// user;
				log.log(Level.ERROR,
						"Error when calling method execute() from SignUpCommand. The user wasn't registered.");
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT
						+ RequestAtribute.COMMAND + "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&"
						+ RequestAtribute.MESSAGE + "=" + RequestMessage.TECHNICAL_ERROR);
			}
			// Send letter about successful registration to the given patient's e-mail;
			Mailer mailer = new Mailer();
			mailer.send(MailAttributes.LETTER_SUBJECT, MailAttributes.LETTER_BODY_SUCCESS_REGISTER,
					MailAttributes.TEMPORARY_EMAIL_FOR_CHECK);

			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_INDEX_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.SUCCESSFUL_REGISTRATION);

		} catch (ValidationServiceException e) {
			log.log(Level.WARN, "Validation error when calling method execute() from SignUpCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_SIGNUP_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ERROR_DATA + "&" + RequestAtribute.INVALID_PARAMETERS + "=" + e.getMessage());
		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling method execute() from SignUpCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		} catch (MailerException e) {
			log.log(Level.WARN, "Error when calling send()from SignUpCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_INDEX_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.SUCCESSFUL_REGISTRATION);
		}

	}

}
