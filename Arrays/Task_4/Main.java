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
        for (i = 0; i < myArr.length; i++) {
            System.out.print(myArr[i] + " ");
        }
        System.out.print("\n");
        int sumFlag = 0;
        for (i=0; i < myArr.length; i++){
            if (myArr[i] == 0){
                sumFlag +=1;
            }
        }
        System.out.println(sumFlag);
        if (sumFlag == 0){
            System.out.println("Нет элементов равных нулю.");
        }

    }


}

