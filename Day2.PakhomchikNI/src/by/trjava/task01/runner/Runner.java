package by.trjava.task01.runner;

import by.trjava.task01.exceptions.AbsentColourException;
import by.trjava.task01.exceptions.NegativeOrZeroWeightException;
import by.trjava.task01.entities.*;
import by.trjava.task01.service.BasketCalculatorService;
import by.trjava.task01.service.BasketCalculator;

public class Runner {

    public static void main(String[] args) {
        Basket basket = new Basket();
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
        catch (AbsentColourException | NegativeOrZeroWeightException ex){
            ex.printStackTrace();
        }
        int amountOfBlue;
        double totalWeight;
        BasketCalculator service = new BasketCalculatorService();
        totalWeight = service.weighBalls(basket);
        System.out.println("The weight of the basket with balls is: " + totalWeight);
        amountOfBlue = service.getNumberOfBlueBalls(basket);
        System.out.println("The amount of blue balls is: " + amountOfBlue);
        System.out.println(basket.removeBall(Colour.BLUE, 1.0));
        System.out.println(service.getNumberOfBlueBalls(basket));
    }
}
