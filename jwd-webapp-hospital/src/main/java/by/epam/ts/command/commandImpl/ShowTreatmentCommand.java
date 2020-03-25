package by.epam.ts.command.commandImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.epam.ts.bean.Treatment;
import by.epam.ts.bean.User;
import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.ServiceException;
import by.epam.ts.service.UserService;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.servlet.manager.ConfigurationManager;
import by.epam.ts.servlet.manager.MessageManager;

public final class ShowTreatmentCommand implements ActionCommand {
	
	private ServiceFactory factory;
	
	public  ShowTreatmentCommand(ServiceFactory factory) {
		this.factory = factory;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;

		UserService userService = factory.getUserService();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userData");
		String id = null;
		if (user != null) {
			id = user.getId();
		}

		List<Treatment> prescriptions = new ArrayList<Treatment>();
		try {
			prescriptions = userService.getPatientsTreatmentById(id);
		} catch (ServiceException ex) {
			// log
			String message = MessageManager.getProperty("message.technicalerror");
			request.setAttribute("techninalErrorMessage", message);
			page = ConfigurationManager.getProperty("path.page.error");
			return page;
		}

		if (prescriptions.isEmpty()) {
			String message = MessageManager.getProperty("message.accessDenied");
			request.setAttribute("accessDeniedMessage", message);
			page = ConfigurationManager.getProperty("path.page.main");
			return page;
		}
		request.setAttribute("treatment", prescriptions);
		page = ConfigurationManager.getProperty("path.page.treatment");

		return page;
	}
}
