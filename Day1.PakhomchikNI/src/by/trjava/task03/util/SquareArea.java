package by.trjava.task03.util;

import by.trjava.exception.NegativeValueException;

public class SquareArea {

    public static double getSmallSquareArea(double bigSquareArea) throws NegativeValueException{
        if (bigSquareArea < 0) {
            throw new NegativeValueException("Exception in Class by.trjava.task03.util.SquareArea, Method" +
                    " getSmallSquareArea(). Area value should be > 0!");
        }
        double circleDiameter;
        double resultArea;
        circleDiameter = Math.sqrt(bigSquareArea);
        resultArea = Math.pow(circleDiameter, 2) / 2;
        return resultArea;
    }

    public static double getAreaOfBigSquareToSmall(double bigSquareArea) throws NegativeValueException{
        double result;
        if (bigSquareArea == 0){
            return 0;
        }
        result = bigSquareArea / getSmallSquareArea(bigSquareArea);
        return result;
    }
}
