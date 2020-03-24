package by.epam.ts.command;

import by.epam.ts.command.commandImpl.LoginCommand;
import by.epam.ts.command.commandImpl.LogoutCommand;
import by.epam.ts.command.commandImpl.ShowTreatment;
import by.epam.ts.command.commandImpl.SignUpCommand;
import by.epam.ts.command.commandImpl.WrongRequestCommand;

public enum CommandEnum {

	LOGIN ,
	LOGOUT ,
	SIGN_UP ,
	SHOW_TREATMENT ,
	WRONG_REQUEST ;

}
