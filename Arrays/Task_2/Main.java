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
        for (i=0; i< myArr.length; i++) {
            System.out.println(myArr[i] + " ");
        }
        int result = 1;
        for (i = 0; i < myArr.length; i++) {
            result = result * myArr[i];
        }
        System.out.println(result);

    }


}

