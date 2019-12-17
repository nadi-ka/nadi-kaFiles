package by.trjava.task02.util;

public class JaggedArrayService {

    public static int getSumOfElementsInRow(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static void bubbleSortBySumInRow(int[][] arrayJagged, boolean isAscending) {
        boolean isSorted = false;
        int[] tmp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < arrayJagged.length - 1; i++) {
                int sumInCurrentLine = getSumOfElementsInRow(arrayJagged[i]);
                int sumInNextLine = getSumOfElementsInRow(arrayJagged[i + 1]);
                if (isAscending) {
                    if (sumInCurrentLine > sumInNextLine) {
                        tmp = arrayJagged[i];
                        arrayJagged[i] = arrayJagged[i + 1];
                        arrayJagged[i + 1] = tmp;
                        isSorted = false;
                    }
                } else {
                    if (sumInCurrentLine < sumInNextLine) {
                        tmp = arrayJagged[i];
                        arrayJagged[i] = arrayJagged[i + 1];
                        arrayJagged[i + 1] = tmp;
                        isSorted = false;
                    }
                }
            }
        }
    }

    public static int getMaxOrMinValueInRow(int[] array, boolean max) {
        int requiredValue = array[0];
        for (int i = 0; i < array.length - 1; i++) {
            if (max) {
                if (array[i + 1] > requiredValue) {
                    requiredValue = array[i + 1];
                }
            } else {
                if (array[i + 1] < requiredValue) {
                    requiredValue = array[i + 1];
                }
            }
        }
        return requiredValue;
    }

    public static void bubbleSortByValueInRow(int[][] arrayJagged, boolean maxValue, boolean isAscending) {
        boolean isSorted = false;
        int[] tmp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < arrayJagged.length - 1; i++) {
                int requiredValueCurrent = getMaxOrMinValueInRow(arrayJagged[i], maxValue);
                int requiredValueNext = getMaxOrMinValueInRow(arrayJagged[i + 1], maxValue);
                if (isAscending) {
                    if (requiredValueCurrent > requiredValueNext) {
                        tmp = arrayJagged[i];
                        arrayJagged[i] = arrayJagged[i + 1];
                        arrayJagged[i + 1] = tmp;
                        isSorted = false;
                    }
                } else {
                    if (requiredValueCurrent < requiredValueNext) {
                        tmp = arrayJagged[i];
                        arrayJagged[i] = arrayJagged[i + 1];
                        arrayJagged[i + 1] = tmp;
                        isSorted = false;
                    }
                }
            }
        }
    }
}
