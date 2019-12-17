package by.trjava.task02.util;

import by.trjava.exceptions.NegativeSizeException;
import by.trjava.exceptions.NullValueException;

public class JaggedArray {
    private int[][] jaggedArray;

    public JaggedArray() {
        jaggedArray = new int[0][0];
    }

    public JaggedArray(int sizeOuter) throws NegativeSizeException {
        if (sizeOuter > 0) {
            this.jaggedArray = new int[sizeOuter][];
        } else {
            throw new NegativeSizeException("by.trjava.task02.util.JaggedArray, setSizeOuter(int sizeOuter)." +
                    " Size of the array cannot be negative!");
        }
    }

    public JaggedArray(int[][] jaggedArray) throws NullValueException {
        if (jaggedArray != null) {
            if (jaggedArray.length > 0) {
                for (int i = 0; i < jaggedArray.length; i++) {
                    if (jaggedArray[i] != null) {
                        this.jaggedArray = jaggedArray;
                    }
                }
            }
        } else {
            throw new NullValueException("by.trjava.task02.util.JaggedArray, constructor " +
                    "JaggedArray(int [][] jaggedArray). Reference to array shouldn't be equals null!");
        }
    }

    public void setSizesInnerRandomly() {
        for (int i = 0; i < jaggedArray.length; i++) {
            int lengthInner = (int) (Math.random() * 10);
            jaggedArray[i] = new int[lengthInner];
        }
    }

    public int[][] getArray() {
        return jaggedArray;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        JaggedArray jaggedArray = (JaggedArray) object;
        if (getArray().length != jaggedArray.getArray().length) {
            return false;
        }
        for (int i = 0; i < getArray().length; i++) {
            if (getArray()[i].length != jaggedArray.getArray()[i].length) {
                return false;
            }
        }
        for (int i = 0; i < getArray().length; i++) {
            for (int j = 0; j < getArray()[i].length; j++) {
                if (getArray()[i][j] != jaggedArray.getArray()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        for (int i = 0; i < jaggedArray.length; i++) {
            for (int j = 0; j < jaggedArray[i].length; j++) {
                result = prime * result + jaggedArray[i][j];
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getClass().getName() + "@\n[");
        for (int i = 0; i < jaggedArray.length; i++) {
            for (int j = 0; j < jaggedArray[i].length; j++) {
                stringBuilder.append(jaggedArray[i][j] + ", ");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("];");
        return stringBuilder.toString();
    }

    public void fillArrayRandomly() throws NullValueException {
        if (jaggedArray == null && jaggedArray.length == 0) {
            throw new NullValueException("by.trjava.task02.util.JaggedArray, fillArrayRandomly()." +
                    " Null reference to array or array.length == 0!");
        }
        for (int i = 0; i < jaggedArray.length; i++) {
            if (jaggedArray[i].length == 0) {
                continue;
            } else {
                for (int j = 0; j < jaggedArray[i].length; j++) {
                    int randomNumber = (int) (Math.random() * 10);
                    jaggedArray[i][j] = randomNumber;
                }
            }
        }
    }
}
