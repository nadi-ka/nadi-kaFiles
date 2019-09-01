package com.company;


public class Abzac {
    public String text;
    public int indexNumberOfChange;

    public Abzac() {
    }

    public Abzac(int i, String text) {
        this.indexNumberOfChange = i;
        this.text = text;
    }

    public void printNewArray(char[] anyArr) {
        int indexStart = 0;
        int indexEnd;
        for (int i = 0; i < anyArr.length; i++) {
            if (anyArr[i] == ' ' || anyArr[i] == '.' || anyArr[i] == ',') {
                indexEnd = i;

                for (i = indexStart; i < indexEnd; i=i+indexNumberOfChange) {
                        anyArr[i] = '%';
                }
                if (i < anyArr.length - 1 && anyArr[i + 1] == ' ') {
                    indexStart = indexEnd + 2;
                } else {
                    indexStart = indexEnd+1;
                }

            }
        }
            for (int i = 0; i < anyArr.length; i++) {
                System.out.print(anyArr[i]);
            }
    }

}

