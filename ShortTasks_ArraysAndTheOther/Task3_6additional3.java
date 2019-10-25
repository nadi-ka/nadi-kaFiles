package com.company;

import java.util.Scanner;

public class Task3_6additional3 {

    public static void main(String[] args) {
        System.out.println("Enter an integer");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] arr = new int[size];
        int i;
        int upperBound = 10;
        String emptyString = " ";
        for (i = 0; i < arr.length; i++) {
            arr[i] = 0 + (int) (Math.random() * (upperBound - 0));
        }
        for (i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + emptyString);
        }
        System.out.println();
        int[] arrNew = myltiplayEveryThirdElement(arr);
        for (i = 0; i < arrNew.length; i++) {
            System.out.print(arrNew[i] + emptyString);
        }
    }

    public static int[] myltiplayEveryThirdElement(int[] anyMas) {
        int i;
        int[] resultMas = new int[anyMas.length];
        for (i = 0; i < anyMas.length; i++) {
            if (i % 3 == 0) {
                resultMas[i] = anyMas[i] * 3;
            } else {
                resultMas[i] = anyMas[i];
            }
        }

        return resultMas;

    }
}

