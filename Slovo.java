package com.company;

public class Slovo extends Predlozhenije {
    protected int amountOfWords = 0;
    public void setAmountOfWords(int amountOfWords){
        this.amountOfWords = amountOfWords;
    }
    public int getAmountOfWords (char [] anyArr) {
        amountOfWords = 1;
        for (int i = 0; i < anyArr.length; i++) {
            if (anyArr[i] == ' ') {
                amountOfWords = amountOfWords + 1;
            }
        }
        return amountOfWords;
    }
    public void printAmountOfWords(){
        System.out.println("There are " + amountOfWords + " words in the text.");
    }

    static void countSymbolsInWord(char[] anyArr) {
        for (int i = 0; i < anyArr.length; i++) {
            String s = "";
            while (i < anyArr.length && (anyArr[i] != ' ' && anyArr[i] !='.' && anyArr[i] != ',')) {
                s = s + anyArr[i];
                i++;

            }
            if (s.length() > 0) {
                System.out.println("In the word " + s + ": " + s.length() + " symbols.");
            }
        }


    }
}
