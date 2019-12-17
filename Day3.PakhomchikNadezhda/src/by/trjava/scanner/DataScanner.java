package by.trjava.scanner;

import java.util.Scanner;

public class DataScanner {

    public static String readTextFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the text or the symbol. In the case of filling the array - " +
                "enter several space separated integers.");
        String str;
        while (!scanner.hasNext()) {
            scanner.nextLine();
        }
        str = scanner.nextLine();
        return str;
    }

    public static int readIntegerFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter an integer.");
        int number;
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        number = scanner.nextInt();
        return number;
    }
}
