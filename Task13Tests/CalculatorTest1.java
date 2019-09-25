import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)

public class CalculatorTest1 {

    private int first;
    private int second;
    private int expectedSum;

    public CalculatorTest1(int first, int second, int expectedSum) {
        this.first = first;
        this.second = second;
        this.expectedSum = expectedSum;

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{0, 0, 0}, {1, 1, 2}, {3, 4, 7}});
    }

    Calculator calculator = new Calculator();

    //Parameterized Test:
    @Test
    public void parameterizedCalculatorSum() {
        int actual = calculator.getSum(first, second);
        assertEquals(expectedSum, actual);
    }
}