package by.trjava.library.controller.command.commandImpl;

import by.trjava.library.controller.command.Command;

public class WrongRequest implements Command {
    @Override
    public String execute(String request) {
        String response;
        response = "Wrong request. Please, try to enter a command again.";
        return response;
    }
}
