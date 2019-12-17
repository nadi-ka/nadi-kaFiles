package by.trjava.task05.runner;

import by.trjava.scanner.DataScanner;
import by.trjava.task05.util.PerfectNumber;

public class Runner {

    public static void main(String[] args) {
        int number;
        boolean isAPerfectNumber;
        number = DataScanner.readIntegerFromConsole();
        isAPerfectNumber = PerfectNumber.isPerfectNumber(number);
        System.out.println("The number " + number + " is perfect: " + isAPerfectNumber);
    }
}
