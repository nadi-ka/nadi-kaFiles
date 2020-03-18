package by.epam.ts.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.epam.ts.servlet.actionFactory.ActionFactory;
import by.epam.ts.servlet.manager.ConfigurationManager;
import by.epam.ts.servlet.manager.MessageManager;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void destroy() {
		ServiceFactory factory = null;
		try {
			factory = ServiceFactoryImpl.getInstance();
		}catch(ServiceException ex) {
			//log
		}
		UserService userService = factory.getUserService();
		userService.clearConnection();
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String page = null;
		ActionFactory client = new ActionFactory();
		ActionCommand command = client.defineCommand(request);
		page = command.execute(request);
		if (page != null) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);	
		}
		else {
			page = ConfigurationManager.getProperty("path.page.index");
			request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
			response.sendRedirect(request.getContextPath() + page);
		}
		
	}
}
