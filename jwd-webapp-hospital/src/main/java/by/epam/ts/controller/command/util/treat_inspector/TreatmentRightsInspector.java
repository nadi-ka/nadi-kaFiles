package by.epam.ts.controller.command.util.treat_inspector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.ts.bean.role.UserRole;
import by.epam.ts.bean.treat_type.TreatmentType;
import by.epam.ts.controller.constant_attribute.SessionAtribute;

/**
 * The class provides method for checking, if the performing of the concrete 
 * procedure is allowed for the performer of concrete specialty;
 */

public class TreatmentRightsInspector {

	public TreatmentRightsInspector() {
	}

	public boolean inspectRightsForCurrentProcedure(HttpServletRequest request, TreatmentType type) {

		HttpSession session = request.getSession(false);
		UserRole userRole = null;

		if (session == null) {
			return false;

		} else {
			/*
			 * Check the user rights according to the rule: only doctor is allowed to
			 * perform surgical treatment, all other types of procedures are allowed for
			 * nurse as well as for doctor;
			 */
			userRole = (UserRole) session.getAttribute(SessionAtribute.USER_ROLE);

			if (userRole == null || userRole == UserRole.PATIENT) {
				return false;

			} else if ((type == TreatmentType.SURGICAL) && (userRole == UserRole.NURSE)) {
				return false;
			}

			return true;
		}
	}
}
