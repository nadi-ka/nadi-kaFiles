package by.epam.ts.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.User;
import by.epam.ts.command.Command;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;
import by.epam.ts.servlet.manager.NavigationManager;
import by.epam.ts.servlet.RegisterController;
import by.epam.ts.servlet.manager.MessageManager;

public final class LoginCommand implements Command {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final Logger log = LogManager.getLogger(RegisterController.class);

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		User user = null;

		try {
			user = userService.logIn(login, password);
		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method logIn() from LoginCommand.", ex);
			page = NavigationManager.getProperty("path.page.error");
			return page;
		}

		if (user == null) {
			String message = MessageManager.getProperty("local.login.errordata");
			request.setAttribute("errordata", message);
			page = NavigationManager.getProperty("path.page.login");
			return page;
		}
		HttpSession session = request.getSession(true);

		// User stores parameters: id, login, role, userStatus;
		session.setAttribute("userData", user);
		request.setAttribute("user", login);
		page = NavigationManager.getProperty("path.page.main");

		return page;

	}

}
