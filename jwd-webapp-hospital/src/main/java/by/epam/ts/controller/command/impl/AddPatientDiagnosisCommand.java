package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.parse.DateParser;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddPatientDiagnosisCommand implements Command {

	private static final String absentDiagnosis = "NONE";

	private static final Logger log = LogManager.getLogger(AddPatientDiagnosisCommand.class);

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
		String idPatient = request.getParameter(RequestAtribute.PATIENT_ID);
		String codePrimary = request.getParameter(RequestAtribute.PRIMARY_DIAGNOSIS);
		String[] codeSecondary = request.getParameterValues(RequestAtribute.SECONDARY_DIAGNOSIS);
		log.info("id:" +idPatient);

		//parsing of the date;
		String settingDate = request.getParameter(RequestAtribute.SETTING_DATE);
		DateParser parser = new DateParser();
		LocalDate diagnosisSettingDate = parser.parseDate(settingDate);
		
		boolean isPrimary = true;
		List<PatientDiagnosis> diagnosisList = new ArrayList<PatientDiagnosis>();
		// check if primary diagnosis was entered by user and add it to List if it's
		// true;
		if (codePrimary != null && !codePrimary.isEmpty() && !codePrimary.equals(absentDiagnosis)) {
			PatientDiagnosis primaryDiagnosis = new PatientDiagnosis(idPatient, codePrimary, isPrimary,
					diagnosisSettingDate);
			diagnosisList.add(primaryDiagnosis);
		}
		// check if secondary diagnosis were entered by user and add them to List if
		// it's true;
		if (codeSecondary != null) {

			for (int i = 0; i < codeSecondary.length; i++) {
				if (codeSecondary[i] != null && !codeSecondary[i].isEmpty()
						&& !codeSecondary[i].equals(absentDiagnosis)) {
				PatientDiagnosis secondaryDiagnosis = new PatientDiagnosis(idPatient, codeSecondary[i], !isPrimary,
						diagnosisSettingDate);
				diagnosisList.add(secondaryDiagnosis);
				}
			}
		}
		// if diagnosisList is empty, redirect to diagnosis page again with the message;
		if (diagnosisList.isEmpty()) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE
					+ "=" + RequestMessage.NOTHING_CHOSEN + "&" + RequestAtribute.PATIENT_ID + "=" + idPatient);
			return;
		}

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			userService.addPatientDiagnosis(diagnosisList);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.DIAGNOSIS_ADDED_SUCCESSFULY + "&"
					+ RequestAtribute.PATIENT_ID + "=" + idPatient);
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addPatientDiagnosis(diagnosisList) from  AddPatientDiagnosisCommand. Invalid parameters:",
					e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.ERROR_DATA);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ERROR_DATA + "&" + RequestAtribute.PATIENT_ID + "=" + idPatient);
		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.addPatientDiagnosis(diagnosisList) from  AddPatientDiagnosisCommand.",
					e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.TECHNICAL_ERROR);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
