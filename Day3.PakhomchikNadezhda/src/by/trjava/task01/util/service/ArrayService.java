package by.trjava.task01.util.service;

import by.trjava.task01.util.entity.IntegerArray;

import java.util.ArrayList;
import java.util.List;

public class ArrayService {
    // IMHO ArrayService in your case should work with you class IntegerArray. Otherwise, why would you create it?

    public static int searchMaxValue(IntegerArray array) {
        if (array != null && array.getArraySize() != 0) {
            int maxValue = array.getElement(0);
            for (int i = 0; i < array.getArraySize() - 1; i++) {
                // Something wron here
                if (array.getElement(i + 1) > maxValue) {
                    maxValue = array.getElement(i + 1);
                }
            }
            return maxValue;
        } else {
            return 0;
        }
    }

    public static int searchMinValue(IntegerArray array) {
        if (array != null && array.getArraySize() != 0) {
            int minValue = array.getElement(0);
            for (int i = 0; i < array.getArraySize() - 1; i++) {
                if (array.getElement(i + 1) < minValue) {
                    minValue = array.getElement(i + 1);
                }
            }
            return minValue;
        } else {
            return 0;
        }
    }

    //This method find simple numbers and'll use in the method getSimpleNumbers();
    public static boolean isSimpleNumber(int number) {
        int firstDivider = 2;
        boolean isSimple = true;
        if (number > 1) {
            // Just a math tip: you can loop until i < ((int)(number/2)+1)
            for (int i = firstDivider; i < ((int)(number/2)+1); i++) {
                if (number % i == 0) {
                    isSimple = false;
                    break;
                }
            }
        } else {
            isSimple = false;
        }
        return isSimple;
    }

    public static List<Integer> getSimpleNumbers(IntegerArray array) {
        List<Integer> resultList = new ArrayList<>();
        if (array != null && array.getArraySize() != 0) {
            // Let's use the power of Iterable here
//            for (int i = 0; i < array.getArraySize(); i++) {
//                if (isSimpleNumber(array[i])) {
//                    resultList.add(array[i]);
//                }
//            }
            for (int value: array) {
                if (isSimpleNumber(value)) {
                    resultList.add(value);
                }
            }
        }
        return resultList;
    }

    //Method to find if the square is perfect for next using in the method getFibonacciNumbers();
    public static boolean isPerfectSquare(int number) {
        if (number < 0) {
            return false;
        } else {
            int n = (int) Math.sqrt(number);
            return (n * n == number);
        }
    }

    //Algorithm of checking if the number is Fibonacci number: Math.sqrt(5 * N^2 + 4 || 5 * N^2 - 4);
    public static List<Integer> getFibonacciNumbers(IntegerArray array) {
        List<Integer> listFibonacci = new ArrayList<>();
//        for (int i = 0; i < array.getArraySize(); i++) {
//            if (isPerfectSquare(5 * array[i] * array[i] + 4) ||
//                    isPerfectSquare(5 * array[i] * array[i] - 4)) {
//                listFibonacci.add(array[i]);
//            }
//        }
        // Power of Iterable here!!!
        for (int value: array) {
            if (isPerfectSquare(5 * value * value + 4) ||
                    isPerfectSquare(5 * value * value - 4)) {
                listFibonacci.add(value);
            }
        }
        return listFibonacci;
    }

    public static List<Integer> getNumbersWithThreeDifferentDigits(IntegerArray array) {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < array.getArraySize(); i++) {
            // Lover - любовник. You mean lower
            int loverBoard = 100;
            int upperBoard = 999;
            if (array.getElement(i) >= loverBoard && array.getElement(i) <= upperBoard) {
                int firstDigit = array.getElement(i) % 10;
                int secondDigit = array.getElement(i) / 10 % 10;
                int thirdDigit = array.getElement(i) / 100;
                if (firstDigit != secondDigit && secondDigit != thirdDigit) {
                    listOfNumbers.add(array.getElement(i));
                }

            }
        }
        return listOfNumbers;
    }

    //Method to convert String to int[] array;
    public static int[] convertTextToArrayInt(String str) throws NumberFormatException {
        String [] stringArray = str.split(" ");
        int [] resultArrayInt = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            int element = Integer.parseInt(stringArray[i]);
            resultArrayInt[i] = element;
        }
        return resultArrayInt;
    }

}
