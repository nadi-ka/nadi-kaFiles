package by.trjava.library.view.scanner;

import java.util.Scanner;

public class DataScanner {

    public static String readStringFromConsole() {
        String string;
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext()) {
            scanner.next();
        }
        string = scanner.nextLine();
        return string;
    }
}
