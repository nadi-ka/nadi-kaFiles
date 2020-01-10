package by.trjava.library.service.bookService;

import by.trjava.library.dal.bookDao.BookDao;
import by.trjava.library.dal.daoFactory.DaoFactory;
import by.trjava.library.dal.daoFactory.JsonDaoFactory;
import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.bean.book.Book;
import by.trjava.library.bean.book.BookCategory;
import by.trjava.library.bean.user.User;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.validation.ValidationBook;
import by.trjava.library.service.validation.ValidationUser;
import java.util.List;

public class BookServiceImpl implements BookService {

    private final DaoFactory factory = JsonDaoFactory.getInstance();
    private final BookDao bookDao = factory.getBookDao();

    public void addBook(Book book, User userWhoPerform) throws ServiceException {
        if (ValidationUser.isAdministrator(userWhoPerform)) {
            if (book!= null && ValidationBook.isCorrectAuthor(book.getAuthor()) &&
                    ValidationBook.isCorrectTitle(book.getTitle()) &&
                    ValidationBook.isCorrectYearOfEdition(book.getYearOfEdition()) &&
                    ValidationBook.isCorrectPrice(book.getPrise()) &&
            ValidationBook.isCorrectDescription(book.getDescription())) {
                try {
                    bookDao.addBook(book);
                } catch (DAOException ex) {
                    throw new ServiceException("The book cannot be added to file! " + book);
                }
            } else {
                throw new ServiceException("Incorrect data! " + book.toString());
            }

        } else {
            throw new ServiceException("Permission of administrator wasn't confirmed!");
        }
    }

    public void updateBook(long id, User userWhoPerform, BookCategory category, String author,
                           String title, int yearOfEdition, double prise, boolean isPopular,
                           String description) throws ServiceException {
        List<Book> list;
        if (ValidationUser.isAdministrator(userWhoPerform)) {
            if (ValidationBook.isCorrectAuthor(author) && ValidationBook.isCorrectTitle(title) &&
                    ValidationBook.isCorrectYearOfEdition(yearOfEdition) && ValidationBook.isCorrectPrice(prise) &&
            ValidationBook.isCorrectDescription(description)) {
                try {
                    list = bookDao.getAllBooks();
                    for (Book book : list) {
                        if (book.getId() == id) {
                            book.setCategory(category);
                            book.setAuthor(author);
                            book.setTitle(title);
                            book.setYearOfEdition(yearOfEdition);
                            book.setPrise(prise);
                            book.setPopular(isPopular);
                            book.setDescription(description);
                            bookDao.addBooks(list);
                        }
                    }
                } catch (DAOException ex) {
                    throw new ServiceException("The Book wasn't updated! " + id);
                }
            } else {
                throw new ServiceException("Incorrect data! " + author + " " + title + " " + yearOfEdition +
                        " " + prise + " " + description);
            }
        } else {
            throw new ServiceException("Permission of administrator wasn't confirmed!");
        }
    }

    public boolean deleteBook(long id, User userWhoPerform) throws ServiceException {
        if (ValidationUser.isAdministrator(userWhoPerform)) {
            try {
                return bookDao.deleteBookById(id);
            } catch (DAOException ex) {
                throw new ServiceException("The book wasn't deleted! " + id);
            }
        } else {
            throw new ServiceException("Permission of administrator wasn't confirmed!");
        }
    }

    public String readAboutBook(Book book) throws ServiceException {
        Book bookToRead;
        if (book != null) {
            try {
                bookToRead = bookDao.getBookById(book.getId());
            } catch (DAOException ex) {
                throw new ServiceException("Book wasn't found! " + book.toString());
            }
        }
        else {
            throw new ServiceException("Incorrect data! " + book.toString());
        }
        return bookToRead.getDescription();
    }

    public List<Book> searchBookByTitle(String title) throws ServiceException {
        List<Book> list;
        if (title != null) {
            try {
                list = bookDao.getBooksByTitle(title);
            }
            catch (DAOException ex) {
                throw new ServiceException("The book wasn't found! " + title);
            }
        }
        else {
            throw new ServiceException("Incorrect title! " + title);
        }
        return list;
    }

    public List<Book> searchBookByAuthor(String author) throws ServiceException {
        List<Book> list;
        if (author != null) {
            try {
                list = bookDao.getBooksByAuthor(author);
                }
            catch (DAOException ex) {
                throw new ServiceException("The book wasn't found! " + author);
            }
        }
        else {
            throw new ServiceException("Incorrect name of author! " + author);
        }
        return list;
    }

}
