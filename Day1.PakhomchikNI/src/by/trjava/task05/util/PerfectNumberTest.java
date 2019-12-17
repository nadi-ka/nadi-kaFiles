package by.trjava.task05.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PerfectNumberTest {

    @Test
    public void isPerfectNumberTest_Positive_Value() {
        int a = 6;
        boolean actual = PerfectNumber.isPerfectNumber(a);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test
    public void isPerfectNumberTest_Negative_Value() {
        int a = -28;
        boolean actual = PerfectNumber.isPerfectNumber(a);
        boolean expected = false;
        assertEquals(expected, actual);
    }
    @Test
    public void isPerfectNumberTest_Zero_Value() {
        int a = 0;
        boolean actual = PerfectNumber.isPerfectNumber(a);
        boolean expected = false;
        assertEquals(expected, actual);
    }
    @Test
    public void isPerfectNumberTest_Value_Equals_One() {
        int a = 1;
        boolean actual = PerfectNumber.isPerfectNumber(a);
        boolean expected = false;
        assertEquals(expected, actual);
    }
}