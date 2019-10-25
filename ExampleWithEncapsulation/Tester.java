package com.company;

public class Tester {
    public String name;
    public String surname;
    protected int experienceInYears;
    String englishLevel;
    private double salary;

    Tester(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    protected Tester(String name, String surname, int experienceInYears) {
        this(name, surname);
        this.experienceInYears = experienceInYears;
    }

    public Tester(String name, String surname, int experienceInYears, String englishLevel, double salary) {
        this(name, surname, experienceInYears);
        this.englishLevel = englishLevel;
        this.salary = salary;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    String getSurname() {
        return surname;
    }

    void setExperienceInYears(int experienceInYears) {
        this.experienceInYears = experienceInYears;
    }

    int getExperienceInYears() {
        return experienceInYears;
    }

    void setEnglishLevel(String englishLevel) {
        this.englishLevel = englishLevel;
    }

    String getEnglishLevel() {
        return englishLevel;
    }

    void setSalary(double salary) {
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }

    private double getSalaryMultTwo() {
        return this.salary * 2;
    }

    int getExperienceInMonths() {
        int monthsInTheYear = 12;
        return this.experienceInYears * monthsInTheYear;
    }

    protected void printNameAndSurname() {
        String emptyString = " ";
        System.out.println("Person's name and surname: " + name + emptyString + surname);
    }

    public void printAllInformation() {
        String Str1 = String.format("The information about the person: %s %s, experience is %s years, english level is %s, salary is %s",
                name, surname, experienceInYears, englishLevel, salary);
        System.out.println(Str1);
    }
}
