package com.company;

public class Predlozhenije extends Abzac{
    protected int amountOfSentenses = 0;

    public void setAmountOfSentenses(int amountOfSentenses){
        this.amountOfSentenses = amountOfSentenses;
    }

    public int getAmountOfSentenses (char[] anyArr) {
        amountOfSentenses = 0;
        for (int i = 0; i < anyArr.length; i++) {
            if (anyArr[i] == '.' || anyArr[i] == '!' || anyArr[i] == '?') {
                amountOfSentenses = amountOfSentenses + 1;
            }
        }
        return amountOfSentenses;
    }
    public void printAmountOfSentenses (){
        System.out.println("There're " + amountOfSentenses + " sentenses in the text");
    }
}
