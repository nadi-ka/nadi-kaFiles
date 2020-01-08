package by.trjava.library.service.validation;

public class ValidationBook {

    public static boolean isCorrectAuthor(String author) {
        if (author != null && author.matches("[A-Z][a-z]+\\s+[A-Z]\\.\\s+[A-Z]\\.")){
            return true;
        }
        return false;
    }

    public static boolean isCorrectTitle(String title) {
        if (title != null && title.length() > 0) {
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
