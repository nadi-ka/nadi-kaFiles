package com.company;

import java.util.Scanner;

public class Task8_3 {
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
        float average = 0f;
        //whole lenth of strings.
        for (String item : linesArray) {
            average = average + (float) item.length();
        }
        //Average calculation.
        average = average / numberOfLines;

        System.out.println("The average length is " + average);
        //Strings, which are shorter than average.
        for (int i = 0; i < numberOfLines; i++) {
            if (linesArray[i].length() < average) {
                System.out.println("Strings, shorter than average:");
                System.out.println(linesArray[i] + " - it's length = " + linesArray[i].length());
            }
        }
    }
}
