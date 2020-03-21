package src.main.java.by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import src.main.java.by.epam.ts.command.ActionCommand;
import src.main.java.by.epam.ts.servlet.manager.ConfigurationManager;

public class EmptyCommand implements ActionCommand{

	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.login");
		return page;
	}

}
