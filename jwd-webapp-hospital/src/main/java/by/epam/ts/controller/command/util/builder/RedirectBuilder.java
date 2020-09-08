package by.epam.ts.controller.command.util.builder;

import by.epam.ts.controller.constant_attribute.RequestAtribute;

/**
 * Class for suitable forming the redirect String;
 */

public class RedirectBuilder {

	private String result = "";
	private final static String equalsSign = "=";
	private final static String andSign = "&";

	public RedirectBuilder(String contextPass, String controllerName, String command) {

		StringBuilder builder = new StringBuilder();
		builder.append(contextPass);
		builder.append(controllerName);
		builder.append(RequestAtribute.COMMAND);
		builder.append(equalsSign);
		builder.append(command);
		this.result = builder.toString();

	}

	public RedirectBuilder setPatientId(String patientId) {

		appendAttribute(RequestAtribute.PATIENT_ID, patientId);
		return this;
	}

	public RedirectBuilder setMessage(String message) {

		appendAttribute(RequestAtribute.MESSAGE, message);
		return this;
	}

	public RedirectBuilder setInvalidParameters(String invalidParameters) {

		appendAttribute(RequestAtribute.INVALID_PARAMETERS, invalidParameters);
		return this;
	}

	public RedirectBuilder setSurname(String surname) {

		appendAttribute(RequestAtribute.SURNAME, surname);
		return this;
	}

	public RedirectBuilder setName(String name) {

		appendAttribute(RequestAtribute.NAME, name);
		return this;
	}

	public RedirectBuilder setDateOfBirth(String dateOfBirth) {

		appendAttribute(RequestAtribute.DATE_OF_BIRTH, dateOfBirth);
		return this;
	}

	public RedirectBuilder setEmail(String email) {

		appendAttribute(RequestAtribute.EMAIL, email);
		return this;
	}

	public RedirectBuilder setStaffId(String staffId) {

		appendAttribute(RequestAtribute.STAFF_ID, staffId);
		return this;
	}

	public RedirectBuilder setTreatmentName(String treatmentName) {

		appendAttribute(RequestAtribute.TREATMENT_NAME, treatmentName);
		return this;
	}

	public RedirectBuilder setDiagnosisName(String diagnosisName) {

		appendAttribute(RequestAtribute.NAME_DIAGNOSIS, diagnosisName);
		return this;
	}

	public RedirectBuilder setQueryString(String queryString) {

		appendAttribute(RequestAtribute.QUERY_SEARCH, queryString);
		return this;
	}

	public String getResultString() {

		return result;
	}

	private void appendAttribute(String attributeName, String attributeValue) {

		StringBuilder builder = new StringBuilder();
		builder.append(andSign);
		builder.append(attributeName);
		builder.append(equalsSign);
		builder.append(attributeValue);
		result += builder.toString();

	}

}
