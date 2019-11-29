package by.trjava.console.task05.util;

public class Calculation {

    public static int getSum(String[] numbers) {
        int resultSum = 0;
        int number;
        for (int i = 0; i < numbers.length; i++) {
            try {
                number = Integer.parseInt(numbers[i]);
                resultSum += number;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return resultSum;
    }

    public static int getProduct(String[] numbers) {
        int resultProduct = 1;
        int number;
        for (int i = 0; i < numbers.length; i++) {
            try {
                number = Integer.parseInt(numbers[i]);
                resultProduct *= number;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return resultProduct;
    }
}
