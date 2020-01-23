package by.trjava.library.controller.command.impl;

import by.trjava.library.beans.book.Book;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.ServiceFactoryImpl;

public class ReadBook implements Command {
    @Override
    public String execute(String request) {
        String bookId = Parser.getParameterValue(request, "id");
        String response;

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        BookService bookService = factory.getBookService();
        try {
            Book book = bookService.findBookById(bookId);
            response = bookService.readBook(book);
        }
        catch (ServiceException ex) {
            response = "Error during operation.";
        }
        return response;
    }
}
