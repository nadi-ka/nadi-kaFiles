package by.trjava.library.controller.command.impl;

import by.trjava.library.beans.book.Book;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.ServiceFactoryImpl;

import java.util.ArrayList;
import java.util.List;

public class FindBook implements Command {
    @Override
    public String execute(String request) {
        String parameterToSearch;
        String response;

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        BookService bookService = factory.getBookService();
        if (request.contains("author")) {
            parameterToSearch = Parser.getParameterValue(request, "author");
            try {
                List<Book> books = bookService.findBookByAuthor(parameterToSearch);
                response = "The result of the search: " + books;
            } catch (ServiceException ex) {
                response = "Nothing was found by your request. Author: " + parameterToSearch;
            }
        } else if (request.contains("title")) {
            parameterToSearch = Parser.getParameterValue(request, "title");
            try {
                List<Book> books = bookService.findBookByTitle(parameterToSearch);
                response = "The result of the search: " + books;
            } catch (ServiceException ex) {
                response = "Nothing was found by your request. Title: " + parameterToSearch;
            }
        } else if (request.contains("id")) {
            parameterToSearch = Parser.getParameterValue(request, "id");
            try {
                Book book = bookService.findBookById(parameterToSearch);
                response = "The result of search: " + book.toString();
            } catch (ServiceException ex) {
                response = "Nothing was found by your request. Id: " + parameterToSearch;
            }
        } else {
            response = "Wrong request. Error during the operation.";
        }
        return response;
    }
}
