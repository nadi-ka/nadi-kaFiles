package by.trjava.library.dal.bookDao;

import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.bean.book.Book;

import java.util.List;

public interface BookDao {

    void addBook(Book book) throws DAOException;

    void addBooks(List<Book> books) throws DAOException;

    List<Book> getAllBooks() throws DAOException;

    Book getBookById(String id) throws DAOException;

    List<Book> getBooksByAuthor(String author) throws DAOException;

    List<Book> getBooksByTitle(String title) throws DAOException;

    boolean deleteBookById (String id) throws DAOException;
}
