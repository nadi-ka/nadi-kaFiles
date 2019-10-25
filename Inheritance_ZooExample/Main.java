package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Herbivores herbivores = new Herbivores();
        herbivores.isFoodGoodForThisAnimal(herbivores.foods);
        herbivores.isMyVoise();
        System.out.println();

        Predators predators = new Predators();
        predators.isFoodGoodForThisAnimal(predators.foods);
        predators.predatorIsHunting();
        System.out.println();

        Horse horse = new Horse("Marusja", "Arabic", 8);
        horse.isFoodGoodForThisAnimal(horse.foods);
        horse.IsMyVoise();
        horse.printAllAboutHorse();
        System.out.println();

        Deer deer = new Deer("Reindeer", "Polar circle");
        deer.isFoodGoodForThisAnimal(deer.foods);
        deer.IsMyVoise();
        deer.printAllAboutDeer();
        System.out.println();

        Tiger tiger = new Tiger("Bengal", 6);
        tiger.isFoodGoodForThisAnimal(tiger.foods);
        tiger.predatorIsHunting();
        tiger.printAboutTiger();
        System.out.println();

        Wolf wolf = new Wolf("grey", 45);
        wolf.isFoodGoodForThisAnimal(wolf.foods);
        wolf.predatorIsHunting();
        wolf.printAboutWolf();
        System.out.println();

        ArrayList <String> newArr = new ArrayList<String>();
        newArr.add("horse");
        newArr.add("deer");
        newArr.add("tiger");
        newArr.add("wolf");
        Zoo zoo = new Zoo(newArr);
        zoo.printZoo();
        System.out.println(zoo.getAmountOfAnimalsInTheZoo());

    }
}
