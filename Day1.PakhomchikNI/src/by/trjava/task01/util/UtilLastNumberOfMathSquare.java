package by.trjava.task01.util;

public class UtilLastNumberOfMathSquare {

    public static int getTheLastNumberOfMathSquare(int anyDigit) {
        int isTheLastNumber;
        int isTheLastNumberOfSquare;
        isTheLastNumber = anyDigit % 10;
        isTheLastNumberOfSquare = (isTheLastNumber * isTheLastNumber) % 10;
        return isTheLastNumberOfSquare;
    }

}
