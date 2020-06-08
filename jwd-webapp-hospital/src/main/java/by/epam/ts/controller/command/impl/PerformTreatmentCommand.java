package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.treat_type.TreatmentType;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.treat_inspector.TreatmentRightsInspector;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class PerformTreatmentCommand implements Command {

	private static final Logger log = LogManager.getLogger(PerformTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String treatmentTypeValue = request.getParameter(RequestAtribute.TREATMENT_TYPE);
		if (treatmentTypeValue == null || treatmentTypeValue.isEmpty()) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		TreatmentType type = TreatmentType.getTreatmentType(treatmentTypeValue);
		if (type == TreatmentType.UNKNOWN) {
			// The type of treatment is undefined, the procedure is prohibited;
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		// Checking of the staff rights for performing current procedure;
		TreatmentRightsInspector inspector = new TreatmentRightsInspector();
		boolean staffRights = inspector.inspectRightsForCurrentProcedure(request, type);
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		if (!staffRights) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_TREAT_PERFORMANCE_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.ACCESS_DENIED + "&" + RequestAtribute.PATIENT_ID
					+ "=" + patientId);
			return;
		}
		// Performing of the procedure is allowed. Getting of all required parameters.
		String staffId = getUserIdFromSession(request);
		String idAppointment = request.getParameter(RequestAtribute.ID_APPOINTMENT);
		String consent = request.getParameter(RequestAtribute.CONSENT);
		String datePerforming = request.getParameter(RequestAtribute.SETTING_DATE);
		String status = request.getParameter(RequestAtribute.TREATMENT_STATUS);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			userService.performCurrentTreatment(consent, idAppointment, datePerforming, staffId, status);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_TREAT_PERFORMANCE_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.PATIENT_ID + "=" + patientId);
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.performeCurrentTreatment from  PerformTreatmentCommand. Invalid parameters:",
					e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_TREAT_PERFORMANCE_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.ERROR_DATA + "&"
					+ RequestAtribute.INVALID_PARAMETERS + "=" + e.getMessage() + "&" + RequestAtribute.PATIENT_ID + "=" + patientId);
		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.performCurrentTreatment() from PerformTreatmentCommand.", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
