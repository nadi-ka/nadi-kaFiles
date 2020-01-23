package by.trjava.library.controller.command.impl;

import by.trjava.library.beans.book.Book;
import by.trjava.library.beans.book.BookCategory;
import by.trjava.library.beans.user.User;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

public class AddBook implements Command {
    @Override
    public String execute(String request) {
        String response;
        String bookCategory = Parser.getParameterValue(request, "category");
        BookCategory category;
        try {
            category = BookCategory.valueOf(bookCategory);
        } catch (IllegalArgumentException | NullPointerException ex) {
            category = BookCategory.UNKNOWN;
        }
        String author = Parser.getParameterValue(request, "author");
        String title = Parser.getParameterValue(request, "title");
        String yearOfEdition = Parser.getParameterValue(request, "year");
        int year;
        try {
            year = Integer.parseInt(yearOfEdition);
        } catch (NumberFormatException ex) {
            year = 0;
        }
        String prise = Parser.getParameterValue(request, "prise");
        double priseDouble;
        try {
            priseDouble = Double.parseDouble(prise);
        } catch (NumberFormatException ex) {
            priseDouble = 0.0;
        }
        String popular = Parser.getParameterValue(request, "popular");
        boolean isPopular;
        try {
            isPopular = Boolean.parseBoolean(popular);
        } catch (NumberFormatException ex) {
            isPopular = false;
        }
        String description = Parser.getParameterValue(request, "description");

        Book book = new Book(category, author, title, year, priseDouble, isPopular, description);

        String userLogin = Parser.getParameterValue(request, "login");
        String userPassword = Parser.getParameterValue(request, "password");

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        BookService bookService = factory.getBookService();
        UserService userService = factory.getUserService();
        try {
            User userWhoPerform = userService.SignIn(userLogin, userPassword);
            bookService.addBook(book, userWhoPerform);
            response = "The operation was completed successfully.";
        } catch (ServiceException ex) {
            response = "Error during the operation. The book wasn't add.";
        }
        return response;
    }
}
