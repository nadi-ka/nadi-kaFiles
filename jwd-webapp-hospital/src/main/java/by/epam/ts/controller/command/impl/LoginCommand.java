package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.User;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.manager.MessageManager;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class LoginCommand implements Command {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
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
			} catch (ServiceException ex) {
				log.log(Level.ERROR, "Error during calling method logIn() from LoginCommand.", ex);
				String message = MessageManager.getProperty("local.login.successfully.registr");
				request.setAttribute("errordata", message);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(NavigationManager.getProperty("path.page.login"));
				if (requestDispatcher != null) {
					requestDispatcher.forward(request, response);
				} else {
					log.log(Level.ERROR, "requestDispatcher==null");
					response.sendRedirect(NavigationManager.getProperty("path.page.error"));
				}
			}

			HttpSession session = request.getSession(true);
			// User stores parameters: id, login, role, userStatus;
			session.setAttribute("userData", user);
			response.sendRedirect(request.getContextPath() + "/register?command=show_patient_main_page");
		
	}

}
