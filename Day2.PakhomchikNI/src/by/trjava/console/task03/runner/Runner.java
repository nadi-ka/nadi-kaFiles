package by.trjava.console.task03.runner;

import by.trjava.console.exceptions.NegativeValueException;
import by.trjava.console.task03.util.RandomNumbersGenerator;

public class Runner {

    public static void main(String[] args) {
        int[] resultArrayOfNumbers;
        System.out.println("Please, enter an integer - amount of random numbers.");
        try {
            resultArrayOfNumbers = RandomNumbersGenerator.getRandomNumbers(args[0]);
            for (int i = 0; i < resultArrayOfNumbers.length; i++) {
                System.out.println(resultArrayOfNumbers[i]);
            }
            for (int i = 0; i < resultArrayOfNumbers.length; i++) {
                System.out.print(resultArrayOfNumbers[i] + " ");
            }
        } catch (NegativeValueException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
