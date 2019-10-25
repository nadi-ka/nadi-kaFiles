package com.company;

import java.util.Scanner;

public class Task8_1a {
    public static void main(String[] args) {
        Scanner scanner = new Scanner((System.in));
        System.out.println("Enter an integer.");
        int numberOfLines = scanner.nextInt();
        String[] linesArray = new String[numberOfLines];
        System.out.println();

        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("Enter the line number " + (i + 1));
            linesArray[i] = scanner.next();
        }
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("word: " + linesArray[i] + " - Length: " + linesArray[i].length());
        }
        String isTheShortest = linesArray[0];
        String isTheLongest = linesArray[0];
        for (int i = 0; i < linesArray.length - 1; i++) {
            if (isTheLongest.length() < linesArray[i + 1].length()) {
                isTheLongest = linesArray[i + 1];
            }
        }
        for (int i = 0; i < linesArray.length - 1; i++) {
            if (isTheShortest.length() > linesArray[i + 1].length()) {
                isTheShortest = linesArray[i + 1];
            }
        }

        System.out.println("The longest word: " + isTheLongest + " Length is: " + isTheLongest.length());
        System.out.println("The shortest word: " + isTheShortest + " Length is: " + isTheShortest.length());

    }
}
