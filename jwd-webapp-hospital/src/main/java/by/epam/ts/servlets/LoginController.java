package by.epam.ts.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		if (request.getParameter("btn_login") != null) {
			String login = request.getParameter("txt_login");
			String password = request.getParameter("txt_password");
			ServiceFactory factory = null;
		    String failedMessage = "LogIn procedure finished unsuccessfully.";
			try {
				factory = ServiceFactoryImpl.getInstance();
			}catch(ServiceException ex) {
				//log
				request.setAttribute("failedMessage", failedMessage);
		    	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		    	dispatcher.include(request, response);
			}
			UserService userService = factory.getUserService();
			
		    try {
		    	userService.logIn(login, password);
		    	HttpSession session = request.getSession();
		    	session.setAttribute("login", login);
		    	RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
		    	dispatcher.forward(request, response);
		    }catch (ServiceException ex){
		    	//log
		    	request.setAttribute("failedMessage", failedMessage);
		    	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		    	dispatcher.include(request, response);
		    }
		}
	}

}
