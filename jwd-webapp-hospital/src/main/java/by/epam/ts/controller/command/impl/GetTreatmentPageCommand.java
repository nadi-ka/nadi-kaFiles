package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.CurrentTreatment;
import by.epam.ts.bean.Treatment;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.TreatmentService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetTreatmentPageCommand implements Command {

	private static final String PATH = "path.page.treatment";
	private static final Logger log = LogManager.getLogger(GetTreatmentPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		TreatmentService service = factory.getTreatmentService();

		String userId = getUserIdFromSession(request);

		if (userId == null) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}
		List<Treatment> prescriptions = new ArrayList<Treatment>();
		try {
			prescriptions = service.getPatientTreatmentById(userId);
			if (!prescriptions.isEmpty()) {
				List<CurrentTreatment> performingList;
				for (Treatment treatment : prescriptions) {
					int idAppointment = treatment.getIdAppointment();
					// the list of performed procedures (could be empty, if the treatment hasn't
					// been begun yet);
					performingList = service.getCurrentTreatmentByAppointmentId(idAppointment);
					treatment.setPerformingList(performingList);
				}
			}
			request.setAttribute(RequestAtribute.PRESCRIPTIONS, prescriptions);
			page = NavigationManager.getProperty(PATH);
			goForward(request, response, page);
		} catch (ServiceException ex) {
			log.log(Level.ERROR, "Error when calling execute() from GetTreatmentPageCommand", ex);
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());
		}

	}
}
