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

        ArrayList<Ball> balls = new ArrayList<>();

        Basket basket = new Basket(balls);
        try {
            Ball ball1 = new Ball(Colour.BLUE, 1.0);
            Ball ball2 = new Ball(Colour.ORANGE, 0.7);
            Ball ball3 = new Ball(Colour.PURPLE, 0.5);
            Ball ball4 = new Ball(Colour.YELLOW, 1.1);
            Ball ball5 = new Ball(Colour.BLUE, 1.0);
            Ball ball6 = new Ball(Colour.WHITE, 1.2);
            Ball ball7 = new Ball(Colour.RED, 1.5);

            basket.addBall(ball1);
            basket.addBall(ball2);
            basket.addBall(ball3);
            basket.addBall(ball4);
            basket.addBall(ball5);
            basket.addBall(ball6);
            basket.addBall(ball7);
        }
        catch (AbsentColourException ex){
            ex.getMessage();
        }
        catch (NegativeOrZeroWeightException ex) {
            ex.getMessage();
        }
        catch (BallWithNullValuesException ex) {
            ex.getMessage();
        }

        ServiceCalculator service = new Service();
        totalWeight = service.weighBalls(basket);
        System.out.println("The weight of the basket with balls is: " + totalWeight);

        amountOfBlue = service.getNumberOfBlueBalls(basket);
        System.out.println("The amount of blue balls is: " + amountOfBlue);


    }
}
