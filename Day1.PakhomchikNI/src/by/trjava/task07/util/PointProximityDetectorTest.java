package by.trjava.task07.util;

import by.trjava.exception.ObjectIsNotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointProximityDetectorTest {

    @Test
    public void comparePointsTest_Positive_Values() throws ObjectIsNotExistException {
        Point point1 = new Point(2.0, 3.0);
        Point point2 = new Point(3.0, 2.0);
        int actual = PointProximityDetector.comparePoints(point1, point2);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void comparePointsTest_Negative_Values() throws ObjectIsNotExistException {
        Point point1 = new Point(-2.0, -3.0);
        Point point2 = new Point(4.0, -5.0);
        int actual = PointProximityDetector.comparePoints(point1, point2);
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void comparePointsTest_Zero_Values() throws ObjectIsNotExistException {
        Point point1 = new Point(-2.0, 0.0);
        Point point2 = new Point(0.0, 0.0);
        int actual = PointProximityDetector.comparePoints(point1, point2);
        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test(expected = ObjectIsNotExistException.class)
    public void comparePointsTest_Null_Values() throws ObjectIsNotExistException {
        Point point1 = null;
        Point point2 = null;
        PointProximityDetector.comparePoints(point1, point2);
    }

    @Test
    public void getNearestPointTest_Equals_Result() throws ObjectIsNotExistException {
        Point point1 = new Point(2.0, 3.0);
        Point point2 = new Point(3.0, 2.0);
        String actual = PointProximityDetector.getNearestPoint(point1, point2);
        String expected = "The nearest is: equals";
        assertEquals(expected, actual);
    }

    @Test
    public void getNearestPointTest_Nearest_First() throws ObjectIsNotExistException {
        Point point1 = new Point(-2.0, 3.0);
        Point point2 = new Point(7.0, 2.0);
        String actual = PointProximityDetector.getNearestPoint(point1, point2);
        String expected = "The nearest is: " + point1.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getNearestPointTest_Nearest_Second() throws ObjectIsNotExistException {
        Point point1 = new Point(-1.0, -3.0);
        Point point2 = new Point(-1.0, 0.0);
        String actual = PointProximityDetector.getNearestPoint(point1, point2);
        String expected = "The nearest is: " + point2.toString();
        assertEquals(expected, actual);
    }

    @Test(expected = ObjectIsNotExistException.class)
    public void getNearestPointTest_Null_Value() throws ObjectIsNotExistException {
        Point point1 = null;
        Point point2 = new Point(2.0, 4.0);
        PointProximityDetector.getNearestPoint(point1, point2);
    }
}