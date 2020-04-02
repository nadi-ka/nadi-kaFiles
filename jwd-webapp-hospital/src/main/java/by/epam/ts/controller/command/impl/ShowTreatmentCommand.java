package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.manager.MessageManager;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class ShowTreatmentCommand implements Command {
	private static final Logger log = LogManager.getLogger(ShowTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userData");
		String id = null;
		if (user == null) {
			String message = MessageManager.getProperty("local.main.denied");
			request.setAttribute("accessdenied", message);
			page = NavigationManager.getProperty("path.page.main");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			} else {
				log.log(Level.ERROR, "requestDispatcher==null");
				response.sendRedirect(NavigationManager.getProperty("path.page.error"));
			}
		} else {
			id = user.getId();

			List<Treatment> prescriptions = new ArrayList<Treatment>();
			try {
				prescriptions = userService.getPatientsTreatmentById(id);
			} catch (ServiceException ex) {
				log.log(Level.ERROR, "Error during calling method getPatientsTreatmentById() from ShowTreatmentCommand",
						ex);
				page = NavigationManager.getProperty("path.page.error");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
				requestDispatcher.forward(request, response);
			}

			if (prescriptions.isEmpty()) {
				String message = MessageManager.getProperty("local.main.denied");
				request.setAttribute("accessdenied", message);
				page = NavigationManager.getProperty("path.page.main");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
				requestDispatcher.forward(request, response);
			} else {
				request.setAttribute("treatment", prescriptions);
				page = NavigationManager.getProperty("path.page.treatment");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
				requestDispatcher.forward(request, response);

			}
		}

	}
}
