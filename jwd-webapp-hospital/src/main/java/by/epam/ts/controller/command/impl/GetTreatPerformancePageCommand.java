package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;
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
import by.epam.ts.bean.Treatment;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetTreatPerformancePageCommand implements Command {

	private static final String PATH = "path.page.staff.treat_performance";
	private static final Logger log = LogManager.getLogger(GetTreatPerformancePageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		TreatmentService treatmentService = factory.getTreatmentService();
		HospitalizationService hospitalizationService = factory.getHospitalizationService();
		Hospitalization hospitalization;
		try {
			// getting of the last hospitalization;
			hospitalization = hospitalizationService.getLastHospitalizationById(patientId);

			if (hospitalization != null) {
				// the last entry date;
				LocalDate entryDate = hospitalization.getEntryDate();
				// the list of prescriptions during the last hospitalization, which could be
				// empty;
				List<Treatment> prescriptions = treatmentService.getTreatmentDuringLastHospitalization(patientId, entryDate);
				if (!prescriptions.isEmpty()) {
					List<CurrentTreatment> performingList;
					for (Treatment treatment : prescriptions) {
						int idAppointment = treatment.getIdAppointment();
						// the list of performed procedures (could be empty, if the treatment hasn't
						// been begun yet);
						performingList = treatmentService.getCurrentTreatmentByAppointmentId(idAppointment);
						treatment.setPerformingList(performingList);
					}
				}
				Collections.sort(prescriptions, Treatment.treatmentStatusComparator);
				
				request.setAttribute(RequestAtribute.PRESCRIPTIONS, prescriptions);
				request.setAttribute(RequestAtribute.PATIENT_ID, patientId);
				log.info("id:" + patientId);
				String page = NavigationManager.getProperty(PATH);
				goForward(request, response, page);
			} else {
				// the patient hasn't been hospitalized yet, no treatment waiting for
				// performance;
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND + "="
						+ CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE
						+ "=" + RequestMessage.NO_CURRENT_TREATMENT + "&" + RequestAtribute.PATIENT_ID + "="
						+ patientId);
			}
		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling execute() from GetTreatPerformancePageCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
