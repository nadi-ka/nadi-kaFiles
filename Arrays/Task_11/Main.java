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
        int before = myArr[0];
        int after = myArr[2];
        int myArrFirst = (myArr[myArr.length - 1] + myArr[1]) / 2;
        int myArrLast = (myArr[myArr.length - 2] + myArr[0]) / 2;
        for (i = 1; i < myArr.length - 1; i++) {
            int tmp = myArr[i];
            myArr[i] = (before + after) / 2;
            if (i == myArr.length - 2) {
                break;
            }
            before = tmp;
            after = myArr[i + 2];

        }
        myArr[0] = myArrFirst;
        myArr[myArr.length - 1] = myArrLast;


        for (i = 0; i < myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }
    }
}
