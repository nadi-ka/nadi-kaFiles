package by.trjava.task09.util;

import by.trjava.exception.NegativeValueException;

public class CircumferenceAndCircleArea {

    public static double getCircumference(double radius) throws NegativeValueException {
        if (radius < 0) {
            throw new NegativeValueException("Exception in Class by.trjava.task09.util. CircumferenceAndCircleArea, " +
                    "Method - getCircumference(). Value of radius cannot be < 0!");
        }
        double result;
        result = 2 * Math.PI * radius;
        return result;
    }

    public static double getCircleArea(double radius) throws NegativeValueException {
        if (radius < 0) {
            throw new NegativeValueException("Exception in Class by.trjava.task09.util. CircumferenceAndCircleArea, " +
                    "Method - getCircleArea(). Value of radius cannot be < 0!");
        }
        double result;
        result = Math.PI * Math.pow(radius, 2);
        return result;
    }
}
