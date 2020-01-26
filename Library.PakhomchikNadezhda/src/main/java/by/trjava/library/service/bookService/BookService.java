package by.trjava.library.service.bookService;

import by.trjava.library.bean.book.Book;
import by.trjava.library.bean.book.BookCategory;
import by.trjava.library.bean.user.User;
import by.trjava.library.service.exceptionService.ServiceException;

import java.util.List;

public interface BookService {

    void addBook(Book book, User userWhoPerform) throws ServiceException;

    void updateBook(Book currentBook, User userWhoPerform, BookCategory category, String author, String title,
                    int yearOfEdition, double prise, boolean isPopular, String description) throws ServiceException;

    boolean deleteBookById(String id, User userWhoPerform) throws ServiceException;

    String readBook(Book book) throws ServiceException;

    Book findBookById(String id) throws ServiceException;

    List<Book> findBookByAuthor(String author) throws ServiceException;

    List<Book> findBookByTitle(String title) throws ServiceException;

}
