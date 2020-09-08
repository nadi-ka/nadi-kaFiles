package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

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
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddNewTreatmentCommand implements Command, AccessManager {

	private static final String ENCODING = "UTF-8";
	private static final Logger log = LogManager.getLogger(AddNewTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Checking of the user rights;
		boolean staffRights = checkDoctorRights(request);
		if (!staffRights) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		String staffId = getUserIdFromSession(request);
		String treatmentType = request.getParameter(RequestAtribute.TREATMENT_TYPE);
		String treatmentName = request.getParameter(RequestAtribute.TREATMENT_NAME);
		String dateBegin = request.getParameter(RequestAtribute.DATE_BEGINNING);
		String dateFinish = request.getParameter(RequestAtribute.DATE_FINISHING);

		// checking of date_finish, as it's allowed not to be fulfilled. By default,
		// date_finish is equals date_begin;
		if (dateFinish == null || dateFinish.isEmpty()) {
			dateFinish = dateBegin;
		}
		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		TreatmentService service = factory.getTreatmentService();

		try {
			service.addNewTreatment(patientId, treatmentType, treatmentName, staffId, dateBegin, dateFinish);
			String treatNameUTF8 = URLEncoder.encode(treatmentName, ENCODING);

			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_PRESCRIPTIONS_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TREATMENT_ADDED_SUCCESSFULY)
					.setTreatmentName(treatNameUTF8).setPatientId(patientId).getResultString());

			log.info("Treat added: " + patientId + " " + treatmentType + " " + treatmentName + " " + staffId + " "
					+ dateBegin + " " + dateFinish);
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewTreatment() from  AddNewTreatmentCommand. Invalid parameters:",
					e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_PRESCRIPTIONS_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).setPatientId(patientId)
					.setInvalidParameters(e.getMessage()).getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling userService.addNewTreatment() from  AddNewTreatmentCommand.", e);

			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());

		}

	}

}
