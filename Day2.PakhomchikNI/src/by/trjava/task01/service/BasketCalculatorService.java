package by.trjava.task01.service;

import by.trjava.task01.entities.Ball;
import by.trjava.task01.entities.Basket;
import by.trjava.task01.entities.Colour;

public class BasketCalculatorService implements BasketCalculator {

    @Override
    public double weighBalls(Basket basket) {
        double totalWeight = 0;
        if (basket != null) {
            for (Ball ball : basket.getBalls()) {
                totalWeight += ball.getWeight();
            }
        }
        return totalWeight;
    }

    @Override
    public int getNumberOfBlueBalls(Basket basket) {
        int result = 0;
        if (basket != null) {
            for (Ball ball : basket.getBalls()) {
                if (ball.getColour() == Colour.BLUE) {
                    result += 1;
                }
            }
        }
        return result;
    }
}
