package by.trjava.library.controller.model;

import by.trjava.library.controller.command.CommandName;

public class RequestModel {
    private final CommandName commandName;
    private final String parameters;

    public RequestModel(CommandName commandName, String parameters){
        this.commandName = commandName;
        this.parameters = parameters;
    }

    public CommandName getCommand(){
        return commandName;
    }

    public String getParameters(){
        return parameters;
    }

}
