package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter number");
        Scanner Scanner = new Scanner(System.in);
        int size1 = Scanner.nextInt();
        System.out.println("Enter one more number");
        int size2 = Scanner.nextInt();
        int[][] myArr = new int[size1][size2];
        int i;
        int j;
        for (i = 0; i < size1; i++) {
            for (j = 0; j < size2; j++) {
                myArr[i][j] = (int) (Math.random() * (10 - 0));
            }
        }
        for (i = 0; i < size1; i++) {
            System.out.println();
            for (j = 0; j < size2; j++) {
                System.out.print(myArr[i][j] + " ");
            }
        }
        System.out.println();
            for (i = 0; i < size1; i++) {
                System.out.println();
                for (j = size2 - 1; j >= 0; j--) {
                    System.out.print(myArr[i][j] + " ");
                }
            }
        System.out.println();
            int max = myArr[0][0];
            int min = myArr[0][0];

        for (i = 0; i < size1; i++) {
            for (j = 0; j < size2; j++) {
                if (myArr[i][j]>max){
                    max = myArr[i][j];
                }
            }

        }
        System.out.println(max);
        for (i = 0; i < size1; i++) {
            for (j = 0; j < size2; j++) {
                if (myArr[i][j] < min) {
                    min = myArr[i][j];
                }
            }
        }
        System.out.println(min);

    }
}
