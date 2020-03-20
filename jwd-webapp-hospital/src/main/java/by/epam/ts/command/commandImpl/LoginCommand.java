package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.ts.bean.User;
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
			String message = MessageManager.getProperty("message.technicalerror");
			request.setAttribute("techninalErrorMessage", 
					message);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		UserService userService = factory.getUserService();
		User user = null;
		
		try {
			user = userService.logIn(login, password);
		}catch (ServiceException ex) {
			//log
			String message = MessageManager.getProperty("message.technicalerror");
			request.setAttribute("techninalErrorMessage", 
					message);
			page = ConfigurationManager.getProperty("path.page.error");
		}
			
			if(user == null) {
				String message = MessageManager.getProperty("message.loginerror");
				request.setAttribute("errorLoginPassMessage", message);
				page = ConfigurationManager.getProperty("path.page.login");
			}
			HttpSession session = request.getSession(true);
			
			//User stores parameters: id, login, role, userStatus;
			session.setAttribute("userData", user);
			
			request.setAttribute("user", login);
			page = ConfigurationManager.getProperty("path.page.main");
		
		return page;
	
	}

}
