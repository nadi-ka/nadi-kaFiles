package by.epam.ts.controller.command;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import by.epam.ts.controller.command.impl.AddHospitalizationCommand;
import by.epam.ts.controller.command.impl.AddNewDiagnosisCommand;
import by.epam.ts.controller.command.impl.AddNewPatientCommand;
import by.epam.ts.controller.command.impl.AddNewStaffCommand;
import by.epam.ts.controller.command.impl.AddNewTreatmentCommand;
import by.epam.ts.controller.command.impl.AddPatientDiagnosisCommand;
import by.epam.ts.controller.command.impl.ChangeLanguageCommand;
import by.epam.ts.controller.command.impl.CancelTreatmentCommand;
import by.epam.ts.controller.command.impl.DischargePatientCommand;
import by.epam.ts.controller.command.impl.GetCurrentPatientPageCommand;
import by.epam.ts.controller.command.impl.GetDiagnosisPageCommand;
import by.epam.ts.controller.command.impl.GetHospitalizationPageCommand;
import by.epam.ts.controller.command.impl.GetHospitalizationPlanCommand;
import by.epam.ts.controller.command.impl.GetPatientDataPageCommand;
import by.epam.ts.controller.command.impl.GetPrescriptionsPageCommand;
import by.epam.ts.controller.command.impl.GetStaffDataPageCommand;
import by.epam.ts.controller.command.impl.GiveConsentCommand;
import by.epam.ts.controller.command.impl.LoginCommand;
import by.epam.ts.controller.command.impl.LogoutCommand;
import by.epam.ts.controller.command.impl.PerformTreatmentCommand;
import by.epam.ts.controller.command.impl.SearchPatientCommand;
import by.epam.ts.controller.command.impl.SearchStaffCommand;
import by.epam.ts.controller.command.impl.ShowErrorPageCommand;
import by.epam.ts.controller.command.impl.GetIndexPageCommand;
import by.epam.ts.controller.command.impl.GetPatientMainPageCommand;
import by.epam.ts.controller.command.impl.GetSignUpPageCommand;
import by.epam.ts.controller.command.impl.GetStaffMainPageCommand;
import by.epam.ts.controller.command.impl.GetTreatPerformancePageCommand;
import by.epam.ts.controller.command.impl.GetTreatmentPageCommand;
import by.epam.ts.controller.command.impl.GetUpdatePatientDataPageCommand;
import by.epam.ts.controller.command.impl.GetUpdatePersonalDataPageCommand;
import by.epam.ts.controller.command.impl.SignUpCommand;
import by.epam.ts.controller.command.impl.UpdatePatientDataCommand;
import by.epam.ts.controller.command.impl.UpdatePersonalDataCommand;
import by.epam.ts.controller.command.impl.UpdateUserRoleCommand;
import by.epam.ts.controller.command.impl.UpdateUserStatusCommand;
import by.epam.ts.controller.command.impl.WrongRequestCommand;
import by.epam.ts.controller.constant_attribute.RequestAtribute;

public final class CommandProvider {
	
	private final static CommandProvider instance = new CommandProvider();	
	private final Map<CommandEnum, Command> repository = new HashMap<>();
	
	private CommandProvider() {
		repository.put(CommandEnum.LOGIN, new LoginCommand());
		repository.put(CommandEnum.LOGOUT, new LogoutCommand());
		repository.put(CommandEnum.GET_TREATMENT_PAGE, new GetTreatmentPageCommand());
		repository.put(CommandEnum.SIGN_UP, new SignUpCommand());
		repository.put(CommandEnum.GET_PATIENT_MAIN_PAGE, new GetPatientMainPageCommand());
		repository.put(CommandEnum.GET_STAFF_MAIN_PAGE, new GetStaffMainPageCommand());
		repository.put(CommandEnum.GET_INDEX_PAGE, new GetIndexPageCommand());
		repository.put(CommandEnum.GET_SIGNUP_PAGE, new GetSignUpPageCommand());
		repository.put(CommandEnum.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		repository.put(CommandEnum.SHOW_ERROR_PAGE, new ShowErrorPageCommand());
		repository.put(CommandEnum.GIVE_CONSENT, new GiveConsentCommand());
		repository.put(CommandEnum.SEARCH_PATIENT, new SearchPatientCommand());
		repository.put(CommandEnum.SEARCH_STAFF, new SearchStaffCommand());
		repository.put(CommandEnum.ADD_NEW_PATIENT, new AddNewPatientCommand());
		repository.put(CommandEnum.GET_PATIENT_DATA_PAGE, new GetPatientDataPageCommand());
		repository.put(CommandEnum.GET_CURRENT_PATIENT_PAGE, new GetCurrentPatientPageCommand());
		repository.put(CommandEnum.ADD_PATIENT_DIAGNOSIS, new AddPatientDiagnosisCommand());
		repository.put(CommandEnum.GET_DIAGNOSIS_PAGE, new GetDiagnosisPageCommand());
		repository.put(CommandEnum.ADD_NEW_DIAGNOSIS, new AddNewDiagnosisCommand());
		repository.put(CommandEnum.ADD_NEW_TREATMENT, new AddNewTreatmentCommand());
		repository.put(CommandEnum.GET_PRESCRIPTIONS_PAGE, new GetPrescriptionsPageCommand());
		repository.put(CommandEnum.ADD_NEW_STAFF, new AddNewStaffCommand());
		repository.put(CommandEnum.GET_STAFF_DATA_PAGE, new GetStaffDataPageCommand());
		repository.put(CommandEnum.GET_HOSPITALIZATION_PAGE, new GetHospitalizationPageCommand());
		repository.put(CommandEnum.ADD_HOSPITALIZATION, new AddHospitalizationCommand());
		repository.put(CommandEnum.DISCHARGE_PATIENT, new DischargePatientCommand());
		repository.put(CommandEnum.GET_TREAT_PERFORMANCE_PAGE, new GetTreatPerformancePageCommand());
		repository.put(CommandEnum.PERFORM_TREATMENT, new PerformTreatmentCommand());
		repository.put(CommandEnum.GET_HOSPITALIZATION_PLAN, new GetHospitalizationPlanCommand());
		repository.put(CommandEnum.UPDATE_USER_ROLE, new UpdateUserRoleCommand());
		repository.put(CommandEnum.UPDATE_USER_STATUS, new UpdateUserStatusCommand());
		repository.put(CommandEnum.GET_UPDATE_PERSONAL_DATA_PAGE, new GetUpdatePersonalDataPageCommand());
		repository.put(CommandEnum.UPDATE_PERSONAL_DATA, new UpdatePersonalDataCommand());
		repository.put(CommandEnum.GET_UPDATE_PATIENT_DATA_PAGE, new GetUpdatePatientDataPageCommand());
		repository.put(CommandEnum.UPDATE_PATIENT_DATA, new UpdatePatientDataCommand());
		repository.put(CommandEnum.CANCEL_TREATMENT, new CancelTreatmentCommand());
		repository.put(CommandEnum.WRONG_REQUEST, new WrongRequestCommand());
	}
	
	public static CommandProvider getInstance() {
		return instance;
	}
	
	public Command defineCommand(HttpServletRequest request) {
		
		Command current;
		String action = request.getParameter(RequestAtribute.COMMAND);
		if (action == null || action.isEmpty()) {
			current = repository.get(CommandEnum.WRONG_REQUEST);
			return current;
		}
		
		CommandEnum command = CommandEnum.valueOf(action.toUpperCase());
		current = repository.get(command);
		
		if (current == null) {
			current = repository.get(CommandEnum.WRONG_REQUEST);
		}
		
		return current;
	}

}
