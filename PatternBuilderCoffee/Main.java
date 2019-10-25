package com.company;

public class Main {

    public static void main(String[] args) {

        //First coffee will be with milk only;

        Coffee coffee1 = new Coffee.Builder()
                .withMilk("with milk")
                .build();
        System.out.println(coffee1);

        //The Second one will be with milk and sugar;

        Coffee coffee2 = new Coffee.Builder()
                .withMilk("with milk")
                .withSugar("sugar")
                .build();
        System.out.println(coffee2);

        //And finally the Third one will be with all ingredients;

        Coffee coffee3 = new Coffee.Builder()
                .withMilk("with milk")
                .withSugar("sugar")
                .withCinnamon("cinnamon")
                .build();
        System.out.println(coffee3);
    }
}
