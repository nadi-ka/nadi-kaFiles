package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.epam.ts.servlet.manager.ConfigurationManager;
import by.epam.ts.servlet.manager.MessageManager;

public class SignUpCommand implements ActionCommand{

	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String PARAM_NAME_EMAIL = "email";
	private static final String PARAM_NAME_USER_TYPE = "type-of-user";
		
		@Override
		public String execute(HttpServletRequest request) {
			String page = null;
			String login = request.getParameter(PARAM_NAME_LOGIN);
			String password = request.getParameter(PARAM_NAME_PASSWORD);
			String email = request.getParameter(PARAM_NAME_EMAIL);
			String typeOfUser = request.getParameter(PARAM_NAME_USER_TYPE);
			
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
			boolean isStaff = false;
			if (typeOfUser.equals("medical-staff")) {
				isStaff = true;
			}
			
			try {
				userService.signUp(email, login, password, isStaff);
				request.setAttribute("user", login);
				page = ConfigurationManager.getProperty("path.page.main");
			} catch(ServiceException ex) {
				//log
				request.setAttribute("errorData", MessageManager.getProperty("message.registrationError"));
				page = ConfigurationManager.getProperty("path.page.login");
			}
			
			return page;
		
		}
	

}
