package com.company;

import java.util.PriorityQueue;

public class Dinosaurs implements Comparable<Dinosaurs> {
    private String spesies;
    private int weightInKilogramms;
    private int growth;

    public Dinosaurs(String spesies, int weightInKilogramms, int growth) {
        this.spesies = spesies;
        this.weightInKilogramms = weightInKilogramms;
        this.growth = growth;
    }

    public String getSpesies() {
        return spesies;
    }

    public int getWeightInKilogramms() {
        return weightInKilogramms;
    }

    public int getGrowth() {
        return growth;
    }

    public int compareTo(Dinosaurs anotherDinosaur) {
        if (this.weightInKilogramms == anotherDinosaur.weightInKilogramms) {
            return 0;
        } else if (this.weightInKilogramms < anotherDinosaur.weightInKilogramms) {
            return -1;
        } else {
            return 1;
        }
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dinosaurs{ ");
        sb.append("spesies=").append(spesies);
        sb.append(", weight in kilogramms=").append(weightInKilogramms);
        sb.append(", growth").append(growth);
        sb.append('}');
        return sb.toString();
    }

}
