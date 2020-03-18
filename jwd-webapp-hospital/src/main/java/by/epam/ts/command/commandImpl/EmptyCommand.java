package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.servlet.manager.ConfigurationManager;

public class EmptyCommand implements ActionCommand{

	@Override
	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.login");
		return page;
	}

}
