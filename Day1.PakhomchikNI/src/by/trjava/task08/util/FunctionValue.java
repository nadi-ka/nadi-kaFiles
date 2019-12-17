package by.trjava.task08.util;

public class FunctionValue {

    public static double getFunctionValue(double x) {
        double result;
        if (x >= 3) {
            result = Math.pow(-x, 2) + (3 * x) + 9;
        } else {
            if (Math.pow(x, 3) - 6 == 0) {
                return Double.POSITIVE_INFINITY;
            }
            result = 1 / (Math.pow(x, 3) - 6);
        }
        return result;
    }
}
