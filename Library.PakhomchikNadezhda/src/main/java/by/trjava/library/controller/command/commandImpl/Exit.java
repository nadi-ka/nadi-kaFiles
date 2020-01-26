package by.trjava.library.controller.command.commandImpl;

import by.trjava.library.controller.command.Command;

public class Exit implements Command {
    @Override
    public String execute(String request) {
        String response;
        response = "Good bye! Thank you for using our application!";
        return response;
    }
}
