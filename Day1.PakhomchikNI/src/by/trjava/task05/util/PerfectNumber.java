package by.trjava.task05.util;

public class PerfectNumber {

    public static boolean isPerfectNumber(int anyNumber) {
        if (anyNumber <= 0) {
            return false;
        } else {
            boolean result;
            int count = 0;
            for (int i = 1; i < anyNumber; i++) {
                if (anyNumber % i == 0) {
                    count = count + i;
                }
            }
            result = (count == anyNumber) ? true : false;
            return result;
        }
    }
}
