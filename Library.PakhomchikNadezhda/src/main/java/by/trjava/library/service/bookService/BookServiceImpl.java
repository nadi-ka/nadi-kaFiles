package by.trjava.library.service.bookService;

import by.trjava.library.dal.bookDao.BookDao;
import by.trjava.library.dal.daoFactory.DaoFactory;
import by.trjava.library.dal.daoFactory.DaoFactoryImpl;
import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.beans.book.Book;
import by.trjava.library.beans.book.BookCategory;
import by.trjava.library.beans.user.User;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.validation.BookValidator;
import by.trjava.library.service.validation.UserValidator;

import java.util.List;

public class BookServiceImpl implements BookService {

    DaoFactory factory = DaoFactoryImpl.getInstance();
    BookDao bookDao = factory.getBookDao();

    public void addBook(Book book, User userWhoPerform) throws ServiceException {
        if (UserValidator.isAdministrator(userWhoPerform)) {
            if (book != null && BookValidator.isCorrectAuthor(book.getAuthor()) &&
                    BookValidator.isCorrectTitle(book.getTitle()) &&
                    BookValidator.isCorrectYearOfEdition(book.getYearOfEdition()) &&
                    BookValidator.isCorrectPrice(book.getPrise()) &&
                    BookValidator.isCorrectDescription(book.getDescription())) {
                List<Book> books;
                try {
                    books = bookDao.getAllBooks();
                    if (BookValidator.isUniqueId(book.getId(), books)) {
                        books.add(book);
                        bookDao.addBooks(books);
                    } else throw new ServiceException("Book with the same id is already exist in the base! " +
                            book.getId());
                } catch (DAOException ex) {
                    throw new ServiceException("The book cannot be added to file! " + book.getTitle(), ex);
                }
            } else {
                throw new ServiceException("Incorrect data! The book wasn't added!");
            }
        } else {
            throw new ServiceException("Permission of administrator wasn't confirmed!");
        }
    }

    public void updateBook(Book currentBook, User userWhoPerform, BookCategory newCategory, String newAuthor,
                           String newTitle, int newYearOfEdition, double newPrise, boolean isPopular,
                           String newDescription) throws ServiceException {
        List<Book> list;
        if (UserValidator.isAdministrator(userWhoPerform)) {
            if (currentBook != null && BookValidator.isCorrectBookCategory(newCategory) &&
                    BookValidator.isCorrectAuthor(newAuthor) &&
                    BookValidator.isCorrectTitle(newTitle) && BookValidator.isCorrectYearOfEdition(newYearOfEdition) &&
                    BookValidator.isCorrectPrice(newPrise) && BookValidator.isCorrectDescription(newDescription)) {
                try {
                    boolean wasUpdated = false;
                    list = bookDao.getAllBooks();
                    for (Book book : list) {
                        if (book.getId().equals(currentBook.getId())) {
                            book.setCategory(newCategory);
                            book.setAuthor(newAuthor);
                            book.setTitle(newTitle);
                            book.setYearOfEdition(newYearOfEdition);
                            book.setPrise(newPrise);
                            book.setPopular(isPopular);
                            book.setDescription(newDescription);
                            wasUpdated = true;
                            break;
                        }
                    }
                    if (!wasUpdated) {
                        throw new ServiceException("The book wasn't found in the base! The operation is impossible.");
                    }
                    bookDao.addBooks(list);

                } catch (DAOException ex) {
                    throw new ServiceException("The book wasn't updated! " + "id: " + currentBook.getId() +
                            "title: " + currentBook.getTitle(), ex);
                }
            } else {
                throw new ServiceException("Incorrect data! The book wasn't updated.");
            }
        } else {
            throw new ServiceException("Permission for this operation wasn't confirmed!");
        }
    }

    public boolean deleteBookById(String id, User userWhoPerform) throws ServiceException {
        if (UserValidator.isAdministrator(userWhoPerform)) {
            try {
                return bookDao.deleteBookById(id);
            } catch (DAOException ex) {
                throw new ServiceException("The book wasn't deleted! " + id, ex);
            }
        } else {
            throw new ServiceException("Permission of administrator wasn't confirmed!");
        }
    }

    public String readBook(Book book) throws ServiceException {
        Book bookToRead;
        if (book != null) {
            try {
                bookToRead = bookDao.getBookById(book.getId());
                if (bookToRead != null) {
                    return bookToRead.getDescription();
                }
                throw new ServiceException("Nothing was found by your request.");
            } catch (DAOException ex) {
                throw new ServiceException("The book wasn't found! " + book.getTitle(), ex);
            }
        } else {
            throw new ServiceException("Incorrect data in request! Null value.");
        }
    }

    public Book findBookById(String id) throws ServiceException {
        Book book;
        if (id != null) {
            try {
                book = bookDao.getBookById(id);
                if (book != null) {
                    return book;
                }
                throw new ServiceException("The book with such id wasn't found in base!" + " id: " + id);
            } catch (DAOException ex) {
                throw new ServiceException("The book with such id wasn't found in base!" + " id: " + id, ex);
            }
        } else {
            throw new ServiceException("Incorrect id in request!" + " id: " + id);
        }
    }

    public List<Book> findBookByTitle(String title) throws ServiceException {
        List<Book> list;
        if (BookValidator.isCorrectTitle(title)) {
            try {
                list = bookDao.getBooksByTitle(title);
                if (!list.isEmpty()) {
                    return list;
                }
                throw new ServiceException("Nothing was found by your request. " + "Title: " + title);
            } catch (DAOException ex) {
                throw new ServiceException("Nothing was found by your request. " + "Title: " + title, ex);
            }
        } else {
            throw new ServiceException("Incorrect title as a function's parameter!" + title);
        }
    }

    public List<Book> findBookByAuthor(String author) throws ServiceException {
        List<Book> list;
        if (BookValidator.isCorrectAuthorForRequest(author)) {
            try {
                list = bookDao.getBooksByAuthor(author);
                if (!list.isEmpty()) {
                    return list;
                }
                throw new ServiceException("Nothing was found by your request. " + "Author: " + author);
            } catch (DAOException ex) {
                throw new ServiceException("Nothing was found by your request. " + "Author: " + author, ex);
            }
        } else {
            throw new ServiceException("Incorrect name of the author as a function's parameter!" + author);
        }
    }

}
