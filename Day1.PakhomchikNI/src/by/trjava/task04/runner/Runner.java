package by.trjava.task04.runner;

import by.trjava.exception.IllegalAmountOfArgumentsException;
import by.trjava.scanner.DataScanner;
import by.trjava.task04.util.EvenNumbers;

public class Runner {

    public static void main(String[] args) {
        boolean isAtLeast2From4NumbersAreEven;
        System.out.println("Please, enter consistently 4 integers.");
        int a = DataScanner.readIntegerFromConsole();
        int b = DataScanner.readIntegerFromConsole();
        int c = DataScanner.readIntegerFromConsole();
        int d = DataScanner.readIntegerFromConsole();
        try {
            isAtLeast2From4NumbersAreEven = EvenNumbers.isAtLeast2From4NumbersAreEven(a, b, c, d);
            System.out.println("At least 2 from 4 digits are even: " + isAtLeast2From4NumbersAreEven);
        } catch (IllegalAmountOfArgumentsException ex) {
            ex.printStackTrace();
        }
    }
}
