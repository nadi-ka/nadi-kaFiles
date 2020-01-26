package by.trjava.library.controller.command.commandImpl;

import by.trjava.library.bean.user.User;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

public class DeleteBookById implements Command {
    @Override
    public String execute(String request) {
        String id = Parser.getParameterValue(request, "id");
        String userLogin = Parser.getParameterValue(request, "login");
        String userPassword = Parser.getParameterValue(request, "password");
        String response;

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        BookService bookService = factory.getBookService();
        UserService userService = factory.getUserService();
        try {
            User userWhoPerform = userService.SignIn(userLogin, userPassword);
            bookService.deleteBookById(id, userWhoPerform);
            response = "The operation was completed successfully.";
        } catch (ServiceException ex) {
            response = "Error during the operation. The book wasn't deleted.";
        }
        return response;
    }
}
