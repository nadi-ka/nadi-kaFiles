package by.trjava.task06.util;

import by.trjava.exception.NegativeValueException;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTimeTest {

    @Test
    public void getTimeTest_Positive_Value() throws NegativeValueException {
        int seconds = 3665;
        String actual = UtilTime.getTime(seconds);
        String expected = "01:01:05";
        assertEquals(expected, actual);
    }

    @Test
    public void getTimeTest_Positive_Value_If_More_Then_Day() throws NegativeValueException {
        int seconds = 86465;
        String actual = UtilTime.getTime(seconds);
        String expected = "00:01:05";
        assertEquals(expected, actual);
    }

    @Test
    public void getTimeTest_Zero_Value() throws NegativeValueException{
        int seconds = 0;
        String actual = UtilTime.getTime(seconds);
        String expected = "00:00:00";
        assertEquals(expected, actual);
    }

    @Test(expected = NegativeValueException.class)
    public void getTimeTest_Negative_Value() throws NegativeValueException {
        int seconds = -3665;
        UtilTime.getTime(seconds);
    }

}