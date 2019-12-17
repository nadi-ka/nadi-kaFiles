package by.trjava.task01.util.service;

import java.util.ArrayList;
import java.util.List;

public class ArrayService {

    public static int searchMaxValue(int[] array) {
        if (array != null && array.length != 0) {
            int maxValue = array[0];
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i + 1] > array[i]) {
                    maxValue = array[i + 1];
                }
            }
            return maxValue;
        } else {
            return 0;
        }
    }

    public static int searchMinValue(int[] array) {
        if (array != null && array.length != 0) {
            int minValue = array[0];
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i + 1] < minValue) {
                    minValue = array[i + 1];
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
            for (int i = firstDivider; i < number; i++) {
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

    public static List<Integer> getSimpleNumbers(int[] array) {
        List<Integer> resultList = new ArrayList<>();
        if (array != null && array.length != 0) {
            for (int i = 0; i < array.length; i++) {
                if (isSimpleNumber(array[i])) {
                    resultList.add(array[i]);
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
    public static List<Integer> getFibonacciNumbers(int[] array) {
        List<Integer> listFibonacci = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (isPerfectSquare(5 * array[i] * array[i] + 4) ||
                    isPerfectSquare(5 * array[i] * array[i] - 4)) {
                listFibonacci.add(array[i]);
            }
        }
        return listFibonacci;
    }

    public static List<Integer> getNumbersWithThreeDifferentDigits(int[] array) {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            int loverBoard = 100;
            int upperBoard = 999;
            if (array[i] >= loverBoard && array[i] <= upperBoard) {
                int firstDigit = array[i] % 10;
                int secondDigit = array[i] / 10 % 10;
                int thirdDigit = array[i] / 100;
                if (firstDigit != secondDigit && secondDigit != thirdDigit) {
                    listOfNumbers.add(array[i]);
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
