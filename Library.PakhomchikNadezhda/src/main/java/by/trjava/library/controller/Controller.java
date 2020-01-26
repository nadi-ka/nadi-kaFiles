package by.trjava.library.controller;

import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.model.RequestModel;

public final class Controller {
    private final CommandProvider provider = new CommandProvider();
    private final char paramDelimiter = ';';

    // Make it work with RequestModel
    public String executeRequest(RequestModel request) {
        String commandName;
        Command executionCommand;

        commandName = request.substring(0, request.indexOf(paramDelimiter));
        executionCommand = provider.getCommand(commandName);
        String response;
        response = executionCommand.execute(request);
        return response;
    }
}
