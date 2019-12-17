package by.trjava.task10.util;

import by.trjava.exception.IllegalValuesException;
import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.*;

public class TangentCalculatorTest {

    @Test
    public void getValueOnSegment_Positive_Values() throws IllegalValuesException {
        int start = 0;
        int finish = 61;
        int step = 30;
        TreeMap<Integer, Double> actual = TangentCalculator.getValueOnSegment(start, finish, step);
        TreeMap<Integer, Double> expected = new TreeMap<>();
        expected.put(0, 0.00);
        expected.put(30, 0.58);
        expected.put(60, 1.73);
        assertEquals(expected, actual);
    }

    @Test
    public void getValueOnSegmentTest_Zero_Values() throws IllegalValuesException {
        int start = 0;
        int finish = 0;
        int step = 0;
        TreeMap<Integer, Double> actual = TangentCalculator.getValueOnSegment(start, finish, step);
        TreeMap<Integer, Double> expected = new TreeMap<>();
        expected.put(0, 0.00);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalValuesException.class)
    public void getValueOnSegmentTest_Illegal_Start_Value() throws IllegalValuesException {
        int start = -1;
        int finish = 91;
        int step = 30;
        TangentCalculator.getValueOnSegment(start, finish, step);
    }

    @Test(expected = IllegalValuesException.class)
    public void getValueOnSegmentTest_Illegal_Finish_Value() throws IllegalValuesException {
        int start = 0;
        int finish = 361;
        int step = 30;
        TangentCalculator.getValueOnSegment(start, finish, step);
    }


    @Test(expected = IllegalValuesException.class)
    public void getValueOnSegmentTest_Start_Value_More() throws IllegalValuesException {
        int start = 60;
        int finish = 0;
        int step = 30;
        TangentCalculator.getValueOnSegment(start, finish, step);
    }

    @Test(expected = IllegalValuesException.class)
    public void getValueOnSegmentTest_Negative_Step_Value() throws IllegalValuesException {
        int start = 0;
        int finish = 91;
        int step = -30;
        TangentCalculator.getValueOnSegment(start, finish, step);
    }

    @Test
    public void getValueOnSegment_Angle90() throws IllegalValuesException {
        int start = 60;
        int finish = 91;
        int step = 30;
        TreeMap<Integer, Double> actual = TangentCalculator.getValueOnSegment(start, finish, step);
        TreeMap<Integer, Double> expected = new TreeMap<>();
        expected.put(60, 1.73);
        expected.put(90, Double.POSITIVE_INFINITY);
        assertEquals(expected, actual);
    }

    @Test
    public void getValueOnSegment_Angle270() throws IllegalValuesException {
        int start = 270;
        int finish = 270;
        int step = 0;
        TreeMap<Integer, Double> actual = TangentCalculator.getValueOnSegment(start, finish, step);
        TreeMap<Integer, Double> expected = new TreeMap<>();
        expected.put(270, Double.NEGATIVE_INFINITY);
        assertEquals(expected, actual);
    }
}