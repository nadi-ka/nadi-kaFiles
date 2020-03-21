package src.main.java.by.epam.ts.command;

import src.main.java.by.epam.ts.command.commandImpl.LoginCommand;
import src.main.java.by.epam.ts.command.commandImpl.LogoutCommand;
import src.main.java.by.epam.ts.command.commandImpl.SignUpCommand;
import src.main.java.by.epam.ts.command.commandImpl.WrongRequestCommand;

public enum CommandEnum {

	LOGIN
	{
		{
			this.command = new LoginCommand();
		}
	},LOGOUT
	{
		{
			this.command = new LogoutCommand();
		}
	}, SIGN_UP
	{
		{
			this.command = new SignUpCommand();
		}
	}, WRONG_REQUEST
	{
		{
			this.command = new WrongRequestCommand();
		}
	};
	
	ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}

}
