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
            System.out.println("Введите целое число - элемент массива");
            Scanner Scanner1 = new Scanner(System.in);
            int num = Scanner1.nextInt();
            myArr[i] = num;
        }
        for (i = 0; i < myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }
        System.out.println("\n");
        for (i = 0; i < myArr.length-1; i += 2) {
            int c = myArr[i];
            myArr[i] = myArr[i + 1];
            myArr[i + 1] = c;
        }
        for (i = 0; i < myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }
    }
}