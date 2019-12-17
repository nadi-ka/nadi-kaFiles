package by.trjava.task09.runner;

import by.trjava.exception.NegativeValueException;
import by.trjava.scanner.DataScanner;
import by.trjava.task09.util.CircumferenceAndCircleArea;

public class Runner {

    public static void main(String[] args) {
        double radius;
        double circumference;
        double area;
        System.out.println("Please, enter a number - the circle's radius.");
        radius = DataScanner.readDoubleFromConsole();
        try {
            circumference = CircumferenceAndCircleArea.getCircumference(radius);
            area = CircumferenceAndCircleArea.getCircleArea(radius);
            System.out.println("The circumference of the circle with the radius " + radius + " is: " +
                    circumference + ", the area is: " + area);
        }
        catch (NegativeValueException ex) {
            ex.printStackTrace();
        }
    }
}
