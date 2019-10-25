package com.company;

import java.util.Scanner;

public class Task8_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an integer.");
        int numberOfLines = scanner.nextInt();
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("Enter the line number " + (i + 1));
            String word = scanner.next();
            char[] charArray = word.toCharArray();
            boolean isCodeOfTheNextSymbolBigger = true;
            for (int j = 0; j < charArray.length - 1; j++) {
                if (charArray[j] > charArray[j + 1]) {
                    isCodeOfTheNextSymbolBigger = false;
                    break;
                }
            }
            if (isCodeOfTheNextSymbolBigger) {
                System.out.println("Word, where codes of symbols increase is: " + word);
                break;
            }
        }
    }
}
