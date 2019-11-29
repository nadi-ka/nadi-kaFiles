package by.trjava.task01.runner;

import by.trjava.task01.exception.AbsentColourException;
import by.trjava.task01.exception.BallWithNullValuesException;
import by.trjava.task01.exception.NegativeOrZeroWeightException;
import by.trjava.task01.util.*;

import java.util.ArrayList;

public class Runner {

    public static void main(String[] args) {
        int amountOfBlue;
        double totalWeight;

        Basket basket = new Basket();

        try {
            basket.addBall(new Ball(Colour.BLUE, 1.0));
            basket.addBall(new Ball(Colour.ORANGE, 0.7));
            basket.addBall(new Ball(Colour.PURPLE, 0.5));
            basket.addBall(new Ball(Colour.YELLOW, 1.1));
            basket.addBall(new Ball(Colour.BLUE, 1.0));
            basket.addBall(new Ball(Colour.WHITE, 1.2));
            basket.addBall(new Ball(Colour.RED, 1.5));
        }
        catch (AbsentColourException | NegativeOrZeroWeightException | BallWithNullValuesException ex){
            ex.getMessage();
        }

        ServiceCalculator service = new Service();
        totalWeight = service.weighBalls(basket);
        System.out.println("The weight of the basket with balls is: " + totalWeight);

        amountOfBlue = service.getNumberOfBlueBalls(basket);
        System.out.println("The amount of blue balls is: " + amountOfBlue);
    }
}
