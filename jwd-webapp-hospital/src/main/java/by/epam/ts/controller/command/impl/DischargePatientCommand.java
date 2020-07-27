package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class DischargePatientCommand implements Command, AccessManager {

	private static final Logger log = LogManager.getLogger(DischargePatientCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Checking of the user rights;
		boolean staffRights = checkDoctorRights(request);
		if (!staffRights) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		String dischargeDate = request.getParameter(RequestAtribute.DATE_FINISHING);
		String idHistory = request.getParameter(RequestAtribute.ID_MEDICAL_HYSTORY);
		String entryDate = request.getParameter(RequestAtribute.DATE_BEGINNING);
		String alreadyDischarged = request.getParameter(RequestAtribute.ALREADY_DISCHARGED);
		
		//If patient has already been discharged, simply return to the previous page with message;
		if (alreadyDischarged != null && !alreadyDischarged.isEmpty()) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_HOSPITALIZATION_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.DISCHARGED_ELIER + "&"
					+ RequestAtribute.PATIENT_ID + "=" + patientId);
			return;
		}

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		DiagnosisService service = factory.getDiagnosisService();
		HospitalizationService hospitalizationService = factory.getHospitalizationService();

		try {
			List<PatientDiagnosis> diagnosisList = service.getCurrentDiagnosisSorted(patientId, entryDate);
			if (!diagnosisList.isEmpty()) {
				// actual diagnosis was found, form the string with final diagnosis;
				String finalDiagnosisString = formFinalDiagnosis(diagnosisList);
				
				// set the discharge date;
				hospitalizationService.setDischargeDate(dischargeDate, entryDate, idHistory);
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT
						+ RequestAtribute.COMMAND + "=" + CommandEnum.GET_HOSPITALIZATION_PAGE.toString().toLowerCase()
						+ "&" + RequestAtribute.MESSAGE + "=" + RequestMessage.DISCHARGED_SUCCESSFULY + "&"
						+ RequestAtribute.PATIENT_ID + "=" + patientId + "&" + RequestAtribute.NAME_DIAGNOSIS + "="
						+ finalDiagnosisString);
			} else {
				// patient shouldn't be discharged without actual diagnosis;
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT
						+ RequestAtribute.COMMAND + "=" + CommandEnum.GET_HOSPITALIZATION_PAGE.toString().toLowerCase()
						+ "&" + RequestAtribute.MESSAGE + "=" + RequestMessage.DIAGNOSIS_ABSENT + "&"
						+ RequestAtribute.PATIENT_ID + "=" + patientId);
			}
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.setDischargeDate() from  DischargePatientCommand. Invalid parameters:",
					e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_HOSPITALIZATION_PAGE.toString().toLowerCase() + "&"
					+ RequestAtribute.MESSAGE + "=" + RequestMessage.ERROR_DATA_DISCHARGE + "&"
					+ RequestAtribute.PATIENT_ID + "=" + patientId + "&"
							+ RequestAtribute.INVALID_PARAMETERS + "=" + e.getMessage());
		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling userService.setDischargeDate() from  DischargePatientCommand.", e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.TECHNICAL_ERROR);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

	private String formFinalDiagnosis(List<PatientDiagnosis> diagnosisList) throws UnsupportedEncodingException{
		StringBuilder resultDiagnosis = new StringBuilder();
		String delimeter = ", ";
		for (PatientDiagnosis item : diagnosisList) {
			resultDiagnosis.append(item.getDiagnosisName());
			resultDiagnosis.append(delimeter);
		}
		String str = resultDiagnosis.toString().trim();
		String encoding = "UTF-8";
		String resultStringUTF8 = URLEncoder.encode(str.substring(0, str.length() - 1), encoding);
		return resultStringUTF8;
	}

}
