import java.util.Scanner;

public class Calculator {
    private int a;
    private int b;

    public Calculator() {

    }

    public Calculator(int a, int b) {
        Scanner scanner = new Scanner((System.in));
        System.out.println("Please, enter an integer.");
        this.a = scanner.nextInt();
        System.out.println("Please, enter the second integer.");
        this.b = scanner.nextInt();
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getSum(int a, int b) {
        return a + b;
    }

    public int getDifference(int a, int b) {
        return a - b;
    }

    public int getMultiply(int a, int b) {
        return a * b;
    }

    public int getSquare(int a) {
        return a * a;
    }

    public int getDivision(int a, int b) {
        if (b == 0) {
            try {
                int result = a / b;
            } catch (ArithmeticException ex) {
                System.out.println("Division by zero is impossible " + ex.toString());
            }
        }
        return a / b;
    }

    public int getCube(int a) {
        return a * a * a;
    }

}
