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
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddNewDiagnosisCommand implements Command {

	private static final Logger log = LogManager.getLogger(AddNewDiagnosisCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Checking of the user rights;
		boolean staffRights = checkStaffRights(request, response);
		if (!staffRights) {
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		String diagnosisCode = request.getParameter(RequestAtribute.CODE_DIAGNOSIS);
		String diagnosisName = request.getParameter(RequestAtribute.NAME_DIAGNOSIS);
		String bedDays = request.getParameter(RequestAtribute.BED_DAYS);
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		
		if (bedDays == null || bedDays.isEmpty()) {
			bedDays = "0";
		}

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		
		try {
			userService.addNewDiagnosis(diagnosisCode, diagnosisName, bedDays);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.DIAGNOSIS_ADDED_SUCCESSFULY + "&"
					+ RequestAtribute.PATIENT_ID + "=" + patientId);
		}catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewDiagnosis() from  AddNewDiagnosisCommand. Invalid parameters:",
					e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.ERROR_DATA);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ERROR_DATA + "&" + RequestAtribute.PATIENT_ID + "=" + patientId);
		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.addNewDiagnosis() from  AddNewDiagnosisCommand.",
					e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.TECHNICAL_ERROR);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
