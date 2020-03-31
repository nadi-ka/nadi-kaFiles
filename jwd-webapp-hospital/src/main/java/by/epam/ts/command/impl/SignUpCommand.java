package by.epam.ts.command.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.command.Command;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;
import by.epam.ts.servlet.manager.NavigationManager;
import by.epam.ts.servlet.RegisterController;
import by.epam.ts.servlet.manager.MessageManager;

public final class SignUpCommand implements Command {

	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String PARAM_NAME_EMAIL = "email";
	private static final Logger log = LogManager.getLogger(RegisterController.class);
	
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
			log.log(Level.ERROR, "Error during calling method signUp() from SignUpCommand", ex);
			page = NavigationManager.getProperty("path.page.error");
			return page;
		}

		if (updatedRows == 0) {
			String message = MessageManager.getProperty("local.signup.errordata");
			request.setAttribute("errordata", message);
			page = NavigationManager.getProperty("path.page.signUp");
			return page;
		} 
			request.setAttribute("user", login);
			String message = MessageManager.getProperty("local.login.successfully.registr");
			request.setAttribute("registrsuccess", message);
			page = NavigationManager.getProperty("path.page.login");

		return page;

	}

}
