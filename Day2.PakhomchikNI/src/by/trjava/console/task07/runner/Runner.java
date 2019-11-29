package by.trjava.console.task07.runner;

import by.trjava.console.task07.util.BirthdayQualifier;

public class Runner {

    public static void main(String[] args) {
        String dayArgs = "13";
        String monthArgs = "11";
        String yearArgs = "2013";
        String message;

        try {
            message = BirthdayQualifier.getInformationAboutBirthday(dayArgs, monthArgs, yearArgs);
            System.out.println(message);
        } catch (NullPointerException ex) {
            System.out.println("One of arguments is illegal or equals null!");
        }
    }
}
