package com.company;

import java.util.Scanner;

public class Task8_7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an integer.");
        int numberOfLines = scanner.nextInt();
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("Enter the line number " + (i + 1));
            String word = scanner.next();
            char[] charArray = word.toCharArray();
            boolean allDifferent = true;
            for (int j = 0; j < charArray.length; j++) {
                char element = charArray[j];
                for (int k = j + 1; k < charArray.length; k++) {
                    if (element == charArray[k]) {
                        allDifferent = false;
                        break;
                    }
                }
            }
            if (allDifferent) {
                System.out.println("Word with all unic elements is: " + word);
                break;
            }

        }
    }
}
