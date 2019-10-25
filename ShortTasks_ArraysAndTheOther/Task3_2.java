package com.company;

import java.util.Scanner;

public class Task3_2 {

    public static void main(String[] args) {
        System.out.println("Enter the integer");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        boolean result = getEvenOrOdd (number);
        System.out.println(result);

    }
    public static boolean getEvenOrOdd (int number){
        boolean result;
        if (number%2 == 0){
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }
}
