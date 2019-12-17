package by.trjava.task08.runner;

import by.trjava.scanner.DataScanner;
import by.trjava.task08.util.FunctionValue;

public class Runner {

    public static void main(String[] args) {
        double x;
        double result;
        System.out.println("Please, enter the value of x.");
        x = DataScanner.readDoubleFromConsole();
        result = FunctionValue.getFunctionValue(x);
        System.out.println("The value of the function F(x) is equals: " + result);
    }
}
