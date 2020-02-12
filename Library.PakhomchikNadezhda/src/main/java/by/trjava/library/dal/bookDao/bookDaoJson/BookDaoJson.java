package by.trjava.library.dal.bookDao.bookDaoJson;

import by.trjava.library.dal.bookDao.BookDao;
import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.bean.book.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BookDaoJson implements BookDao {
    private File file;

    public BookDaoJson(String baseFile) {
        file = new File(baseFile);
    }

    public void addBook(Book book) throws DAOException {
        try {
            List<Book> books = getAllBooks();
            books.add(book);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, books);
        } catch (IOException ex) {
            throw new DAOException("The book wasn't added to file!", ex);
        }
    }

    public void addBooks(List<Book> books) throws DAOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, books);
        } catch (IOException ex) {
            throw new DAOException("The list of books wasn't added to file!", ex);
        }

    }

    public List<Book> getAllBooks() throws DAOException {
        List<Book> books;
        try {
            ObjectMapper mapper = new ObjectMapper();
            books = mapper.readValue(file, new TypeReference<List<Book>>() {
            });
        } catch (IOException ex) {
            throw new DAOException("Extracting from file is impossible!", ex);
        }
        return books;
    }

    public Book getBookById(String id) throws DAOException {
        Book bookById = null;
        boolean wasFound = false;
        try {
            List<Book> books;
            ObjectMapper mapper = new ObjectMapper();
            books = mapper.readValue(file, new TypeReference<List<Book>>() {
            });
            for (Book book : books) {
                if (book.getId().equals(id)) {
                    bookById = book;
                    wasFound = true;
                    break;
                }
            }
        } catch (IOException ex) {
            throw new DAOException("The book wasn't found! Reading from the file is impossible.", ex);
        }
        if (!wasFound) {
            throw new DAOException("The book with such id is not exist in the base!");
        }
        return bookById;
    }

    public List<Book> getBooksByAuthor(String author) throws DAOException {
        List<Book> booksByAuthor = new ArrayList<Book>();
        try {
            List<Book> books;
            ObjectMapper mapper = new ObjectMapper();
            books = mapper.readValue(file, new TypeReference<List<Book>>() {
            });
            for (Book book : books) {
                if (book.getAuthor().contains(author)) {
                    booksByAuthor.add(book);
                }
            }
        } catch (IOException ex) {
            throw new DAOException("Reading from the file is impossible.", ex);
        }
        return booksByAuthor;
    }

    public List<Book> getBooksByTitle(String title) throws DAOException {
        List<Book> booksByTitle = new ArrayList<Book>();
        try {
            List<Book> books;
            ObjectMapper mapper = new ObjectMapper();
            books = mapper.readValue(file, new TypeReference<List<Book>>() {
            });
            for (Book book : books) {
                if (book.getTitle().contains(title)) {
                    booksByTitle.add(book);
                }
            }
        } catch (IOException ex) {
            throw new DAOException("Reading from the file is impossible.", ex);
        }
        return booksByTitle;
    }

    public boolean deleteBookById(String id) throws DAOException {
        boolean wasRemoved = false;
        try {
            List<Book> books = getAllBooks();
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getId().equals(id)) {
                    books.remove(books.get(i));
                    wasRemoved = true;
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, books);
        } catch (IOException ex) {
            throw new DAOException("The book wasn't deleted! " + id, ex);
        }
        return wasRemoved;
    }
}
