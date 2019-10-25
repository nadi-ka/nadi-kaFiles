package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Herbivores extends Animals {

    protected ArrayList<String> foods = new ArrayList<String>(Arrays.asList
            ("grass", "carrot", "apples", "oats", "flowers"));

    @Override
    public void isFoodGoodForThisAnimal(ArrayList<String> foods) {
        boolean iEatThis = true;
        System.out.println("I'm hungry! Please, feed me!");
        System.out.println("I like to eat:");
        for (String food : foods) {
            System.out.println(food);
        }
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("i'm herbivore. What do you want to give me?");
        String whatDoYouWantToGiveMe = scanner.next();
        for (String food : foods) {
            if (food.equals(whatDoYouWantToGiveMe)) {
                System.out.println("Mmm! Tasty!");
                iEatThis = true;
                break;
            }
            else {
                iEatThis = false;
            }
        }
        if (iEatThis == false) {
            System.out.println("No, fuh!!! I'll not eat this!");
        }
    }

    public void isMyVoise() {
        System.out.println("This's a voice of Herbivores!");
    }
}
