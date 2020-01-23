package by.trjava.library.controller;

import by.trjava.library.controller.command.Command;

public final class Controller {
    private final CommandProvider provider = new CommandProvider();
    private final char paramDelimiter = ';';

    public String executeRequest(String request) {
        String commandName;
        Command executionCommand;

        commandName = request.substring(0, request.indexOf(paramDelimiter));
        executionCommand = provider.getCommand(commandName);
        String response;
        response = executionCommand.execute(request);
        return response;
    }
}
