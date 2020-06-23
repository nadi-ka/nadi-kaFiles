package by.epam.ts.controller.command.util.treat_inspector;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.bean.treat_status.TreatmentStatus;
import by.epam.ts.controller.constant_attribute.RequestAtribute;

public class TreatmentStatusInspector {

	public boolean checkTreatmentStatus(HttpServletRequest request) {
		
		String treatmentStatus = request.getParameter(RequestAtribute.CURRENT_TREATMENT_STATUS);
		return (treatmentStatus != null && !treatmentStatus.isEmpty()
				&& !treatmentStatus.equals(TreatmentStatus.CANCELED.getStatusValue())
				&& !treatmentStatus.equals(TreatmentStatus.COMPLETED.getStatusValue()));
	}
}
