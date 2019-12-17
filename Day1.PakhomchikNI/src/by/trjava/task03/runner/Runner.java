package by.trjava.task03.runner;

import by.trjava.exception.NegativeValueException;
import by.trjava.task03.util.SquareArea;
import by.trjava.scanner.DataScanner;

public class Runner {

    public static void main(String[] args) {
        double bigSquareArea;
        double smallSquareArea;
        double bigSquareAreaToSmall;

        System.out.println("Please, enter a decimal number - a big square's area.");
        bigSquareArea = DataScanner.readDoubleFromConsole();

        try {
            smallSquareArea = SquareArea.getSmallSquareArea(bigSquareArea);
            System.out.println("The small square's area is: " + smallSquareArea);

            bigSquareAreaToSmall = SquareArea.getAreaOfBigSquareToSmall(bigSquareArea);
            System.out.println("The smaller square area is " + bigSquareAreaToSmall + " times less, then the " +
                    "big one.");
        } catch (NegativeValueException ex) {
            ex.printStackTrace();
        }
    }
}
