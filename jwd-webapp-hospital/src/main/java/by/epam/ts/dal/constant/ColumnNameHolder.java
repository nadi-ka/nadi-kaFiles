package by.epam.ts.dal.constant;

public final class ColumnNameHolder {

	/**
	 * Column names for the table 'users'
	 */

	public final static String USERS_ID_STAFF = "id_medical_staff";
	public final static String USERS_ID_PATIENT = "id_patient";
	public final static String USERS_LOGIN = "login";
	public final static String USERS_PASSWORD = "password";
	public final static String USERS_ROLE = "role";
	public final static String USERS_STATUS = "user_status";

	/**
	 * Column names for the table 'medical-staff'
	 */

	public final static String STAFF_ID = "id";
	public final static String STAFF_SPECIALTY = "specialty";
	public final static String STAFF_SURNAME = "surname";
	public final static String STAFF_NAME = "name";
	public final static String STAFF_EMAIL = "email";

	/**
	 * Column names for the table 'medical-staff'
	 */

	public final static String PATIENT_ID = "id";
	public final static String PATIENT_SURNAME = "surname";
	public final static String PATIENT_NAME = "name";
	public final static String PATIENT_BIRTH_DATE = "birth_date";
	public final static String PATIENT_EMAIL = "email";

	/**
	 * Column names for the table 'treatment'
	 */

	public final static String TREATMENT_ID_APPOINTMENT = "id_appointment";
	public final static String TREATMENT_TYPE = "treatment_type";
	public final static String TREATMENT_NAME = "treatment_name";
	public final static String TREATMENT_ID_ASSIGNED = "id_assigned_by";
	public final static String TREATMENT_DATE_BEGINNING = "date_begin/holding";
	public final static String TREATMENT_DATE_FINISHING = "date_finish";
	public final static String TREATMENT_CONSENT = "consent";

	/**
	 * Column names for the table 'current-treatment'
	 */

	public final static String CURRENT_ID_PROCEDURE = "id_procedure";
	public final static String CURRENT_DATE = "date";
	public final static String CURRENT_ID_PERFORMER = "id_performer";
	public final static String CURRENT_STATUS = "status";

	/**
	 * Column names for the table 'id-m2m-code'
	 */

	public final static String M2M_CODE_DIAGNOSIS = "code_diagnosis";
	public final static String M2M_PRIMARY = "is_primary";
	public final static String M2M_SETTING_DATE = "setting_date";

	/**
	 * Column names for the table 'diagnosis'
	 */

	public final static String DIAGNOSIS_CODE = "code";
	public final static String DIAGNOSIS_NAME = "name";
	public final static String DIAGNOSIS_BED_DAYS = "bed_days";

	/**
	 * Column names for the table 'hospitalization'
	 */

	public final static String HOSPITALIZATION_ID_HISTORY = "id_history";
	public final static String HOSPITALIZATION_ENTRY_DATE = "entry_date";
	public final static String HOSPITALIZATION_DISCHARGE_DATE = "discharge_date";

}
