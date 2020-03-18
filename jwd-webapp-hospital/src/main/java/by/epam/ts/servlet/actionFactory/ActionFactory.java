package by.epam.ts.servlet.actionFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.command.CommandEnum;
import by.epam.ts.command.commandImpl.EmptyCommand;

public class ActionFactory {
	
	public ActionCommand defineCommand(HttpServletRequest request) {
		
		ActionCommand current= new EmptyCommand();
		String action = request.getParameter("command");
		if (action == null || action.isEmpty()) {
			return current;
		}
		
		List<String> names = new ArrayList<String>();
		for(CommandEnum one: CommandEnum.values()) {
			names.add(one.name());
		}
		if (!names.contains(action)) {
			CommandEnum currentEnum = CommandEnum.WRONG_REQUEST;
			current = currentEnum.getCurrentCommand();
			return current;
		}
		CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
		current = currentEnum.getCurrentCommand();
		return current;
	}

}
