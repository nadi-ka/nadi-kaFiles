package com.company;

public class ManAstrologer {
    private static String thePlanetOfLife;
    private String name;
    private double salary;

    static {
        thePlanetOfLife = "Earth";
    }

    {
        name = "David";
    }

    {
        salary = 500.50;
    }

    public ManAstrologer(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    public ManAstrologer(){

    }

    public static String getThePlanetOfLiving() {
        return thePlanetOfLife;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void printAboutAstrologer() {
        System.out.println("I'm the astrologer from the outer Class. I live on the " + getThePlanetOfLiving() +
                ". My name is " + getName() + ". My salary is " + getSalary());
    }

    public class AstrologerEmployeeOfPlanetarium {
        private String cityOfLife;
        private String nameOfPlanetarium;
        private int age;

        {
            cityOfLife = "Athens";
        }

        {
            nameOfPlanetarium = "Mercury";
        }

        {
            age = 30;
        }
        public AstrologerEmployeeOfPlanetarium(){

        }

        public AstrologerEmployeeOfPlanetarium(String cityOfLife, String nameOfPlanetarium, int age) {
            this.cityOfLife = cityOfLife;
            this.nameOfPlanetarium = nameOfPlanetarium;
            this.age = age;
        }

        public String getCityOfLife() {
            return cityOfLife;
        }

        public String getNameOfPlanetarium() {
            return nameOfPlanetarium;
        }

        public int getAge() {
            return age;
        }

        public void printAboutEmployeeOfPlanetariumFromInnerClass() {
            System.out.println("I'm employee. My city is " + getCityOfLife() + ". I work in the planetarium, named " +
                    getNameOfPlanetarium() + ". My age is " + getAge() + " My employer is the astrologer from the" +
                    "outer Class. His name is " + getName() + ". As I've heard his salary is " + getSalary());
        }

    }

}
