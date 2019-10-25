package com.company;

import java.util.Scanner;

public class Task3_4 {

    public static void main(String[] args) {
        System.out.println("Enter an integer");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int result = getToCube(num);
        System.out.println(result);
    }
    public static int getToCube (int number){
        int result = number * number * number;
        return result;
    }
}
