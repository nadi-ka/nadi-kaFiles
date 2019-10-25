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
        for (i = 0; i < myArr.length - 1; i++) {
            if (myArr[i + 1] <= myArr[i]) {
                System.out.println("Последовательность не является возрастающей");
                break;
            }

        }
        if (i == myArr.length - 1) {
            System.out.println("Последовательность является возрастающей");
        }
    }
}


