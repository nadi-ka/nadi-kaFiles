package com.company;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Person person = new Person();
        Class c = person.getClass();
        Field[] fields = c.getFields();
        for (Field field : fields) {
            System.out.println(field.getName() + " " + field.get(person));

        }
        Field[] declaredFields = c.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            System.out.println(field.getName() + " " + field.get(person));

        }

        Field f = person.getClass().getDeclaredField("age");
        f.setAccessible(true);
        f.set(person, 25);
        System.out.println(person);

        f = person.getClass().getDeclaredField("count");
        f.setAccessible(true);
        f.set(person, 100);
        System.out.println(person);

        f = person.getClass().getDeclaredField("lesson");
        f.setAccessible(true);
        f.set(person, "lesson19");
        System.out.println(person);

        f = person.getClass().getField("name");
        f.set(person, "Andrey");
        System.out.println(person);
    }
}
