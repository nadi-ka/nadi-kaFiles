package by.trjava.task01.util;

import by.trjava.task01.exception.BallWithNullValuesException;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Ball> balls = new ArrayList<>();

    public Iterable<Ball>getBalls() {
        return balls;
    }

    public void addBall(Ball ball) throws BallWithNullValuesException {
        if (ball.getWeight() == 0 || ball.getColour() == null) {
            throw new BallWithNullValuesException("Exception in Class Basket, Method addBall(). You can't add" +
                    "the ball without indication of colour and/or weight to the basket!");
        }

        balls.add(ball);
    }

    public void removeBalls() {
        balls.clear();
    }

    @Override
    public String toString() {
        String str = "Balls in the basket: ";
        for (Ball ball : balls) {
            str += ball.toString() + "; \n";
        }
        return str;
    }
}
