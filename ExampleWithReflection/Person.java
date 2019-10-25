package com.company;

public class Person {
    private int age = 20;
    String lesson = "lesson 18";
    protected int count = 25;
    public String name = "Alex";

    @Override
    public String toString(){
        return ("person's data: " + age + " " + name + " " + count + " " + lesson);

    }

}
