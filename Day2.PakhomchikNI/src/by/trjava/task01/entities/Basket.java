package by.trjava.task01.entities;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Ball> balls = new ArrayList<>();

    public Basket() {
    }

    public Basket(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public Iterable<Ball> getBalls() {
        return balls;
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public void removeAllBalls() {
        balls.clear();
    }
    public boolean removeBall(Colour colour, double weight) {
        boolean isRemoved = false;
        for (Ball ball: balls) {
            if (ball.getColour() == colour && ball.getWeight() == weight) {
                balls.remove(ball);
                isRemoved = true;
                break;
            }
        }
        return isRemoved;
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
