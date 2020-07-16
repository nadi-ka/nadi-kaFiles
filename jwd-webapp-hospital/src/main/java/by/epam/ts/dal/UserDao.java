package by.epam.ts.dal;

import java.util.List;

import by.epam.ts.bean.MedicalStaff;
import by.epam.ts.bean.Patient;
import by.epam.ts.bean.User;

public interface UserDao {

	int createUserPatient(User user) throws DaoException;

	int createUserStaff(User user) throws DaoException;

	MedicalStaff findStaffByEmail(String email) throws DaoException;

	Patient findPatientByEmail(String email) throws DaoException;

	User findUserByLogin(String login) throws DaoException;

	String findLogin(String login) throws DaoException;

	int createNewPatient(Patient patient) throws DaoException;

	int updatePatientPersonalData(Patient patient) throws DaoException;

	List<Patient> findPatientBySurname(String surname) throws DaoException;

	Patient findPatientById(String id) throws DaoException;

	int createNewStaff(MedicalStaff medicalStaff) throws DaoException;

	int updateStaffPersonalData(String surname, String name, String email, String id) throws DaoException;

	MedicalStaff findStaffById(String id) throws DaoException;

	List<MedicalStaff> findUserStaffBySurname(String surname) throws DaoException;

	int updateStaffUserRole(int newRole, String id) throws DaoException;

	int updateUserStatus(boolean newStatus, String id) throws DaoException;

}
