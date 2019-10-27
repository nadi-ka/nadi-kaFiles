package com.company;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Dinosaurs dinosaur1 = new Dinosaurs("ankilozaurus", 300, 7);
        Dinosaurs dinosaur2 = new Dinosaurs("triceratops", 150, 5);
        Dinosaurs dinosaur3 = new Dinosaurs("pterodondon", 100, 2);
        Dinosaurs dinosaur4 = new Dinosaurs("stegosaurus", 1000, 6);

        PriorityQueue<Dinosaurs> dinosaurs = new PriorityQueue<Dinosaurs>();

        dinosaurs.add(dinosaur1);
        dinosaurs.add(dinosaur2);
        dinosaurs.add(dinosaur3);
        dinosaurs.add(dinosaur4);
        for (Object dinosaur: dinosaurs){
            System.out.println(dinosaur);
        }
        System.out.println();
        /* Здесь использован метод compareTo, который реализует интерфейс Comparable, часто называемый "естественным
        сравнением" ("natural comparison method") - т.е. методом по умолчанию. Основные типы (например, Integer,
        String, Float) уже имеют свои методы compareTo. Выводим из очереди динозавров в порядке возрастания веса.
         */
        while (! dinosaurs.isEmpty()){
            System.out.println(dinosaurs.remove().getWeightInKilogramms());
        }
        System.out.println(dinosaurs.isEmpty());
        System.out.println();

        TreeMap <Integer, Dinosaurs> dino = new TreeMap<>();
        dino.put(dinosaur1.getGrowth(), dinosaur1);
        dino.put(dinosaur2.getGrowth(), dinosaur2);
        dino.put(dinosaur3.getGrowth(), dinosaur3);
        dino.put(dinosaur4.getGrowth(), dinosaur4);
        for (Integer key: dino.keySet()){
            System.out.println(key);
        }
        dino.values().stream().forEach(element -> System.out.println(element));
        System.out.println();
        //The key is the dino's growth, by which they are sorted.
        dino.remove(2);
        System.out.println(dino);
        dino.remove(5);
        System.out.println(dino);
        dino.remove(6);
        System.out.println(dino);
        dino.remove(7);
        System.out.println(dino.isEmpty());
        System.out.println();

        PriorityQueue<Dinosaurs> dinosaursByGrowth = new PriorityQueue<Dinosaurs>(4,
                new NewComparatorForDinosaurs());
        dinosaursByGrowth.add(dinosaur1);
        dinosaursByGrowth.add(dinosaur2);
        dinosaursByGrowth.add(dinosaur3);
        dinosaursByGrowth.add(dinosaur4);
        for (Dinosaurs dinosaur: dinosaursByGrowth){
            System.out.println(dinosaur);
        }
        /*здесь используем сортировку через интерфейс Comparator. Для этой цели создаём отдельный класс, который
        реализует интерфейс Comparator. Например, у нас уже есть класс Dinosaurs. Создадим отдельный класс,
        который будут выполнять функцию сравнения - NewComparatorForDinosaurs. Выводим динозавров из очереди по
        возрастанию роста.
        */
        while (! dinosaursByGrowth.isEmpty()){
            System.out.println(dinosaursByGrowth.remove().getGrowth());
        }
        System.out.println(dinosaursByGrowth.isEmpty());

    }
}
