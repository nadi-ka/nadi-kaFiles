package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetUpdatePersonalDataPageCommand implements Command {
	
	private static final Logger log = LogManager.getLogger(GetUpdatePersonalDataPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stafftId = getUserIdFromSession(request);
		
		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			//if staff will not be found - service throws exception (access allowed only for correctly logged persons);
			MedicalStaff staff = userService.getStaffById(stafftId);
			request.setAttribute(RequestAtribute.MEDICAL_STAFF, staff);
			String page = NavigationManager.getProperty("path.page.staff.update_personal_data");
			goForward(request, response, page);
		}catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling execute() from GetUpdatePersonalDataPageCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
