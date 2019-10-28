package com.company;

public class Main {

    public static void main(String[] args) {
	int num1 = Calculator.getInt();
	int num2 = Calculator.getInt();
	char operation = Calculator.getOperation();
	int result = Calculator.calc(num1, num2, operation);
        System.out.println("The result is: " + result);
    }
}
