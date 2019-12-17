package by.trjava.console.task07.runner;

import by.trjava.console.task07.util.BirthdayQualifier;

public class Runner {

    public static void main(String[] args) {
        String dayArgs = args[0];
        String monthArgs = args[1];
        String yearArgs = args[2];
        String message;
        try {
            message = BirthdayQualifier.getInformationAboutBirthday(dayArgs, monthArgs, yearArgs);
            System.out.println(message);
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("One/several arguments are illegal or equals null! Please, try again to" +
                    "enter them correctly in the order :day, month, year.");
        }
    }
}
