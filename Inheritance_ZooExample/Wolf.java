package com.company;

public class Wolf extends Predators{
    private String colour;
    private int weight;

    public Wolf(String colour, int weight){
        this.colour = colour;
        this.weight = weight;
    }
    public void predatorIsHunting (){
        System.out.println("The wolf has just catched a rabbit");
    }
    public void printAboutWolf(){
        System.out.println("It's " + colour + " wolf. It's weight is: " + weight + " kilo.");
    }
}
