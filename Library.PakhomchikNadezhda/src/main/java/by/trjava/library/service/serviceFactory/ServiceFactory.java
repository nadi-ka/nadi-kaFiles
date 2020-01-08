package by.trjava.library.service.serviceFactory;

import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.userService.UserService;

public interface ServiceFactory {

    UserService getUserService();

    BookService getBookService();
}
