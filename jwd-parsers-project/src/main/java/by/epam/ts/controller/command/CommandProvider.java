package by.epam.ts.controller.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.controller.command.impl.GetErrorPage;
import by.epam.ts.controller.command.impl.GetIndexPage;
import by.epam.ts.controller.command.impl.ParseCommand;
import by.epam.ts.controller.command.impl.UploadFileCommand;
import by.epam.ts.controller.command.impl.WrongRequestCommand;
import by.epam.ts.controller.constant_attribute.RequestAttribute;

public final class CommandProvider {
	private final static CommandProvider instance = new CommandProvider();
	private final Map<CommandEnum, ActionCommand> repository = new HashMap<>();

	private CommandProvider() {
		repository.put(CommandEnum.PARSE_DOCUMENT, new ParseCommand());
		repository.put(CommandEnum.UPLOAD_FILE, new UploadFileCommand());
		repository.put(CommandEnum.GET_ERROR_PAGE, new GetErrorPage());
		repository.put(CommandEnum.GET_INDEX_PAGE, new GetIndexPage());
		repository.put(CommandEnum.WRONG_REQUEST, new WrongRequestCommand());
	}

	public static CommandProvider getInstance() {
		return instance;
	}

	public ActionCommand defineCommand(HttpServletRequest request) {
		ActionCommand current;
		String action = request.getParameter(RequestAttribute.COMMAND);
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
