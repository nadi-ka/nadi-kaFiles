package by.trjava.task01.runner;

import by.trjava.task01.dataaccess.DataAccess;
import by.trjava.exceptions.InputFromFileImpossibleException;
import by.trjava.exceptions.NegativeSizeException;
import by.trjava.exceptions.NullValueException;
import by.trjava.scanner.DataScanner;
import by.trjava.task01.util.entity.IntegerArray;
import by.trjava.task01.util.service.ArrayService;

import java.util.List;

public class Runner {

    public static void main(String[] args) {
        //Creating and filling the array from console.
        IntegerArray integerArray1 = null;
        try {
            String str = DataScanner.readTextFromConsole();
            int[] array1 = ArrayService.convertTextToArrayInt(str);
            integerArray1 = new IntegerArray(array1);
            System.out.println(integerArray1);
        }
        catch (NumberFormatException | NullValueException ex) {
            ex.printStackTrace();
        }
        //Creating and filling the array randomly.
        IntegerArray integerArray2 = null;
        int size = DataScanner.readIntegerFromConsole();
        try {
            integerArray2 = new IntegerArray(size);
            integerArray2.fillArrayRandomly();
            System.out.println(integerArray2);
        }
        catch (NegativeSizeException ex) {
            ex.printStackTrace();
        }
        //Creating and filling the array from file.
        IntegerArray integerArray3 = null;
        try {
            int[] array3 = DataAccess.readTextFromFile();
            integerArray3 = new IntegerArray(array3);
            System.out.println(integerArray3);
        }
        catch (InputFromFileImpossibleException | NullValueException ex) {
            ex.printStackTrace();
        }
        System.out.println();
        //Bubble sort
        integerArray2.bubbleSort();
        System.out.println("Bubble sort: " + integerArray2);
        //Insertion sort;
        integerArray1.insertionSort();
        System.out.println("Insertion sort: " + integerArray1);
        //Selection sort;
        integerArray3.selectionSort();
        System.out.println("Selection sort" + integerArray3);
        //Binary search in the sorted array;
        System.out.println("The index of searching element is: " + integerArray1.binarySearch(7));
        System.out.println();
        IntegerArray integerArray = null;
        int size2 = DataScanner.readIntegerFromConsole();
        try {
            integerArray = new IntegerArray(size2);
            integerArray.fillArrayRandomly();
            System.out.println(integerArray);
        }
        catch (NegativeSizeException ex) {
            ex.printStackTrace();
        }
        int[] array4 = integerArray.getArray();
        //Looking for max and min values int the array;
        int valueMax;
        int valueMin;
        System.out.println("Max value: " + ArrayService.searchMaxValue(array4));
        System.out.println("Min value: " + ArrayService.searchMinValue(array4));
        //Looking for simple numbers in the array;
        List<Integer> simpleNumbers;
        simpleNumbers = ArrayService.getSimpleNumbers(array4);
        System.out.print("Simple numbers: ");
        for (Integer element: simpleNumbers) {
            System.out.print(element + " ");
        }
        System.out.println();
        //Looking for Fibonacci numbers;
        List<Integer> fibonacciNumbers;
        fibonacciNumbers = ArrayService.getFibonacciNumbers(array4);
        System.out.print("Fibonacci numbers: ");
        for (Integer element: fibonacciNumbers) {
            System.out.print(element + " ");
        }
        System.out.println();
        //Looking for numbers with 3 different digits;
        List<Integer> numbersWith3DifferentDigits;
        numbersWith3DifferentDigits = ArrayService.getNumbersWithThreeDifferentDigits(array4);
        System.out.print("Numbers with 3 different digits: ");
        for (Integer element: numbersWith3DifferentDigits) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
