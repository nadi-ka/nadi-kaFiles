package by.trjava.library.runner;

import by.trjava.library.dal.bookDao.BookDao;
import by.trjava.library.dal.bookDao.BookDaoJson;
import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.dal.userDao.UserDao;
import by.trjava.library.dal.userDao.UserDaoJson;
import by.trjava.library.bean.book.Book;
import by.trjava.library.bean.book.BookCategory;
import by.trjava.library.bean.user.User;


import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        User user1 = new User("Ivanov", "Ivan", "Shark", "shark777");
        User user2 = new User("Petrov", "Peter", "Octopus", "octopus333");
        User user3 = new User("Sidorov", "Sidor", "Srimp", "shrimp666");
        User user4 = new User("Davydov", "David", "Fish", "fish111");

        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        try {
            UserDao userDao = new UserDaoJson();
            userDao.addUsers(users);
        } catch (DAOException ex) {
            ex.printStackTrace();
        }

        try {
            UserDao userDao = new UserDaoJson();
            userDao.addUser(user4);
            List<User> list = userDao.getAllUsers();
            for (User user : list) {
                System.out.println(user);
            }
            User userToFind = userDao.getUserById(2);
            System.out.println("By ID: " + userToFind);
            System.out.println();
            List<User> usersByName = userDao.getUserBySurname("Sidorov");
            for (User user : usersByName) {
                System.out.println("By surname: " + user);
            }

            System.out.println("Deleted: " + userDao.deleteUserById(2));
            List<User> list2 = userDao.getAllUsers();
            for (User user22 : list2) {
                System.out.println(user22);
            }

        } catch (DAOException ex) {
            ex.printStackTrace();
        }

        System.out.println("...............................................................");
        System.out.println();

        List<Book> books = new ArrayList<Book>();
        Book book1 = new Book(BookCategory.NOVEL, "Tolstoy", "Voina i mir",
                1996, 35.50, false, "Novel about the life of " +
                "Russian aristocracy and the war of 1812.");
        Book book2 = new Book(BookCategory.FAIRYTALE, "Pushkin", "Skazka o rybake i rybke",
                2005, 10.35, false, "Fairytale.");
        Book book3 = new Book(BookCategory.FANTASY, "Joan Rouling", "Harry Potter",
                2012, 20.50, true, "Popular fantasy book");
        Book book4 = new Book(BookCategory.POETRY, "Pushkin", "Eugeny Onegin", 2010,
                15.30, true, "World famous poem.");

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);

        try {
            BookDao bookDao = new BookDaoJson();
            bookDao.addBooks(books);

            books = bookDao.getAllBooks();
            for (Book book : books) {
                System.out.println(book);
            }

            System.out.println();

            Book bookById = bookDao.getBookById(4);
            List<Book> bookByAuthor = bookDao.getBooksByAuthor("Push");
            System.out.println("By ID: " + bookById);
            for (Book bookByAuth : bookByAuthor) {
                System.out.println("By author: " + bookByAuth);
            }

            List<Book> bookByTitle = bookDao.getBooksByTitle("Onegin");
            for (Book bookByTit : bookByTitle) {
                System.out.println("By title: " + bookByTit);
            }

        } catch (DAOException ex) {
            ex.printStackTrace();
        }

        System.out.println(".........................................................................");

        Book book5 = new Book(BookCategory.BIOGRAPHY, "Brok Yeitz", "Enzo Ferrari",
                2018, 35.70, false, "Biography of the founder of Ferrari");

    }
}
