package com.company;

public class Task3_5 {

    public static void main(String[] args) {
        int upperBoard = 20;
        int a = (int) (Math.random() * upperBoard);
        int b = (int) (Math.random() * upperBoard);
        int c = (int) (Math.random() * upperBoard);
        System.out.println(a + " " + b + " " + c);
        int result = getDifference(a, b, c);
        System.out.println(result);
    }

    public static int getDifference(int a, int b, int c) {
        int result = a - b - c;
        return result;
    }

}
