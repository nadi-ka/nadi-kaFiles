package com.company;

public class ExamplesPerson {
    public static void main(String[] args) {
        Tester tester1 = new Tester("Alexey", "Sidorov");
        tester1.printAllInformation();
        tester1.setName("Nikolay");
        tester1.setSurname("Petrov");
        tester1.setExperienceInYears(5);
        tester1.setEnglishLevel("upper intermidiate");
        tester1.setSalary(2500.50);
        System.out.println(tester1.getName());
        System.out.println(tester1.getSurname());
        System.out.println(tester1.getExperienceInYears());
        System.out.println(tester1.getEnglishLevel());
        System.out.println(tester1.getSalary());
        System.out.println(tester1.getExperienceInMonths());
        tester1.printNameAndSurname();
        tester1.printAllInformation();
        System.out.println(tester1.getSalaryMultTwo());
        //Privat Method cannot be called from the other Java Class

    }

}
