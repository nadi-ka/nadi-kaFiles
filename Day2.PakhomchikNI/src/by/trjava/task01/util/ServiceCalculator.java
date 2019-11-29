package by.trjava.task01.util;

import java.util.ArrayList;

// I would rename it to BasketCalculator. The methods here are all about calculations of the basket related values.
public interface ServiceCalculator {

    double weighBalls(Basket basket);

    int getNumberOfBlueBalls(Basket basket);

}
