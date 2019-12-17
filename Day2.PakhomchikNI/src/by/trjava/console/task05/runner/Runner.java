package by.trjava.console.task05.runner;

import by.trjava.console.task05.util.ArgumentsCalculator;

public class Runner {

    public static void main(String[] args) {
        int resultSum;
        int resultProduct;
        try {
            resultSum = ArgumentsCalculator.getSum(args);
            System.out.println("The result sum is: " + resultSum);
            resultProduct = ArgumentsCalculator.getProduct(args);
            System.out.println("The result product is: " + resultProduct);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

    }
}
