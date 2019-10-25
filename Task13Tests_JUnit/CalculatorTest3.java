import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest3 {

    public CalculatorTest3() {
    }

    Calculator calculator = new Calculator();

    @BeforeEach
    void setUp() {
        System.out.println("before");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after");
    }
    // Display name method
    @Test
    @DisplayName("This is test method for getting the cube of an integer")
    public void getCube() {
        int actual = calculator.getCube(3);
        int expected = 3 * 3 * 3;
        assertEquals(expected, actual);

    }

}