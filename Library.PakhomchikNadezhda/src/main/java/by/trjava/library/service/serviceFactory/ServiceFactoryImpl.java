package by.trjava.library.service.serviceFactory;

import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.bookService.BookServiceImpl;
import by.trjava.library.service.userService.UserService;
import by.trjava.library.service.userService.UserServiceImpl;

public final class ServiceFactoryImpl implements ServiceFactory{
    private final static ServiceFactoryImpl instance = new ServiceFactoryImpl();

    // Since you were told to make ServiceFactoryImpl singleton, then you should make use of it. E.g. initialize
    // the variable here and return them. It will make you to have always the same service object:
    private UserServiceImpl userService = new UserServiceImpl();;
    private BookServiceImpl bookService = new BookServiceImpl();

    private ServiceFactoryImpl() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public BookService getBookService() {
        return bookService;
    }

}
