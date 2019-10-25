package com.company;

import java.util.Scanner;

public class Task8_5 {
    static boolean isLat(String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static void main(String[] args) {
        final String spaceString = " ";
        Scanner scanner = new Scanner((System.in));
        System.out.println("Enter an integer.");
        int numberOfLines = scanner.nextInt();
        String[] linesArray = new String[numberOfLines];
        System.out.println();
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("Enter the line number " + (i + 1));
            linesArray[i] = scanner.next();

        }
        int count = 0;
        for (int i = 0; i < numberOfLines; i++) {
            if (isLat(linesArray[i]) == true) {
                System.out.println(linesArray[i]);
                count = count + 1;
            }

        }
        System.out.println("Amount of words whis only latinic symbols = " + count);
    }
}
