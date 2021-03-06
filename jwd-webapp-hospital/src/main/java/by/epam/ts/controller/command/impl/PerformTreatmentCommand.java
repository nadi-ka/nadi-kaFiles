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
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.command.util.treat_inspector.TreatmentRightsInspector;
import by.epam.ts.controller.command.util.treat_inspector.TreatmentStatusInspector;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class PerformTreatmentCommand implements Command {

	private static final Logger log = LogManager.getLogger(PerformTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);

		// if procedure was completed/canceled, it shouldn't be performed;
		TreatmentStatusInspector statusInspector = new TreatmentStatusInspector();
		if (!statusInspector.checkTreatmentStatus(request)) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_TREAT_PERFORMANCE_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.WRONG_REQUEST).setPatientId(patientId).getResultString());

			return;
		}

		String treatmentTypeValue = request.getParameter(RequestAtribute.TREATMENT_TYPE);
		TreatmentType type = TreatmentType.getTreatmentType(treatmentTypeValue);
		// The type of treatment is undefined, the procedure is prohibited;
		if (treatmentTypeValue == null || treatmentTypeValue.isEmpty() || type == TreatmentType.UNKNOWN) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}

		// Checking of the staff rights for performing current procedure;
		TreatmentRightsInspector inspector = new TreatmentRightsInspector();
		boolean staffRights = inspector.inspectRightsForCurrentProcedure(request, type);
		if (!staffRights) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_TREAT_PERFORMANCE_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.ACCESS_DENIED).setPatientId(patientId).getResultString());

			return;
		}
		// Performing of the procedure is allowed. Getting of all required parameters.
		String staffId = getUserIdFromSession(request);
		String idAppointment = request.getParameter(RequestAtribute.ID_APPOINTMENT);
		String consent = request.getParameter(RequestAtribute.CONSENT);
		String datePerforming = request.getParameter(RequestAtribute.SETTING_DATE);
		String status = request.getParameter(RequestAtribute.TREATMENT_STATUS);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		TreatmentService service = factory.getTreatmentService();
		try {
			service.performCurrentTreatment(consent, idAppointment, datePerforming, staffId, status);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_TREAT_PERFORMANCE_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setPatientId(patientId).getResultString());

		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling performeCurrentTreatment() from  PerformTreatmentCommand. Invalid parameters:",
					e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_TREAT_PERFORMANCE_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).setPatientId(patientId)
					.setInvalidParameters(e.getMessage()).getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling performCurrentTreatment() from PerformTreatmentCommand.", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}
}
