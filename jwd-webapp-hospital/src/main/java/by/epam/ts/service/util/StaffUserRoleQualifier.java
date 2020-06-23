package by.epam.ts.service.util;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.role.UserRole;
import by.epam.ts.bean.specialty.Specialty;
import by.epam.ts.service.exception.ServiceException;

public class StaffUserRoleQualifier {
	
	public UserRole qualifyStaffUserRole(MedicalStaff staff) throws ServiceException {
		Specialty specialty = staff.getSpecialty();
		
		UserRole userRole;
		switch (specialty) {
		case DOCTOR:
			userRole = UserRole.DOCTOR;
			break;
		case NURSE:
			userRole = UserRole.NURSE;
			break;
		default:
			throw new ServiceException("Error when calling qualifyStaffUserRole(MedicalStaff staff) from UserRoleQualifier. Specialty is undefined");
		}
		return userRole;
	}
}
