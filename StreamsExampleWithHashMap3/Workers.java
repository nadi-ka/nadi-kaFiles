package com.company;

import java.util.LinkedList;

public class Workers {
    private LinkedList<Workers> workersList = new LinkedList<>();
    private String name;
    private int age;
    private boolean hasCriminalRecordOrParkingFine;

    public Workers() {
    }

    public Workers(String name, int age, boolean hasCriminalRecordOrParkingFine) {
        this.name = name;
        this.age = age;
        this.hasCriminalRecordOrParkingFine = hasCriminalRecordOrParkingFine;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getHasCriminalRecordOrParkingFine() {
        return hasCriminalRecordOrParkingFine;
    }

    public LinkedList<Workers> getWorkers() {
        return workersList;
    }

    public void addWorkerToList(Workers worker) {
        workersList.add(worker);
    }

    public int getSimpleNumberFrom3To107() {
        int loverBoard = 2;
        int upperBoard = 107;
        int numberFrom3To107 = loverBoard + (int) (Math.random() * (upperBoard + 1 - loverBoard));
        for (int i = loverBoard; i < numberFrom3To107; i++) {
            if (numberFrom3To107 % i == 0) {
                numberFrom3To107 = loverBoard + (int) (Math.random() * (upperBoard + 1 - loverBoard));
                continue;
            }

        }
        System.out.println(numberFrom3To107);
        return numberFrom3To107;

    }

    @Override
    public String toString() {
        return "Worker name: " + name + ", Worker age: " + age + ", if he/she has criminal record or parking fines: " +
                hasCriminalRecordOrParkingFine + ".";

    }
}
