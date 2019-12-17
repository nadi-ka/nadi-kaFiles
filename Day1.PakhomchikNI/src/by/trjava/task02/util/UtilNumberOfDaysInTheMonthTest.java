package by.trjava.task02.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilNumberOfDaysInTheMonthTest {

    @Test
    public void isALeapYearTest_Leap() {
        int year = 2000;
        boolean actual = UtilNumberOfDaysInTheMonth.isALeapYear(year);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test
    public void isALeapYearTest_Not_Leap() {
        int year = 2002;
        boolean actual = UtilNumberOfDaysInTheMonth.isALeapYear(year);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void isALeapYearTest_Zero_Value() {
        int year = 0;
        boolean actual = UtilNumberOfDaysInTheMonth.isALeapYear(year);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfDaysInTheMonth_Leap_February() {
        int month = 2;
        int year = 2012;
        int actual = UtilNumberOfDaysInTheMonth.getNumberOfDaysInTheMonth(month, year);
        int expected = 29;
        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfDaysInTheMonthTest_Not_Leap_February() {
        int month = 2;
        int year = 2013;
        int actual = UtilNumberOfDaysInTheMonth.getNumberOfDaysInTheMonth(month, year);
        int expected = 28;
        assertEquals(expected, actual);
    }

    @Test
    public void getNumberOfDaysInTheMonthTest_Zero_Value() {
        int month = 0;
        int year = 0;
        int actual = UtilNumberOfDaysInTheMonth.getNumberOfDaysInTheMonth(month, year);
        int expected = 0;
        assertEquals(expected, actual);
    }
}