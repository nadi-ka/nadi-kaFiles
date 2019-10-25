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
            myArr[i] = 0 + (int) (Math.random() * (10 - 0));
        }
        for (i = 0; i < myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }
        System.out.println("\n");
        int first = myArr[0];
        int second = myArr[1];
        for (i = 2; i < myArr.length; i++) {
            int tmp = myArr[i];
            myArr[i] = first;
            first = second;
            second = tmp;
        }
        myArr[0] = first;
        myArr[1] = second;
        for (i = 0; i < myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }


    }
}
