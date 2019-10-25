package com.company;

public class Horse extends Herbivores{
    private String name;
    private String breed;
    private int age;

    public Horse(String name, String breed, int age){
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
    public void IsMyVoise (){
        System.out.println("I-go-go!");
    }
    public void printAllAboutHorse (){
        System.out.println("The horse's name is: " + name + " breed: " + breed + " age: " + age);
    }
}
