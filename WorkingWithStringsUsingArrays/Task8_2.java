package com.company;

import java.util.Scanner;

public class Task8_2 {

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
        //Bubble sort will be used.
        for (int i = numberOfLines - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (linesArray[j].length() > linesArray[j + 1].length()) {
                    String tmp = linesArray[j];
                    linesArray[j] = linesArray[j + 1];
                    linesArray[j + 1] = tmp;
                }
            }
        }
        for (int i = 0; i < linesArray.length; i++) {
            System.out.println(linesArray[i]);
        }
        System.out.println();
        for (int i = numberOfLines - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (linesArray[j].length() < linesArray[j + 1].length()) {
                    String tmp = linesArray[j];
                    linesArray[j] = linesArray[j + 1];
                    linesArray[j + 1] = tmp;
                }
            }
        }
        for (int i = 0; i < linesArray.length; i++) {
            System.out.println(linesArray[i]);
        }


    }
}
