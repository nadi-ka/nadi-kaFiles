package by.trjava.task10.runner;

import by.trjava.exception.IllegalValuesException;
import by.trjava.scanner.DataScanner;
import by.trjava.task10.util.TangentCalculator;

import java.util.Map;
import java.util.TreeMap;

public class Runner {

    public static void main(String[] args) {
        int start;
        int finish;
        int step;
        System.out.println("Please, enter consistently 3 integers: angles in degrees (0 - 360) 'from-to' we'll " +
                "count tangents and the step");
        start = DataScanner.readIntegerFromConsole();
        finish = DataScanner.readIntegerFromConsole();
        step = DataScanner.readIntegerFromConsole();
        TreeMap<Integer, Double> resultMap;
        try {
            resultMap = TangentCalculator.getValueOnSegment(start, finish, step);
            for (Map.Entry<Integer, Double> pair : resultMap.entrySet()) {
                System.out.println(pair.getKey() + "  " + pair.getValue());
            }
        }
        catch (IllegalValuesException ex) {
            ex.printStackTrace();
        }
    }
}
