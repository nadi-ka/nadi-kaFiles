package by.epam.ts.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.ts.bean.role.UserRole;
import by.epam.ts.controller.constant_attribute.SessionAtribute;

/*
 * Interface provides default methods for checking the staff rights;
 */

public interface AccessManager {

	default boolean checkStaffRights(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		UserRole userRole = (UserRole) session.getAttribute(SessionAtribute.USER_ROLE);
		if ((userRole != null)
				&& (userRole == UserRole.ADMINISTRATOR || userRole == UserRole.DOCTOR || userRole == UserRole.NURSE)) {
			return true;
		}
		return false;
	}

	default boolean checkDoctorRights(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		UserRole userRole = (UserRole) session.getAttribute(SessionAtribute.USER_ROLE);
		if ((userRole != null) && (userRole == UserRole.ADMINISTRATOR || userRole == UserRole.DOCTOR)) {
			return true;
		}
		return false;
	}

	default boolean checkAdminRights(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		UserRole userRole = (UserRole) session.getAttribute(SessionAtribute.USER_ROLE);
		if (userRole != null && userRole == UserRole.ADMINISTRATOR) {
			return true;
		}
		return false;
	}

}
