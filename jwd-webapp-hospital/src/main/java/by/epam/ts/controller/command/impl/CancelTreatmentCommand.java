package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.access_manager.AccessManager;
import by.epam.ts.controller.command.util.treat_inspector.TreatmentStatusInspector;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class CancelTreatmentCommand implements Command, AccessManager {
	
	private static final Logger log = LogManager.getLogger(CancelTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Check the staff rights;
		boolean staffRights = checkDoctorRights(request);
		if (!staffRights) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}

		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);

		// if procedure was completed/canceled, it cant't be canceled again;
		TreatmentStatusInspector statusInspector = new TreatmentStatusInspector();
		if (!statusInspector.checkTreatmentStatus(request)) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.WRONG_REQUEST + "&" + RequestAtribute.PATIENT_ID
					+ "=" + patientId);
			return;
		}
		
		// Cancellation is allowed. Getting of all required parameters.
		String staffId = getUserIdFromSession(request);
		String idAppointment = request.getParameter(RequestAtribute.ID_APPOINTMENT);
		
		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			userService.cancelTreatment(idAppointment, staffId);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.PATIENT_ID + "=" + patientId + "&" + RequestAtribute.MESSAGE + "=" + RequestMessage.CANCELED_SUCCESS);
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling cancelTreatment() from  CancelTreatmentCommand. Invalid parameters:",
					e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.ERROR_DATA);
		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling cancelTreatment() from CancelTreatmentCommand.", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}


	}

}
