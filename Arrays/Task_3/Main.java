package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Введите целое число");
        Scanner Scanner = new Scanner(System.in);
        int input = Scanner.nextInt();
        int size = input;
        int[] myArr = new int[size];
        int i;
        for (i = 0; i < myArr.length; i++) {
            myArr[i] = 0 + (int) (Math.random() * (10 - 0));
        }
        for (i=0; i< myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }
        System.out.print("\n");
        while (i < myArr.length) {
            if (i % 3 == 0){
                int result = myArr[i] * 2;
                System.out.println(result);
            }
        }

    }


}


