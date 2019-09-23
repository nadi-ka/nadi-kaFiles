package com.company;

public class Array {
    public final String spaceString = " ";
    private String[] cities;
    private int arraySize;
    private String firstElement;
    private String lastElement;

    public Array(String[] cities, int arraySize) {
        this.cities = cities;
        this.arraySize = arraySize;
    }

    public void setFirstElement(String firstElement) {
        this.firstElement = firstElement;
    }

    public String getFirstElement() {
        return firstElement;
    }

    public void setLastElement(String lastElement) {
        this.lastElement = lastElement;
    }

    public String getLastElement() {
        return lastElement;
    }

    public void printArray(String[] anyArr) {
        for (String element : anyArr) {
            System.out.print(element + spaceString);
        }
        System.out.println();
    }

    public void divisionOfTheArrayLengthByTheIndexOfTheElement(String[] anyArr) {
        try {
            for (int i = 0; i < anyArr.length; i++) {
                int division = (int) anyArr.length / i;
                System.out.print(division + spaceString);
            }
        } catch (ArithmeticException ex) {
            System.out.println("One of your indexes is null. You've tried to divide by zero. " + ex.toString());
        } finally {
            for (int i = 1; i < anyArr.length; i++) {
                int division = (int) anyArr.length / i;
                System.out.print(division + " ");
            }
            System.out.println("We've changed lower bound: not from element[0] - from element[1]");
        }
    }

    public void divisionOfTheLengthOfTheNextElementByTheIndexOfPrevious(String[] anyArr) {
        try {
            for (int i = 0; i < anyArr.length; i++) {
                int result = (int) anyArr[i + 1].length() / i;
                System.out.println(result);
            }

        } catch (ArithmeticException ex) {
            System.out.println("The first index is zero. You can't divide by zero " + ex.toString());
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element whith The index [anyArr.length()+1] is not exist. " + ex.toString());
        }
    }

    public void printFirstPlusLast() {
        try {
            System.out.println("Merger first and last elements: " + getFirstElement().concat(getLastElement()));
        } catch (NullPointerException ex) {
            System.out.println("Your name of the city is null. Please, set it. " + ex.toString());
        }
    }

    public void printTheLastAndConcatWithTheFirst(String[] anyArr) {
        try {
            System.out.println(anyArr[anyArr.length]);
            getFirstElement().concat(getLastElement());
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
            System.out.println("You've tried to print element, which isn't exist. You can't merger null Strings " +
                    ex.toString());

        }

    }
}