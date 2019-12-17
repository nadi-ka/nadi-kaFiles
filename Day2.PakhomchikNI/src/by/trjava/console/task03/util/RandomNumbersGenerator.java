package by.trjava.console.task03.util;

import by.trjava.console.exceptions.NegativeValueException;

public class RandomNumbersGenerator {

    public static int[] getRandomNumbers(String argument) throws NegativeValueException {
        int amountOfNumbers = translateStringToDigit(argument);
        if (amountOfNumbers < 0) {
            throw new NegativeValueException("Exception in the Class by.trjava.console.task03.util." +
                    "RandomNumbersGenerator, Method - getRandomNumbers(). You've entered a negative number!");
        }
        int[] resultArray = new int[amountOfNumbers];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = (int) (Math.random() * 1000);
        }
        return resultArray;
    }

    public static int translateStringToDigit(String argument) throws NumberFormatException {
        int amountOfNumbers;
        amountOfNumbers = Integer.parseInt(argument);
        return amountOfNumbers;
    }
}
