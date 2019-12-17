package by.trjava.task01.entities;

import by.trjava.task01.exceptions.AbsentColourException;
import by.trjava.task01.exceptions.NegativeOrZeroWeightException;

public class Ball {
    private Colour colour;
    private double weight;

    public Ball() {
        /*
        let's consider, that the ball physically cannot exist without colour and weight. So if the user'll
        decide to leave one or both fields empty, these fields'll have default values.
         */
        this.colour = Colour.WHITE;
        this.weight = 0.1;
    }

    public Ball(Colour colour, double weight) throws AbsentColourException, NegativeOrZeroWeightException {
        this.setColour(colour);
        this.setWeight(weight);
    }

    public void setWeight(double weight) throws NegativeOrZeroWeightException {
        if (weight <= 0) {
            throw new NegativeOrZeroWeightException("The weight of the ball must be > 0!" + weight);
        } else {
            this.weight = weight;
        }
    }

    public void setColour(Colour colour) throws AbsentColourException {
        if (colour == null) {
            throw new AbsentColourException("The colour of the ball shouldn't be equals null!");
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
        return getClass().getName() + "@" +
                "ball {" +
                "weight = " + weight +
                ", colour = " + colour + "}";
    }
}
