package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.servlet.manager.ConfigurationManager;
import by.epam.ts.servlet.manager.MessageManager;

public final class SignUpCommand implements ActionCommand {

	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String PARAM_NAME_EMAIL = "email";
	private static final String PARAM_NAME_USER_TYPE = "type-of-user";
	
	private ServiceFactory factory;
	
	public  SignUpCommand(ServiceFactory factory) {
		this.factory = factory;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		String email = request.getParameter(PARAM_NAME_EMAIL);
		String typeOfUser = request.getParameter(PARAM_NAME_USER_TYPE);

		UserService userService = factory.getUserService();
		boolean isStaff = false;
		if (typeOfUser.equals("medical-staff")) {
			isStaff = true;
		}

		int updatedRows = 0;

		try {
			updatedRows = userService.signUp(email, login, password, isStaff);
		} catch (ServiceException ex) {
			// log
			String message = MessageManager.getProperty("message.technicalerror");
			request.setAttribute("techninalErrorMessage", message);
			page = ConfigurationManager.getProperty("path.page.error");
		}

		if (updatedRows == 0) {
			String message = MessageManager.getProperty("message.registrationError");
			request.setAttribute("errorRegistrationDada", message);
			page = ConfigurationManager.getProperty("path.page.signUp");
		} else {
			request.setAttribute("user", login);
			page = ConfigurationManager.getProperty("path.page.main");
		}

		return page;

	}

}
