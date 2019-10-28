package com.company;

import java.util.Scanner;

public class Calculator {
    static Scanner scanner = new Scanner(System.in);

    public static int getInt() {
        System.out.println("Enter an integer");
        int num;
        if (scanner.hasNextInt()) {
            num = scanner.nextInt();
        } else {
            System.out.println("You've entered incorrect digit. Please, try again!");
            scanner.next();
            num = getInt(); //recursion
        }
        return num;
    }

    public static char getOperation() {
        System.out.println("Enter an operation for calculator");
        char operation;
        if (scanner.hasNext()) {
            operation = scanner.next().charAt(0);
        } else {
            System.out.println("You've entered incorrect operation. Please, try again!");
            scanner.next();
            operation = getOperation(); //recursion
        }
        return operation;
    }

    public static int calc(int num1, int num2, char operation) {
        int result;
        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                try {
                    result = num1 / num2;
                    break;

                } catch (ArithmeticException ex) {
                    ex.printStackTrace();
                }
            default:
                System.out.println("The operation wasn't identified. Please, try again!");
                result = calc(num1, num2, getOperation()); //recursion
        }
        return result;
    }

}
