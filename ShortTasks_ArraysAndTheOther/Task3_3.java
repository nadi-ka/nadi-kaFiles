package com.company;

public class Task3_3 {

    public static void main(String[] args) {
        int upperBoard = 100;
        int num = 0 + (int)(Math.random() * (upperBoard - 0));
        System.out.println(num);
        int result = getToSquare(num);
        System.out.println(result);
    }

    public static int getToSquare(int number) {
        int result = number * number;
        return result;
    }

}
