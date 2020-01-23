package by.trjava.library.service.validation;

import by.trjava.library.beans.book.Book;
import by.trjava.library.beans.book.BookCategory;

import java.util.List;

public class BookValidator {

    public static boolean isUniqueId(String id, List<Book> books) {
        for (Book book: books) {
            if (book.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCorrectBookCategory(BookCategory bookCategory) {
        if (bookCategory != null) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectAuthor(String author) {
        if (author != null && author.matches("[A-Z][a-z]+\\s+[A-Z]\\.\\s?[A-Z]?\\.?")){
            return true;
        }
        return false;
    }

    public static boolean isCorrectAuthorForRequest(String author) {
        if (author != null && author != " " && author.length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectTitle(String title) {
        if (title != null && title != " " && title.length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectYearOfEdition(int year) {
        if (String.valueOf(year).matches("-?\\d{1,4}")) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectPrice(double price) {
        if (price > 0.0) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectDescription(String description) {
        if (description != null && description.length() > 0) {
            return true;
        }
        return false;
    }
}
