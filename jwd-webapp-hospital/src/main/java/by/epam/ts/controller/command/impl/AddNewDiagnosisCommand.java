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
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddNewDiagnosisCommand implements Command, AccessManager {

	private static final Logger log = LogManager.getLogger(AddNewDiagnosisCommand.class);

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
		String diagnosisCode = request.getParameter(RequestAtribute.CODE_DIAGNOSIS);
		String diagnosisName = request.getParameter(RequestAtribute.NAME_DIAGNOSIS);
		String bedDays = request.getParameter(RequestAtribute.BED_DAYS);
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);

		if (bedDays == null || bedDays.isEmpty()) {
			bedDays = "0";
		}

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		DiagnosisService service = factory.getDiagnosisService();

		try {
			service.addNewDiagnosis(diagnosisCode, diagnosisName, bedDays);

			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.DIAGNOSIS_ADDED_SUCCESSFULY).setPatientId(patientId)
					.getResultString());

		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewDiagnosis() from  AddNewDiagnosisCommand. Invalid parameters:",
					e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).setPatientId(patientId)
					.setInvalidParameters(e.getMessage()).getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling userService.addNewDiagnosis() from  AddNewDiagnosisCommand.", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}

}
