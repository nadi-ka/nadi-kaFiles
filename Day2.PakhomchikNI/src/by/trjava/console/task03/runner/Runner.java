package by.trjava.console.task03.runner;

import by.trjava.console.task03.util.RandomNumbersGenerator;

public class Runner {

    public static void main(String[] args) {
        int amountOfNumbers;
        int[] resultArrayOfNumbers;

        System.out.println("Please, enter an integer - amount of random numbers!");
        amountOfNumbers = Integer.parseInt(args[0]);
        resultArrayOfNumbers = RandomNumbersGenerator.getRandomNumbers(amountOfNumbers);
        for (int i = 0; i < resultArrayOfNumbers.length; i++) {
            System.out.println(resultArrayOfNumbers[i]);
        }
        for (int i = 0; i < resultArrayOfNumbers.length; i++) {
            System.out.print(resultArrayOfNumbers[i] + " ");
        }
    }
}
