package by.trjava.task06.runner;

import by.trjava.exception.NegativeValueException;
import by.trjava.scanner.DataScanner;
import by.trjava.task06.util.UtilTime;

public class Runner {

    public static void main(String[] args) {
        int secondsFromTheBeginningOfTheDay;
        String result;
        System.out.println("Please, enter an integer - index number of a current second of a day.");
        secondsFromTheBeginningOfTheDay = DataScanner.readIntegerFromConsole();
        try {
            result = UtilTime.getTime(secondsFromTheBeginningOfTheDay);
            System.out.println("Current time is: " + result);
        }
        catch (NegativeValueException ex) {
            ex.printStackTrace();
        }
    }
}
