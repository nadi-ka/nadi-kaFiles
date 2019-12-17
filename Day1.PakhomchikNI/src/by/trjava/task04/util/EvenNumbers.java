package by.trjava.task04.util;

import by.trjava.exception.IllegalAmountOfArgumentsException;

public class EvenNumbers {

    public static boolean isAtLeast2From4NumbersAreEven(int... numbers) throws IllegalAmountOfArgumentsException {
        boolean result = false;
        int count = 0;
        int amountOfArguments = 4;
        if (numbers.length != amountOfArguments) {
            throw new IllegalAmountOfArgumentsException("Exception in Class by.trjava.task04.util.EvenNumbers, " +
                    "Method - isAtLeast2From4NumbersAreEven(). The amount of arguments should be equals 4!");
        }
        for (int i = 0; i < amountOfArguments; i++) {
            if (numbers[i] % 2 == 0) {
                count = count + 1;
            }
            if (count == 2) {
                result = true;
                break;
            }
        }
        return result;
    }
}
