package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите целое число");
        Scanner Scanner = new Scanner(System.in);
        int size = Scanner.nextInt();
        int[] myArr = new int[size];
        int i;
        for (i = 0; i < myArr.length; i++) {
            myArr[i]=0 + (int)(Math.random() * (10-0));
        }
        for (i = 0; i < myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }
        System.out.println("\n");
        int min = myArr[0];
        int max = myArr[0];
        for (i=0; i<myArr.length; i++){
            if (myArr[i] < min){
                min=myArr[i];
            }
            if (myArr[i] > max){
                max = myArr[i];
            }

        }
        System.out.println("min=" + min + " max=" + max);
    }
}
