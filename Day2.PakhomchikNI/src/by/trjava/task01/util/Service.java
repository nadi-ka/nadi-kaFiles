package by.trjava.task01.util;

import java.util.ArrayList;

public class Service implements ServiceCalculator {

    @Override
    public double weighBalls(Basket basket) {
        double totalWeight = 0;
        if (basket == null) {
            totalWeight += 0;
        } else {
            for (Ball ball : basket.getBalls()) {
                totalWeight += ball.getWeight();
            }
        }
        return totalWeight;
    }

    @Override
    public int getNumberOfBlueBalls(Basket basket) {
        int result = 0;
        if (basket == null) {
            return result;
        } else {
            for (Ball ball : basket.getBalls()) {
                if (ball.getColour() == Colour.BLUE) {
                    result += 1;
                }
            }
        }
        return result;
    }
}
