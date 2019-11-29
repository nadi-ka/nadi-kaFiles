package by.trjava.console.scanner;

import java.util.Scanner;

public class DataScanner {

    public static String getString() {
        String resultString;
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext()) {
            scanner.next();
        }
        resultString = scanner.next();
        return resultString;
    }
}
