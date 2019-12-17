package by.trjava.task01.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilLastNumberOfMathSquareTest {

    @Test
    public void getTheLastNumberOfMathSquareTest_Positive_Number() {
        int a = 5;
        int actual = UtilLastNumberOfMathSquare.getTheLastNumberOfMathSquare(a);
        int expected = 5;
        assertEquals(expected, actual);
    }
    @Test
    public void getTheLastNumberOfMathSquareTest_Negative_Number() {
        int a = -5;
        int actual = UtilLastNumberOfMathSquare.getTheLastNumberOfMathSquare(a);
        int expected = 5;
        assertEquals(expected, actual);
    }
    @Test
    public void getTheLastNumberOfMathSquareTest_Zero() {
        int a = 0;
        int actual = UtilLastNumberOfMathSquare.getTheLastNumberOfMathSquare(a);
        int expected = 0;
        assertEquals(expected, actual);
    }
}