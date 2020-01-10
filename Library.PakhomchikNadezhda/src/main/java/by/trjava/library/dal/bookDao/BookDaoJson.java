package by.trjava.library.dal.bookDao;

import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.bean.book.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJson implements BookDao {
    private final static String baseFile = "D:\\Workspace\\book\\book.json";

    File file = new File(baseFile);

    public void addBook(Book book) throws DAOException {
        try {
            List<Book> books = getAllBooks();

            // Here you should generate a new Book Id
            // book.setId(generator.generateId())

            books.add(book);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, books);
        } catch (IOException ex) {
            throw new DAOException("The book wasn't added to file! " + baseFile);
        }
    }

    public void addBooks(List<Book> books) throws DAOException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Loop through books and generate the Ids
            // book.setId(generator.generateId())

            mapper.writeValue(file, books);
        } catch (IOException ex) {
            throw new DAOException("The list of books wasn't added to file! " + baseFile);
        }

    }

    public List<Book> getAllBooks() throws DAOException {
        List<Book> books;
        try {
            ObjectMapper mapper = new ObjectMapper();
            books = mapper.readValue(file, new TypeReference<List<Book>>() {
            });
        } catch (IOException ex) {
            throw new DAOException("Extracting from file is impossible! " + baseFile);
        }
        return books;
    }

    public Book getBookById(long id) throws DAOException, NullPointerException {
        Book bookById = null;
        try {
            List<Book> books;
            ObjectMapper mapper = new ObjectMapper();
            books = mapper.readValue(file, new TypeReference<List<Book>>() {
            });
            for (Book book : books) {
                if (book.getId() == id) {
                    bookById = book;
                    break;
                }
            }
        } catch (IOException ex) {
            throw new DAOException("Extracting from file is impossible! " + baseFile);
        }
        return bookById;
    }

    public List<Book> getBooksByAuthor(String author) throws DAOException, NullPointerException {
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
            throw new DAOException("Extracting from file is impossible! " + baseFile);
        }
        return booksByAuthor;
    }

    public List<Book> getBooksByTitle(String title) throws DAOException, NullPointerException {
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
            throw new DAOException("Extracting from file is impossible! " + baseFile);
        }
        return booksByTitle;
    }

    public boolean deleteBookById (long id) throws DAOException{
        boolean wasRemoved = false;
        try {
            List<Book> books = getAllBooks();
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getId() == id) {
                    books.remove(books.get(i));
                    wasRemoved = true;
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, books);
        } catch (IOException ex) {
            throw new DAOException("The user wasn't added to file! " + baseFile);
        }
        return wasRemoved;
    }
}
