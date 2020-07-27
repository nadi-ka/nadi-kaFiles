package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Hospitalization;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.PatientDiagnosis;
import by.epam.ts.bean.Treatment;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.DiagnosisService;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetCurrentPatientPageCommand implements Command {

	private static final String PATH = "path.page.staff.current_patient";
	private static final Logger log = LogManager.getLogger(GetCurrentPatientPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		String message = request.getParameter(RequestAtribute.MESSAGE);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		TreatmentService treatmentService = factory.getTreatmentService();
		DiagnosisService diagnosisService = factory.getDiagnosisService();
		HospitalizationService hospitalizationService = factory.getHospitalizationService();
		
		Patient patient;
		List<Treatment> prescriptions;
		List<PatientDiagnosis> diagnosisList;
		List<Hospitalization> hospitalizations;
		try {
			prescriptions = treatmentService.getPatientTreatmentById(patientId);
			log.info("prescriptions:" + prescriptions.isEmpty());
			if (!prescriptions.isEmpty()) {
				List<CurrentTreatment> performingList;
				for (Treatment treatment : prescriptions) {
					int idAppointment = treatment.getIdAppointment();
					// the list of performed procedures (could be empty, if the treatment hasn't
					// been begun yet);
					performingList = treatmentService.getCurrentTreatmentByAppointmentId(idAppointment);
					log.info("performingList:" + performingList.toString());
					treatment.setPerformingList(performingList);
					log.info(treatment.getTreatmentName() + " " + treatment.getTreatmentStatus());
				}
			}
			Collections.sort(prescriptions, Treatment.treatmentStatusComparator);
			
			diagnosisList = diagnosisService.getSortedPatientDiagnosisById(patientId);
			hospitalizations = hospitalizationService.getAllHospitalizationsById(patientId);
			patient = userService.getPatientById(patientId);
			patient.setPrescriptions(prescriptions);
			patient.setDiagnosisList(diagnosisList);
			patient.setHospitalizations(hospitalizations);
			request.setAttribute(RequestAtribute.PATIENT, patient);
			request.setAttribute(RequestAtribute.MESSAGE, message);
			String page = NavigationManager.getProperty(PATH);
			goForward(request, response, page);
		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling execute() from GetCurrentPatientPageCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}
	}
}
