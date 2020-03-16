package by.epam.ts.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;

public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("btn_register") != null) {
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String typeOfUser = request.getParameter("type-of-user");
			String successMessage = "You've been registered succesfully.";
			String failedMessage = "Registration procedure wasn't completed succesfully.";
			ServiceFactory factory = null;
			try {
				factory = ServiceFactoryImpl.getInstance();
			} catch (ServiceException ex) {
				// log
				request.setAttribute("failedMessage", failedMessage);
				RequestDispatcher dispatcher = request.getRequestDispatcher("signUp.jsp");
				dispatcher.include(request, response);
			}
			UserService userService = factory.getUserService();
			boolean isStaff = false;
			if (typeOfUser.equals("medical-staff")) {
				isStaff = true;
			}

			try {
				userService.signUp(email, login, password, isStaff);
				request.setAttribute("successMessage", successMessage);
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			} catch (ServiceException ex) {
				// log
				request.setAttribute("failedMessage", failedMessage);
				RequestDispatcher dispatcher = request.getRequestDispatcher("signUp.jsp");
				dispatcher.include(request, response);
			}
		}
	}
}
