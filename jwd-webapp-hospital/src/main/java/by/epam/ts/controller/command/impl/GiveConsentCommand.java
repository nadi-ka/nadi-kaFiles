package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.treat_status.TreatmentStatus;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GiveConsentCommand implements Command {

	private static final Logger log = LogManager.getLogger(GiveConsentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idAppointment = request.getParameter(RequestAtribute.ID_APPOINTMENT);
		String consent = request.getParameter(RequestAtribute.CONSENT);
		
		// if procedure was completed/canceled, it shouldn't be performed;
		if (!checkTreatStatusForPerformProcedure(request)) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_TREATMENT_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.WRONG_REQUEST).getResultString());
			
			return;
		}

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		TreatmentService service = factory.getTreatmentService();
		try {
			service.getPatientsConsent(idAppointment, consent);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_TREATMENT_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.getResultString());
			
		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method execute from GiveConsentCommand", ex);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}

	private boolean checkTreatStatusForPerformProcedure(HttpServletRequest request) {
		String treatmentStatus = request.getParameter(RequestAtribute.TREATMENT_STATUS);
		return (treatmentStatus != null && !treatmentStatus.isEmpty()
				&& !treatmentStatus.equals(TreatmentStatus.CANCELED.getStatusValue())
				&& !treatmentStatus.equals(TreatmentStatus.COMPLETED.getStatusValue()));
	}

}
