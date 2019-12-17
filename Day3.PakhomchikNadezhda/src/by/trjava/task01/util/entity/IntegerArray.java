package by.trjava.task01.util.entity;

import by.trjava.exceptions.NegativeSizeException;
import by.trjava.exceptions.NullValueException;

import java.util.Iterator;

public class IntegerArray implements Iterable<Integer> {
    private int[] array;

    public IntegerArray() {
        this.array = new int[0];
    }

    public IntegerArray(int size) throws NegativeSizeException{
        if (size >= 0) {
            array = new int[size];
        }
        else {
            throw new NegativeSizeException("by.trjava.task01.util.entity.IntegerArray, constructor " +
                    "IntegerArray(int size). Array size cannot be a negative number!");
        }
    }

    public IntegerArray(int[] array) throws NullValueException {
        this.setArray(array);
    }

    public void setArray(int[] array) throws NullValueException {
        if (array != null) {
            this.array = array;
        }
        else {
            throw new NullValueException(" by.trjava.task01.util.entity.IntegerArray, setArray(int[] array)." +
                    " The array shouldn't reference to null!");
        }
    }
    public boolean setElement(int index, int elementValue) {
        if (index >= 0 && index < array.length) {
            array[index] = elementValue;
            return true;
        }
        return false;
    }

    public int[] getArray() {
        return array;
    }

    public Iterable<Integer> getArray1() {
        return this;
    }

    public int getArraySize() {
        return this.array.length;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < array.length;
            }

            @Override
            public Integer next() {
                return array[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

//    @Override
//    public boolean equals(Object object) {
//        if (this == object) {
//            return true;
//        }
//        if (object == null || this.getClass() != object.getClass()) {
//            return false;
//        }
//        IntegerArray integerArray = (IntegerArray) object;
//        if (array.length != integerArray.getArraySize()) {
//            return false;
//        }
//        for (int i = 0; i < array.length; i++) {
//            int in = 0;
//            in = integerArray.getArray()[i];
//            if (array[i] != integerArray.getArray()[i]) {
//                return false;
//            }
//        }
//        return true;
//    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        for (int i = 0; i < array.length; i++) {
            result = prime * result + array[i];
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName() + "@[");
        for (int element : array) {
            stringBuilder.append(element + "; ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    //Methods for sort;
    public void bubbleSort() {
        boolean isSorted = false;
        int tmp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    tmp = array[i];
                    array[i] = array[i+1];
                    array[i + 1] = tmp;
                    isSorted = false;
                }
            }
        }
    }
    public void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i - 1;
            while (j >= 0 && current < array[j]) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }
    }
    public void selectionSort() {
        for (int i = 0; i < array.length; i++) {
            int min = array[i];
            int indexMin = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    indexMin = j;
                }
            }
            int tmp = array[i];
            array[i] = min;
            array[indexMin] = tmp;
        }
    }
    //Binary search method;
    public int binarySearch(int elementToSearch) {
        int firstIndex = 0;
        int lastIndex = array.length - 1;
        while (firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            if (elementToSearch == array[middleIndex]) {
                return middleIndex;
            }
            else if (elementToSearch > array[middleIndex]) {
                firstIndex = middleIndex + 1;
            }
            else if (elementToSearch < array[middleIndex]) {
                lastIndex = middleIndex - 1;
            }
        }
        return -1;
    }

    //filling of the array with random numbers method;
    public void fillArrayRandomly() {
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
    }
}
