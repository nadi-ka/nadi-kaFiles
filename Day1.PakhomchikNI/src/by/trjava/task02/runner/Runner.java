package by.trjava.task02.runner;

import by.trjava.task02.util.UtilNumberOfDaysInTheMonth;
import by.trjava.scanner.DataScanner;

public class Runner {

    public static void main(String[] args) {
        int year;
        int month;
        int result;

        System.out.println("Please, enter a year.");
        year = DataScanner.readIntegerFromConsole();
        System.out.println("Please, enter a month as a digit.");
        month = DataScanner.readIntegerFromConsole();

        result = UtilNumberOfDaysInTheMonth.getNumberOfDaysInTheMonth(month, year);
        System.out.println("There are " + result + " days in this month.");
    }
}
