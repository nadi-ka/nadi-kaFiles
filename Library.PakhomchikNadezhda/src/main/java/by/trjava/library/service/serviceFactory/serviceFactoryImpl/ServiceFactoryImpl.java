package by.trjava.library.service.serviceFactory.serviceFactoryImpl;

import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.bookService.bookServiceImpl.BookServiceImpl;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.userService.UserService;
import by.trjava.library.service.userService.userServiceImpl.UserServiceImpl;

public final class ServiceFactoryImpl implements ServiceFactory {
    private final static ServiceFactoryImpl instance = new ServiceFactoryImpl();
    private UserService userService = new UserServiceImpl();
    private BookService bookService = new BookServiceImpl();

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
