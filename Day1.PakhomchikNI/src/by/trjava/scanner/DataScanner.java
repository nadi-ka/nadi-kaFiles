package by.trjava.scanner;

import java.util.Scanner;

public class DataScanner {

    public static int readIntegerFromConsole() {
        Scanner scanner = new Scanner(System.in);
        int a;
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        a = scanner.nextInt();
        return a;
    }

    public static double readDoubleFromConsole() {
        Scanner scanner = new Scanner(System.in);
        double a;
        while (!scanner.hasNextDouble()) {
            scanner.next();
        }
        a = scanner.nextDouble();
        return a;
    }

}
