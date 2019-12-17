package by.trjava.task02.runner;

import by.trjava.exceptions.NegativeSizeException;
import by.trjava.exceptions.NullValueException;
import by.trjava.scanner.DataScanner;
import by.trjava.task02.util.JaggedArray;
import by.trjava.task02.util.JaggedArrayService;

public class Runner {

    public static void main(String[] args) {
        JaggedArray jaggedArray1 = null;
        int size1 = DataScanner.readIntegerFromConsole();
        try {
            jaggedArray1 = new JaggedArray(size1);
        }
        catch (NegativeSizeException ex) {
            ex.printStackTrace();
        }
        jaggedArray1.setSizesInnerRandomly();
        //Filling or the jagged array randomly;
        try {
            jaggedArray1.fillArrayRandomly();
            System.out.println(jaggedArray1.toString());
        }
        catch (NullValueException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        //Sort by the sum of elements in the row ascending / descending;
        boolean isAscending = true;
        JaggedArrayService.bubbleSortBySumInRow(jaggedArray1.getArray(), isAscending);
        System.out.println("Sort by sum in rows ascending:\n" + jaggedArray1);
        System.out.println();
        JaggedArrayService.bubbleSortBySumInRow(jaggedArray1.getArray(), !isAscending);
        System.out.println("Sort by sum in rows descending:\n" + jaggedArray1);
        System.out.println();
        //Sort by the max value in the row ascending / descending;
        boolean sortByMaxValue = true;
        JaggedArrayService.bubbleSortByValueInRow(jaggedArray1.getArray(), sortByMaxValue, isAscending);
        System.out.println("Sort by max value ascending:\n" + jaggedArray1);
        System.out.println();
        JaggedArrayService.bubbleSortByValueInRow(jaggedArray1.getArray(), sortByMaxValue, !isAscending);
        System.out.println("Sort by max value descending:\n" + jaggedArray1);
        System.out.println();
        //Sort by the min value in the row ascending / descending;
        JaggedArrayService.bubbleSortByValueInRow(jaggedArray1.getArray(), !sortByMaxValue, isAscending);
        System.out.println("Sort by min value ascending:\n" + jaggedArray1);
        System.out.println();
        JaggedArrayService.bubbleSortByValueInRow(jaggedArray1.getArray(), !sortByMaxValue, !isAscending);
        System.out.println("Sort by min value descending:\n" + jaggedArray1);

    }
}
