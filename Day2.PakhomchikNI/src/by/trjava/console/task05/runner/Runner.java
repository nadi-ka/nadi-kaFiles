package by.trjava.console.task05.runner;

import by.trjava.console.task05.util.Calculation;

public class Runner {

    public static void main(String[] args) {
        int resultSum;
        int resultProduct;

        resultSum = Calculation.getSum(args);
        System.out.println("The result sum is: " + resultSum);
        resultProduct = Calculation.getProduct(args);
        System.out.println("The result product is: " + resultProduct);

    }
}
