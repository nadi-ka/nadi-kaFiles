package by.trjava.task09.util;

import by.trjava.exception.NegativeValueException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CircumferenceAndCircleAreaTest {

    @Test
    public void getCircumferenceTest_Positive_Value() throws NegativeValueException {
        double radius = 3.0;
        double actual = CircumferenceAndCircleArea.getCircumference(radius);
        double expected = 18.850;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getCircumferenceTest_Zero_Value() throws NegativeValueException {
        double radius = 0.0;
        double actual = CircumferenceAndCircleArea.getCircumference(radius);
        double expected = 0.0;
        assertEquals(expected, actual, 0.001);
    }

    @Test(expected = NegativeValueException.class)
    public void getCircumferenceTest_Negative_Value() throws NegativeValueException {
        double radius = -3.0;
        CircumferenceAndCircleArea.getCircumference(radius);
    }

    @Test
    public void getCircleAreaTest_Positive_Value() throws NegativeValueException {
        double radius = 3.0;
        double actual = CircumferenceAndCircleArea.getCircleArea(radius);
        double expected = 28.274;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getCircleAreaTest_Zero_Value() throws NegativeValueException {
        double radius = 0.0;
        double actual = CircumferenceAndCircleArea.getCircleArea(radius);
        double expected = 0.0;
        assertEquals(expected, actual, 0.001);
    }

    @Test(expected = NegativeValueException.class)
    public void getCircleAreaTest_Negative_Value() throws NegativeValueException {
        double radius = -5.0;
        CircumferenceAndCircleArea.getCircleArea(radius);
    }
}