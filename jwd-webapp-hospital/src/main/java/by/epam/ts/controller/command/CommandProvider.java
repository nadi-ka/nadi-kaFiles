package by.epam.ts.controller.command;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import by.epam.ts.controller.command.impl.ChangeLanguageCommand;
import by.epam.ts.controller.command.impl.GiveConsentCommand;
import by.epam.ts.controller.command.impl.LoginCommand;
import by.epam.ts.controller.command.impl.LogoutCommand;
import by.epam.ts.controller.command.impl.ShowErrorPageCommand;
import by.epam.ts.controller.command.impl.ShowIndexPageCommand;
import by.epam.ts.controller.command.impl.ShowPatientMainPageCommand;
import by.epam.ts.controller.command.impl.ShowSignUpPageCommand;
import by.epam.ts.controller.command.impl.ShowStaffMainPageCommand;
import by.epam.ts.controller.command.impl.ShowTreatmentCommand;
import by.epam.ts.controller.command.impl.SignUpCommand;
import by.epam.ts.controller.command.impl.WrongRequestCommand;

public final class CommandProvider {
	
	private final static CommandProvider instance = new CommandProvider();	
	private final Map<CommandEnum, Command> repository = new HashMap<>();
	
	private CommandProvider() {
		repository.put(CommandEnum.LOGIN, new LoginCommand());
		repository.put(CommandEnum.LOGOUT, new LogoutCommand());
		repository.put(CommandEnum.SHOW_TREATMENT, new ShowTreatmentCommand());
		repository.put(CommandEnum.SIGN_UP, new SignUpCommand());
		repository.put(CommandEnum.SHOW_PATIENT_MAIN_PAGE, new ShowPatientMainPageCommand());
		repository.put(CommandEnum.SHOW_STAFF_MAIN_PAGE, new ShowStaffMainPageCommand());
		repository.put(CommandEnum.SHOW_INDEX_PAGE, new ShowIndexPageCommand());
		repository.put(CommandEnum.SHOW_SIGNUP_PAGE, new ShowSignUpPageCommand());
		repository.put(CommandEnum.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		repository.put(CommandEnum.SHOW_ERROR_PAGE, new ShowErrorPageCommand());
		repository.put(CommandEnum.GIVE_CONSENT, new GiveConsentCommand());
		repository.put(CommandEnum.WRONG_REQUEST, new WrongRequestCommand());
	}
	
	public static CommandProvider getInstanse() {
		return instance;
	}
	
	public Command defineCommand(HttpServletRequest request) {
		
		Command current;
		String action = request.getParameter("command");
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
