package com.company;

import java.util.Scanner;

public class Task8_4 {
    private static int diffWords(String st) {
        StringBuffer uniqueStr = new StringBuffer();
        //unique String
        String current;
        //current symbol in String
        for (int i = 0; i < st.length(); i++) {
            current = String.valueOf(st.charAt(i));
            //get current symbol
            if (uniqueStr.indexOf(current) == -1)
                uniqueStr.append(current);
            //if there isn't symbol in the String - append it.
        }

        return uniqueStr.length();
        //return the length of unique String
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
        String goal = linesArray[0];
        //word, that we're looking for
        for (int i = 0; i < linesArray.length; i++) {
            System.out.print(linesArray[i] + ", ");
            if (diffWords(linesArray[i]) < diffWords(goal))
                goal = linesArray[i];
            //new min element
        }
        System.out.println();
        System.out.println("Word: " + goal + " - number of different symbols: " + diffWords(goal));
    }

}
