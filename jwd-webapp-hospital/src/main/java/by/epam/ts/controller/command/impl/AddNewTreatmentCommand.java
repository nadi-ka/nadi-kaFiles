package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.constant_attribute.SessionAtribute;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddNewTreatmentCommand implements Command {

	private static final Logger log = LogManager.getLogger(AddNewTreatmentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Checking of the user rights;
		boolean staffRights = checkDoctorRights(request, response);
		if (!staffRights) {
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		HttpSession session = request.getSession(false);
		String staffId = null;
		if (session != null) {
			staffId = (String) session.getAttribute(SessionAtribute.USER_ID);
		}
		String treatmentType = request.getParameter(RequestAtribute.TREATMENT_TYPE);
		String treatmentName = request.getParameter(RequestAtribute.TREATMENT_NAME);
		String dateBegin = request.getParameter(RequestAtribute.DATE_BEGINNING);
		String dateFinish = request.getParameter(RequestAtribute.DATE_FINISHING);

		// checking of date_finish, as it's allowed not to be fulfilled. By default,
		// date_finish is equals date_begin;
		if (dateFinish == null || dateFinish.isEmpty()) {
			dateFinish = dateBegin;
		}
		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();

		try {
			userService.addNewTreatment(patientId, treatmentType, treatmentName, staffId, dateBegin, dateFinish);
			String treatNameUTF8 = URLEncoder.encode(treatmentName, "UTF-8");
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_PRESCRIPTIONS_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE
					+ "=" + RequestMessage.TREATMENT_ADDED_SUCCESSFULY + "&" + RequestAtribute.TREATMENT_NAME + "="
					+ treatNameUTF8 + "&" + RequestAtribute.PATIENT_ID + "=" + patientId);
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewTreatment() from  AddNewTreatmentCommand. Invalid parameters:",
					e);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.GET_PRESCRIPTIONS_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ERROR_DATA + "&" + RequestAtribute.PATIENT_ID + "=" + patientId);
		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling userService.addNewTreatment() from  AddNewTreatmentCommand.", e);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
