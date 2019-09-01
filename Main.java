package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please, enter several sentenses.");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        System.out.println("Enter an integer.");
        int indexNumberOfChange = scanner.nextInt();
        char [] arrayText = text.toCharArray();

        Predlozhenije predlozhenije = new Predlozhenije();
        predlozhenije.setAmountOfSentenses(predlozhenije.getAmountOfSentenses(arrayText));
        System.out.println(predlozhenije.getAmountOfSentenses(arrayText));
        predlozhenije.printAmountOfSentenses();
        System.out.println();
        //Upper there are methods from the Class Predlozhenije.
        Slovo slovo = new Slovo();
        slovo.setAmountOfWords(slovo.getAmountOfWords(arrayText));
        System.out.println(slovo.getAmountOfWords(arrayText));
        Slovo.countSymbolsInWord(arrayText);
        slovo.printAmountOfWords();
        System.out.println();
        //Upper there are shown methods from the Class Slovo, including static method.
        Abzac abzac = new Abzac(indexNumberOfChange, text);
        abzac.printNewArray(arrayText);
        /*Upper there are shown methods from the Class Abzac, including method, which changes
        every n-th symbol of the word to '%'.
         */
    }
}
