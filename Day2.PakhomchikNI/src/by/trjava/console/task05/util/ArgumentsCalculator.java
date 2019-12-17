package by.trjava.console.task05.util;

public class ArgumentsCalculator {

    public static int getSum(String[] numbers) throws NumberFormatException{
        int resultSum = 0;
        int number;
        if (numbers != null) {
            for (int i = 0; i < numbers.length; i++) {
                    number = Integer.parseInt(numbers[i]);
                    resultSum += number;
            }
        }
        return resultSum;
    }

    public static int getProduct(String[] numbers) throws NumberFormatException{
        int resultProduct = 1;
        int number;
        if (numbers == null || numbers.length == 0) {
            resultProduct = 0;
        } else {
            for (int i = 0; i < numbers.length; i++) {
                try {
                    number = Integer.parseInt(numbers[i]);
                    resultProduct *= number;
                } catch (NumberFormatException ex) {
                    /*
                    we process the exception here, because if 'catch' block will work out, then one of arguments
                    cannot be produced to Integer and we expect zero result (not 1).
                     */
                    resultProduct = 0;
                }
            }
        }
        return resultProduct;
    }
}
