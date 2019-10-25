package com.company;

import java.util.Scanner;

public class Task8_8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an integer.");
        int numberOfLines = scanner.nextInt();
        String word1 = null;
        String word2 = null;
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("Enter the line number " + (i + 1));
            String word = scanner.next();
            char[] charArray = word.toCharArray();
            boolean isDigitsPalindrome = true;
            int middle = (int) charArray.length / 2;
            for (int j = 0; j < middle; j++) {
                if (charArray[j] != charArray[charArray.length - 1 - j] ||
                        !Character.isDigit(charArray[j])) {
                    isDigitsPalindrome = false;
                    break;
                }
            }
            if (isDigitsPalindrome && Character.isDigit(charArray[middle])) {
                if (word1 == null) {
                    word1 = word;
                } else {
                    word2 = word;
                }
                if (word2 != null) {
                    System.out.println("Word-palindrom is: " + word2);
                    break;
                }
            }
        }
        if (word1 != null && word2 == null) {
            System.out.println("Word-palindrom is: " + word1);
        }
    }
}
