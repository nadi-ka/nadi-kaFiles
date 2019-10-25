package com.company;

public class Tiger extends Predators{
    private String species;
    private int needsKilogrammsOfMeatEveryDay;

    public Tiger(String species, int needsKilogrammsOfMeatEveryDay){
        this.species = species;
        this.needsKilogrammsOfMeatEveryDay = needsKilogrammsOfMeatEveryDay;
    }

    public void predatorIsHunting (){
        System.out.println("The tiger has just catched an antilope");
    }
    public void printAboutTiger (){
        System.out.println("The tiger's species is: " + species + ". It needs " + needsKilogrammsOfMeatEveryDay
        + " kilo of meat every day.");
    }
}
