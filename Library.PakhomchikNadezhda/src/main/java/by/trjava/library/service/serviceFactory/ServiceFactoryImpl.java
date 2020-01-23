package by.trjava.library.service.serviceFactory;

import by.trjava.library.service.bookService.BookService;
import by.trjava.library.service.bookService.BookServiceImpl;
import by.trjava.library.service.userService.UserService;
import by.trjava.library.service.userService.UserServiceImpl;

public final class ServiceFactoryImpl implements ServiceFactory{
    private final static ServiceFactoryImpl instance = new ServiceFactoryImpl();
    private UserServiceImpl userService;
    private BookServiceImpl bookService;

    private ServiceFactoryImpl() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return new UserServiceImpl();
    }

    public BookService getBookService() {
        return new BookServiceImpl();
    }


}
