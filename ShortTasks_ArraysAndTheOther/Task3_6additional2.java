import java.util.Scanner;

public class Task3_6additional2 {
    public static void main(String[] args) {
        System.out.println("Enter an integer");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] arr1 = new int[size];
        int i;
        int upperBoard = 10;
        String emptyString = " ";
        for (i = 0; i < arr1.length; i++) {
            arr1[i] = 0 + (int) (Math.random() * (upperBoard - 0));
        }
        for (i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + emptyString);
        }
        System.out.println();
        int resultMyltiply = getArrMultiply(arr1);
        System.out.println(resultMyltiply);
    }

    public static int getArrMultiply(int[] anyArr) {
        int result = 1;
        for (int i = 0; i < anyArr.length; i++) {
            result = result * anyArr[i];
        }
            return result;
    }
}

