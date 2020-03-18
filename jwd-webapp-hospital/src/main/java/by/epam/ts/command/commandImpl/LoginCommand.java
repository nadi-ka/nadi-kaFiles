package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.epam.ts.servlet.manager.ConfigurationManager;
import by.epam.ts.servlet.manager.MessageManager;

public class LoginCommand implements ActionCommand{
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		
		ServiceFactory factory = null;
		try {
		factory = ServiceFactoryImpl.getInstance();
		}catch(ServiceException ex) {
			//log
			request.setAttribute("techninalErrorMessage", 
					MessageManager.getProperty("message.technicalerror"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		UserService userService = factory.getUserService();
		try {
			userService.logIn(login, password);
			request.setAttribute("user", login);
			page = ConfigurationManager.getProperty("path.page.main");
		} catch(ServiceException ex) {
			//log
			request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("message.loginerror"));
			page = ConfigurationManager.getProperty("path.page.login");
		}
		
		return page;
	
	}

}
