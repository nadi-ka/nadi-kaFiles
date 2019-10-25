package com.company;

import java.util.ArrayList;

public class Zoo {
    protected ArrayList<String> zooAnimals = new ArrayList<String>();
    protected int amountOfAnimalsInTheZoo;

    public Zoo (ArrayList zooAnimals){
        this.zooAnimals = zooAnimals;
    }
    public int getAmountOfAnimalsInTheZoo() {
        return amountOfAnimalsInTheZoo = zooAnimals.size();
    }
    public void printZoo (){
        for (Object zooAnimal: zooAnimals){
            System.out.println(zooAnimal + " ");
        }
    }




}
