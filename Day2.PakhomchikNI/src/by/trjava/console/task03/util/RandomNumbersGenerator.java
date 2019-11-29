package by.trjava.console.task03.util;

public class RandomNumbersGenerator {

    public static int[] getRandomNumbers(int amountOfNumbers) throws NegativeArraySizeException {
        if (amountOfNumbers < 0) {
            throw new NegativeArraySizeException("You have entered a negative number! " + amountOfNumbers);
        }
        int[] resultArray = new int[amountOfNumbers];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = (int) (Math.random() * 1000);
        }
        return resultArray;
    }
}
