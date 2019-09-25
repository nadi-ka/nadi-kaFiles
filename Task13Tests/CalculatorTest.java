import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @BeforeEach
    public void setUp() {
        System.out.println("before");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("after");
    }

    Calculator calculator = new Calculator();


    @Test
    public void getSum() {
        int actual = calculator.getSum(25, 10);
        int expected = 25 + 10;
        assertEquals(expected, actual);
    }

    @Test
    public void getDifference() {
        int actual = calculator.getDifference(25, 10);
        int expected = 25 - 10;
        assertEquals(expected, actual, "Unexpected result!");

    }

    @Test
    public void getMultiply() {
        int actual = calculator.getMultiply(25, 10);
        int expected = 25 * 10;
        assertEquals(expected, actual, "Unexpected result!");
    }

    @Test
    public void getSquare() {
        int actual = calculator.getSquare(25);
        int expected = 25 * 25;
        assertEquals(expected, actual, "Unexpected resuly");

    }

    @Test
    public void getDivision() {
        int actual = calculator.getDivision(25, 5);
        int expected = 25 / 5;
        assertEquals(expected, actual);
    }

    //Negative Test:
    @Test(expected = ArithmeticException.class)
    public void getDivisionByZero() {
        assertEquals(calculator.getDivision(25, 0), 25 / 0,
                "Division by zero!");
    }

    //Ignored test:
    @Ignore("We want to realize this test later")
    @Test
    public void getDivisionWithDoubleResult() {
        double actual = calculator.getDivision(25, 4);
        double expected = 25 / 4;
        assertEquals(expected, actual);

    }

}