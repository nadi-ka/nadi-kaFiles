package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetCurrentPatientPageCommand implements Command {

	private static final Logger log = LogManager.getLogger(GetCurrentPatientPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		log.info("id:" + patientId);
		String message = request.getParameter(RequestAtribute.MESSAGE);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		Patient patient;
		List<Treatment> prescriptions;
		List<PatientDiagnosis> diagnosisList;
		List<Hospitalization> hospitalizations;
		try {
			prescriptions = userService.getSortedPatientsTreatmentById(patientId);
			diagnosisList = userService.getSortedPatientDiagnosisById(patientId);
			hospitalizations = userService.getAllHospitalizationsById(patientId);
			patient = userService.getPatientById(patientId);
			patient.setPrescriptions(prescriptions);
			patient.setDiagnosisList(diagnosisList);
			patient.setHospitalizations(hospitalizations);
			request.setAttribute(RequestAtribute.PATIENT, patient);
			request.setAttribute(RequestAtribute.MESSAGE, message);
			String page = NavigationManager.getProperty("path.page.staff.current_patient");
			goForward(request, response, page);
		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling execute() from GetCurrentPatientPageCommand", e);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}
	}

}
