package com.company;

public class Task3_1 {

    public static void main(String[] args) {
        int upperBoard = 100;
        int a = 0 + (int)(Math.random() * (upperBoard - 0));
        int b = 0 + (int)(Math.random() * (upperBoard - 0));
        System.out.print(a + " " + b);
        System.out.println();
        int result = getMin(a, b);
        System.out.println(result);
    }

    public static int getMin (int first, int second){
        int min = 0;
        if (first < second){
            min = first;
        }
        else if (first == second){
            System.out.println("Digits are equal");
        }
        else {
            min = second;
        }
       return min;
    }
}
