package by.trjava.task01.util;

import by.trjava.task01.exception.AbsentColourException;
import by.trjava.task01.exception.NegativeOrZeroWeightException;

public class Ball {
    private Colour colour;
    private double weight;

    public Ball() {
    }

    public Ball(Colour colour, double weight) throws AbsentColourException, NegativeOrZeroWeightException {
        this.setColour(colour);
        this.setWeight(weight);
    }

    public void setWeight(double weight) throws NegativeOrZeroWeightException {
        if (weight <= 0) {
            throw new NegativeOrZeroWeightException("Exception in Class Ball, Method setWeight(). The weight of the " +
                    "ball must be > 0! " + weight);
        } else {
            this.weight = weight;
        }
    }

    public void setColour(Colour colour) throws AbsentColourException {
        if (colour == null) {
            throw new AbsentColourException("Exception in Class Ball, Method setColour(). The colour of the ball" +
                    "shouldn't be null!");
        } else {
            this.colour = colour;
        }
    }

    public double getWeight() {
        return weight;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Ball ball = (Ball) object;
        return weight == ball.weight
                && (colour == ball.colour || (colour != null && colour.equals(ball.getColour())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        long isDoubleToLong = Double.doubleToLongBits(weight);
        int weightAsInteger = (int) (isDoubleToLong - (isDoubleToLong >>> 32));
        int result = 1;
        result = prime * result + weightAsInteger;
        result = result * prime + ((colour == null) ? 0 : colour.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ball {" +
                "weight = " + weight +
                ", colour = " + colour + "}";
    }
}
