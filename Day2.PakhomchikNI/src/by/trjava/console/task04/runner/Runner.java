package by.trjava.console.task04.runner;

import by.trjava.console.task04.util.PasswordsComparator;

public class Runner {

    public static void main(String[] args) {
        boolean isEquals;
        String truePassword = "12345";
        String introducedPassword;
        introducedPassword = args[0];
        isEquals = PasswordsComparator.comparePasswords(truePassword, introducedPassword);
        System.out.println("Passwords are equals: " + isEquals);
    }
}
