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

public final class GetStaffDataPageCommand implements Command {
	
	private static final Logger log = LogManager.getLogger(GetStaffDataPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String newAddedStaffId = request.getParameter(RequestAtribute.STAFF_ID);
		String message = request.getParameter(RequestAtribute.MESSAGE);
		
		if (newAddedStaffId != null) {
			ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
			UserService userService = factory.getUserService();
			MedicalStaff medicalStaff;
			try {
				//in case when staff will not be found, service throws exception. Null value will never come.
				medicalStaff = userService.getStaffById(newAddedStaffId);
				request.setAttribute(RequestAtribute.MEDICAL_STAFF, medicalStaff);
				request.setAttribute(RequestAtribute.MESSAGE, message);
				String page = NavigationManager.getProperty("path.page.staff.staff_data");
		        goForward(request, response, page);
		        log.info("Staff obj:" + medicalStaff.toString());
			}catch (ServiceException e) {
				log.log(Level.ERROR, "Error when calling execute() from GetStaffDataPageCommand", e);
				response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
						+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
						+ RequestMessage.TECHNICAL_ERROR);
			}
		}else {
			String page = NavigationManager.getProperty("path.page.staff.staff_data");
	        goForward(request, response, page);
		}

	}

}
