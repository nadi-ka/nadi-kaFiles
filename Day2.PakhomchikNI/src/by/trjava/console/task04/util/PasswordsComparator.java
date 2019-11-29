package by.trjava.console.task04.util;

public class PasswordsComparator {

    public static boolean comparePasswords(String truePassword, String introducedPassword) {
        boolean isEquals;
        isEquals = (truePassword.equals(introducedPassword)) ? true : false;
        return isEquals;
    }
}
