package by.trjava.library.service.bookService;

import by.trjava.library.bean.book.Book;
import by.trjava.library.bean.book.BookCategory;
import by.trjava.library.bean.user.User;
import by.trjava.library.service.exceptionService.ServiceException;

import java.util.List;

public interface BookService {

    void addBook(Book book, User userWhoPerform) throws ServiceException;

    void updateBook(long id, User userWhoPerform, BookCategory category, String author, String title, int yearOfEdition,
                    double prise, boolean isPopular, String description) throws ServiceException;

    boolean deleteBook(long id, User userWhoPerform) throws ServiceException;

    String readAboutBook(Book book) throws ServiceException;

    List<Book> searchBookByAuthor(String author) throws ServiceException;

    List<Book> searchBookByTitle(String title) throws ServiceException;

}
