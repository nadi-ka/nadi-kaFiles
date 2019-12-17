package by.trjava.task07.runner;

import by.trjava.exception.ObjectIsNotExistException;
import by.trjava.scanner.DataScanner;
import by.trjava.task07.util.Point;
import by.trjava.task07.util.PointProximityDetector;

public class Runner {

    public static void main(String[] args) {
        double coordinate1;
        double coordinate2;
        System.out.println("Please, enter 2 numbers - coordinates of the first point.");
        coordinate1 = DataScanner.readDoubleFromConsole();
        coordinate2 = DataScanner.readDoubleFromConsole();
        Point point1 = new Point(coordinate1, coordinate2);
        System.out.println("Please, enter 2 numbers - coordinates of the second point.");
        coordinate1 = DataScanner.readDoubleFromConsole();
        coordinate2 = DataScanner.readDoubleFromConsole();
        Point point2 = new Point(coordinate1, coordinate2);
        try {
            String result = PointProximityDetector.getNearestPoint(point1, point2);
            System.out.println(result);
        }
        catch (ObjectIsNotExistException ex) {
            ex.printStackTrace();
        }

    }
}
