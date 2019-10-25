package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Введите целое числою");
        Scanner Scanner = new Scanner(System.in);
        int input = Scanner.nextInt();
        int size = input;
        int[] myArr = new int[size];
        int i;
        for (i = 0; i < myArr.length; i++) {
            myArr[i] = 0 + (int) (Math.random() * (10 - 0));
        }
        for (i = 0; i < myArr.length; i++) {
            System.out.println(myArr[i] + " ");
        }
        for (i = myArr.length - 1; i >= 0; i--) {

            System.out.println(myArr[i] + " ");
        }

    }


}
