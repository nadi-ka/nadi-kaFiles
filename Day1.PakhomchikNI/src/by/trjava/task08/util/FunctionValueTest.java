package by.trjava.task08.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionValueTest {

    @Test
    public void getFunctionValueTest_Positive1_Value() {
        double x = 4;
        double actual = FunctionValue.getFunctionValue(x);
        double expected = 37;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getFunctionValueTest_Positive2_Value() {
        double x = 2;
        double actual = FunctionValue.getFunctionValue(x);
        double expected = 0.5;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getFunctionValueTest_Negative_Value() {
        double x = -4;
        double actual = FunctionValue.getFunctionValue(x);
        double expected = -0.0143;
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getFunctionValueTest_Zero_Value() {
        double x = 0;
        double actual = FunctionValue.getFunctionValue(x);
        double expected = -0.1667;
        assertEquals(expected, actual, 0.001);
    }
}