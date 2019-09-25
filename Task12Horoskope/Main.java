package com.company;

import java.time.zone.ZoneRules;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the day of your birth as a digit.");
        int dayOhBirth = scanner.nextInt();
        System.out.println("Enter the month of your birth as a digit.");
        int monthOfBirth = scanner.nextInt();
        System.out.println();
        Day dayOfBirthAsEnum = Day.getNumberOfDayReturnReturnAsEnum(dayOhBirth);
        Month monthOfBirthAsEnum = Month.getNumberOfMonthAndReturnAsEnam(monthOfBirth);

        Horoscope horoscope = new Horoscope();
        Zodiac yourSign = horoscope.getZodiacSign(dayOfBirthAsEnum, monthOfBirthAsEnum);
        System.out.println(yourSign + ": " + yourSign.getHoroscopeText());
        System.out.println();

        //Lower method's from outer Class'll be demonstrated.
        System.out.println(ManAstrologer.getThePlanetOfLiving());
        //The first object'll be initialized with logical blocks.
        ManAstrologer manAstrologer = new ManAstrologer();
        System.out.println(manAstrologer.getName());
        System.out.println(manAstrologer.getSalary());
        System.out.println();
        //The second - with the help of Constructor.
        ManAstrologer manAstrologer1 = new ManAstrologer("Den", 700.25);
        manAstrologer1.printAboutAstrologer();
        System.out.println();

        //Lower method's from inner Class'll be demonstrated.
        //The first object'll be initialized with logical blocks.
        ManAstrologer.AstrologerEmployeeOfPlanetarium astrologerEmployeeOfPlanetarium =
                new ManAstrologer().new AstrologerEmployeeOfPlanetarium();
        System.out.println(astrologerEmployeeOfPlanetarium.getCityOfLife());
        System.out.println(astrologerEmployeeOfPlanetarium.getNameOfPlanetarium());
        System.out.println(astrologerEmployeeOfPlanetarium.getAge());
        System.out.println();
        //The second - with the help of Constructor.
        ManAstrologer.AstrologerEmployeeOfPlanetarium astrologerEmployeeOfPlanetarium1 =
                new ManAstrologer().new AstrologerEmployeeOfPlanetarium("Budapest", "Moon",
                        55);
        astrologerEmployeeOfPlanetarium1.printAboutEmployeeOfPlanetariumFromInnerClass();

    }
}
