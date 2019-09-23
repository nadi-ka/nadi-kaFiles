package com.company;

public class Main {

    public static void main(String[] args) {
        String[] cities = {"Stockholm", "Tokio", "Singapore", "Athens", "Budapest"};
        Array array = new Array(cities, cities.length);
        array.setFirstElement(cities[0]);
        array.setLastElement(cities[cities.length - 1]);
        array.printArray(cities);
        array.divisionOfTheArrayLengthByTheIndexOfTheElement(cities);
        System.out.println();
        array.divisionOfTheLengthOfTheNextElementByTheIndexOfPrevious(cities);
        System.out.println();

        String[] cities1 = {null, "Tokio", "Singapore", "Athens", "Budapest"};
        array.setFirstElement(cities1[0]);
        array.setLastElement(cities1[cities1.length - 1]);
        array.printArray(cities1);
        array.printFirstPlusLast();
        array.printTheLastAndConcatWithTheFirst(cities1);

        //В примере ниже блок finally не будет отработан, так как происходит системный выход из программы.
        try {
            System.exit(0);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            System.out.println("This message will not be printed.");
        }

    }

}
