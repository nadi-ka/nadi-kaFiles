package by.trjava.task01.util;

import by.trjava.task01.exception.BallWithNullValuesException;

import java.util.ArrayList;

public class Basket {
    private ArrayList<Ball> balls;

    public Basket() {
    }

    public Basket(ArrayList<Ball> balls) {
        this.balls = new ArrayList<Ball>();
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = new ArrayList<Ball>();
    }

    public  ArrayList<Ball>getBalls() {
        return balls;
    }

    public void addBall(Ball ball) throws BallWithNullValuesException {
        if (ball.getWeight() == 0 || ball.getColour() == null) {
            throw new BallWithNullValuesException("Exception in Class Basket, Method addBall(). You can't add" +
                    "the ball without indication of colour and/or weight to the basket!");
        } else {
            balls.add(ball);
        }
    }

    public void removeBall() {
        if (!balls.isEmpty()) {
            for (Ball ball : balls) {
                balls.remove(ball);
            }
        }
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
