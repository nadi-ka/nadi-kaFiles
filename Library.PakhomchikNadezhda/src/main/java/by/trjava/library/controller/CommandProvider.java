package by.trjava.library.controller;

import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.command.CommandName;
import by.trjava.library.controller.command.commandImpl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<CommandName, Command>();

    CommandProvider() {
        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put(CommandName.REGISTER, new Register());
        repository.put(CommandName.SET_PERMISSION_ADMINISTRATOR, new SetPermissionAdministrator());
        repository.put(CommandName.UPDATE_USER, new UpdateUser());
        repository.put(CommandName.DELETE_USER, new DeleteUser());
        repository.put(CommandName.FIND_USER_BY_ID, new FindUserById());
        repository.put(CommandName.FIND_USER_BY_SURNAME, new FindUserBySurname());
        repository.put(CommandName.ADD_BOOK, new AddBook());
        repository.put(CommandName.UPDATE_BOOK, new UpdateBook());
        repository.put(CommandName.DELETE_BOOK_BY_ID, new DeleteBookById());
        repository.put(CommandName.READ_BOOK, new ReadBook());
        repository.put(CommandName.FIND_BOOK, new FindBook());
        repository.put(CommandName.EXIT, new Exit());
        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    Command getCommand(String name) {
        CommandName commandName;
        Command command;
        try {
            commandName = CommandName.valueOf(name);
            command = repository.get(commandName);
        }
        catch (IllegalArgumentException | NullPointerException ex) {
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
