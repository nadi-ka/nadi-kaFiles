package src.main.java.by.epam.ts.servlet.actionFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import src.main.java.by.epam.ts.command.ActionCommand;
import src.main.java.by.epam.ts.command.CommandEnum;
import src.main.java.by.epam.ts.command.commandImpl.EmptyCommand;

public class ActionFactory {
	
	public ActionCommand defineCommand(HttpServletRequest request) {
		
		ActionCommand current = new EmptyCommand();
		String action = request.getParameter("command");
		if (action == null || action.isEmpty()) {
			return current;
		}
		
		List<String> names = new ArrayList<String>();
		for(CommandEnum one: CommandEnum.values()) {
			names.add(one.name());
		}
		if (!names.contains(action.toUpperCase())) {
			CommandEnum command = CommandEnum.WRONG_REQUEST;
			current = command.getCurrentCommand();
			return current;
		}
		CommandEnum command = CommandEnum.valueOf(action.toUpperCase());
		current = command.getCurrentCommand();
		return current;
	}

}
