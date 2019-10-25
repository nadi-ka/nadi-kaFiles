package com.company;

public class Deer extends Herbivores{
    String spesiesOfTheDeer;
    String habitat;

    public Deer (String spesiesOfTheDeer, String habitat){
        this.spesiesOfTheDeer = spesiesOfTheDeer;
        this.habitat = habitat;
    }

    public void IsMyVoise (){
        System.out.println("E-e-e! - the deer's voise.");
    }
    public void printAllAboutDeer (){
        System.out.println("it' s the " + spesiesOfTheDeer + ", it's habitat is: " + habitat);
    }
}
