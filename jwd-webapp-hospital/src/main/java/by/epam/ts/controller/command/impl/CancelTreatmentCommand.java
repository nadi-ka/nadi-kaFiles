package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.AccessManager;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.command.util.treat_inspector.TreatmentStatusInspector;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class CancelTreatmentCommand implements Command, AccessManager {

	private static final Logger log = LogManager.getLogger(CancelTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Check the staff rights;
		boolean staffRights = checkDoctorRights(request);
		if (!staffRights) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}

		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);

		// if procedure was completed/canceled, it cant't be canceled again;
		TreatmentStatusInspector statusInspector = new TreatmentStatusInspector();
		if (!statusInspector.checkTreatmentStatus(request)) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.WRONG_REQUEST).setPatientId(patientId).getResultString());

			return;
		}

		// Cancellation is allowed. Getting of all required parameters;
		String staffId = getUserIdFromSession(request);
		String idAppointment = request.getParameter(RequestAtribute.ID_APPOINTMENT);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		TreatmentService service = factory.getTreatmentService();
		try {
			service.cancelTreatment(idAppointment, staffId);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.CANCELED_SUCCESS).setPatientId(patientId).getResultString());

		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling cancelTreatment() from  CancelTreatmentCommand. Invalid parameters:", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.ERROR_DATA).setPatientId(patientId).getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling cancelTreatment() from CancelTreatmentCommand.", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());

		}

	}

}
