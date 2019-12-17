package by.trjava.task03.util;

import by.trjava.exception.NegativeValueException;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquareAreaTest {

    @Test
    public void getSmallSquareAreaTest_Positive() throws NegativeValueException {
        double area = 25;
        double actual = SquareArea.getSmallSquareArea(area);
        double expected = 12.5;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getSmallSquareAreaTest_Zero_Value() throws NegativeValueException {
        double area = 0.0;
        double actual = SquareArea.getSmallSquareArea(area);
        double expected = 0.0;
        assertEquals(expected, actual, 0.001);
    }

    @Test(expected = NegativeValueException.class)
    public void getSmallSquareAreaTest_Negative_Value() throws NegativeValueException {
        double area = -8.0;
        SquareArea.getSmallSquareArea(area);
    }

    @Test
    public void getAreaOfBigSquareToSmallTest_Positive() throws NegativeValueException {
        double area = 25;
        double actual = SquareArea.getAreaOfBigSquareToSmall(area);
        double expected = 2;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getAreaOfBigSquareToSmall_Zero_Value() throws NegativeValueException {
        double area = 0;
        double actual = SquareArea.getAreaOfBigSquareToSmall(area);
        double expected = 0.0;
        assertEquals(expected, actual, 0.001);
    }

    @Test(expected = NegativeValueException.class)
    public void getAreaOfBigSquareToSmall_Negative_Value() throws NegativeValueException {
        double area = -8;
        SquareArea.getAreaOfBigSquareToSmall(area);
    }
}