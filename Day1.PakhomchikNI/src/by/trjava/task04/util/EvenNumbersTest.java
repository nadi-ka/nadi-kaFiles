package by.trjava.task04.util;

import by.trjava.exception.IllegalAmountOfArgumentsException;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvenNumbersTest {

    @Test
    public void isAtLeast2From4NumbersAreEvenTest_Positive_Values() throws IllegalAmountOfArgumentsException {
        boolean actual = EvenNumbers.isAtLeast2From4NumbersAreEven(1, 7, 6, 5);
        boolean expected = false;
        assertEquals(expected, actual);
    }
    @Test
    public void isAtLeast2From4NumbersAreEvenTest_Zero_Value() throws IllegalAmountOfArgumentsException{
        boolean actual = EvenNumbers.isAtLeast2From4NumbersAreEven(2, 0, 5, 7);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test
    public void isAtLeast2From4NumbersAreEvenTest_Negative_Value() throws IllegalAmountOfArgumentsException{
        boolean actual = EvenNumbers.isAtLeast2From4NumbersAreEven(-6, 1, 3, -4);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test (expected = IllegalAmountOfArgumentsException.class)
    public void isAtLeast2From4NumbersAreEvenTest_More_Arguments() throws IllegalAmountOfArgumentsException{
        EvenNumbers.isAtLeast2From4NumbersAreEven(1, 10, 4, 6, 7);
    }
    @Test (expected = IllegalAmountOfArgumentsException.class)
    public void isAtLeast2From4NumbersAreEvenTest_Less_Arguments() throws IllegalAmountOfArgumentsException{
        EvenNumbers.isAtLeast2From4NumbersAreEven(1, 0, 2);
    }

}