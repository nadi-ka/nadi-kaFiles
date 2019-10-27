package com.company;

import java.util.Comparator;

public class NewComparatorForDinosaurs implements Comparator<Dinosaurs> {

    public int compare(Dinosaurs dino1, Dinosaurs dino2) {
        if (dino1.getGrowth() == dino2.getGrowth()) {
            return 0;
        }
        if (dino1.getGrowth() > dino2.getGrowth()) {
            return 1;
        } else {
            return -1;
        }
    }

}