package by.epam.ts.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.Command;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;
import by.epam.ts.servlet.manager.PageManager;
import by.epam.ts.servlet.manager.MessageManager;

public final class SignUpCommand implements Command {

	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String PARAM_NAME_EMAIL = "email";
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		String email = request.getParameter(PARAM_NAME_EMAIL);
		
		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		
		int updatedRows = 0;
		try {
			updatedRows = userService.signUp(email, login, password);
		} catch (ServiceException ex) {
			// log
			String message = MessageManager.getProperty("message.technicalerror");
			request.setAttribute("techninalErrorMessage", message);
			page = PageManager.getProperty("path.page.error");
			return page;
		}

		if (updatedRows == 0) {
			String message = MessageManager.getProperty("message.registrationError");
			request.setAttribute("errorRegistrationDada", message);
			page = PageManager.getProperty("path.page.signUp");
		} else {
			request.setAttribute("user", login);
			page = PageManager.getProperty("path.page.main");
		}

		return page;

	}

}
