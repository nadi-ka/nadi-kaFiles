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
import by.epam.ts.controller.command.access_manager.AccessManager;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.command.util.parse.DateParser;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddPatientDiagnosisCommand implements Command, AccessManager {

	private static final String absentDiagnosis = "NONE";

	private static final Logger log = LogManager.getLogger(AddPatientDiagnosisCommand.class);

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
		String idPatient = request.getParameter(RequestAtribute.PATIENT_ID);
		String codePrimary = request.getParameter(RequestAtribute.PRIMARY_DIAGNOSIS);
		String[] codeSecondary = request.getParameterValues(RequestAtribute.SECONDARY_DIAGNOSIS);

		// parsing of the date;
		String settingDate = request.getParameter(RequestAtribute.SETTING_DATE);
		DateParser parser = new DateParser();
		LocalDate diagnosisSettingDate = parser.parseDate(settingDate);

		if (diagnosisSettingDate == null) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.INVALID_DATE).setPatientId(idPatient).getResultString());

			return;
		}

		List<PatientDiagnosis> diagnosisList = getPatientDiagnosisList(codePrimary, codeSecondary, idPatient,
				diagnosisSettingDate);
		// if diagnosisList is empty, redirect to diagnosis page again with the message;
		if (diagnosisList.isEmpty()) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.NOTHING_CHOSEN).setPatientId(idPatient).getResultString());

			return;
		}

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		DiagnosisService service = factory.getDiagnosisService();

		try {
			service.addPatientDiagnosis(diagnosisList);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.DIAGNOSIS_ADDED_SUCCESSFULY).setPatientId(idPatient)
					.getResultString());

		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addPatientDiagnosis() from  AddPatientDiagnosisCommand. Invalid parameters:",
					e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_DIAGNOSIS_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.ERROR_DATA).setPatientId(idPatient).getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.addPatientDiagnosis() from  AddPatientDiagnosisCommand.", e);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());

		}

	}

	private List<PatientDiagnosis> getPatientDiagnosisList(String codePrimary, String[] codeSecondary, String idPatient,
			LocalDate diagnosisSettingDate) {

		List<PatientDiagnosis> diagnosisList = new ArrayList<PatientDiagnosis>();
		boolean isPrimary = true;

		// check if primary diagnosis was entered by user and add it to List;
		if (codePrimary != null && !codePrimary.isEmpty() && !codePrimary.equals(absentDiagnosis)) {
			PatientDiagnosis primaryDiagnosis = new PatientDiagnosis(idPatient, codePrimary, isPrimary,
					diagnosisSettingDate);
			diagnosisList.add(primaryDiagnosis);
		}
		// check if secondary diagnosis were entered by user and add them to List;
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
		return diagnosisList;
	}

}
