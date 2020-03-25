package by.epam.ts.servlet.actionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.command.CommandEnum;
import by.epam.ts.command.commandImpl.EmptyCommand;
import by.epam.ts.command.commandImpl.LoginCommand;
import by.epam.ts.command.commandImpl.LogoutCommand;
import by.epam.ts.command.commandImpl.ShowTreatmentCommand;
import by.epam.ts.command.commandImpl.SignUpCommand;
import by.epam.ts.command.commandImpl.WrongRequestCommand;
import by.epam.ts.service.serviceFactory.ServiceFactory;

public class ActionFactory {
	
	private final Map<CommandEnum, ActionCommand> repository = new HashMap<>();
	private ServiceFactory factory;
	
	public ActionFactory(ServiceFactory factory) {
		this.factory = factory;
		repository.put(CommandEnum.LOGIN, new LoginCommand(factory));
		repository.put(CommandEnum.LOGOUT, new LogoutCommand(factory));
		repository.put(CommandEnum.SHOW_TREATMENT, new ShowTreatmentCommand(factory));
		repository.put(CommandEnum.SIGN_UP, new SignUpCommand(factory));
		repository.put(CommandEnum.WRONG_REQUEST, new WrongRequestCommand(factory));
	}
	
	public ActionCommand defineCommand(HttpServletRequest request) {
		
		ActionCommand current = new EmptyCommand(factory);
		String action = request.getParameter("command");
		if (action == null || action.isEmpty()) {
			return current;
		}
		
		List<String> names = new ArrayList<String>();
		for(CommandEnum one: CommandEnum.values()) {
			names.add(one.name());
		}
		if (!names.contains(action.toUpperCase())) {
			current = repository.get(CommandEnum.WRONG_REQUEST);
			return current;
		}
		CommandEnum command = CommandEnum.valueOf(action.toUpperCase());
		current = repository.get(command);
		return current;
	}

}
