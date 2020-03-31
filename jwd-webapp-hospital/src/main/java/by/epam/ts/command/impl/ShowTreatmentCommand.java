package by.epam.ts.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.command.Command;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;
import by.epam.ts.servlet.manager.NavigationManager;
import by.epam.ts.servlet.RegisterController;
import by.epam.ts.servlet.manager.MessageManager;

public final class ShowTreatmentCommand implements Command {
	private static final Logger log = LogManager.getLogger(RegisterController.class);

	@Override
	public String execute(HttpServletRequest request) {
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
			return page;
		}
		id = user.getId();

		List<Treatment> prescriptions = new ArrayList<Treatment>();
		try {
			prescriptions = userService.getPatientsTreatmentById(id);
		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method getPatientsTreatmentById() from ShowTreatmentCommand", ex);
			page = NavigationManager.getProperty("path.page.error");
			return page;
		}

		if (prescriptions.isEmpty()) {
			String message = MessageManager.getProperty("local.main.denied");
			request.setAttribute("accessdenied", message);
			page = NavigationManager.getProperty("path.page.main");
			return page;
		}
		request.setAttribute("treatment", prescriptions);
		page = NavigationManager.getProperty("path.page.treatment");

		return page;
	}
}
