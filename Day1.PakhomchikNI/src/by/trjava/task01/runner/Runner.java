package by.trjava.task01.runner;

import by.trjava.scanner.DataScanner;
import by.trjava.task01.util.UtilLastNumberOfMathSquare;

public class Runner {

    public static void main(String[] args) {
        int number;
        int result;

        System.out.println("Please, enter an integer.");
        number = DataScanner.readIntegerFromConsole();

        result = UtilLastNumberOfMathSquare.getTheLastNumberOfMathSquare(number);
        System.out.println("The last number of the math square of the digit is: " + result);

    }

}
