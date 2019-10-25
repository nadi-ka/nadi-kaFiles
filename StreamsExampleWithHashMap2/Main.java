package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        HashMap<Integer, String> mapIdName = new HashMap<>();
        mapIdName.put(1, "David");
        mapIdName.put(2, "Andrey");
        mapIdName.put(3, "Olga");
        mapIdName.put(4, "Egor");
        mapIdName.put(5, "Anna");
        mapIdName.put(6, "Konstantin");
        mapIdName.put(7, "Oleg");
        mapIdName.put(8, "Sergey");
        mapIdName.put(9, "John");
        mapIdName.put(10, "Yurij");
        mapIdName.put(11, "Maria");
        mapIdName.put(12, "Angela");
        mapIdName.put(13, "Georg");

        DataOfClient dataOfClient = new DataOfClient();
        dataOfClient.setMapData(mapIdName);
        System.out.println(dataOfClient.getMapData());
        System.out.println();

        Stream<Map.Entry<Integer, String>> streamClientData = mapIdName.entrySet().stream();
        streamClientData.filter(d -> d.getKey() == 1 || d.getKey() == 2 || d.getKey() == 5 ||
                d.getKey() == 8 || d.getKey() == 9 || d.getKey() == 13).
                filter(d -> d.getValue().length() % 2 != 0).
                map(d -> d.getValue()).
                map(word -> new StringBuilder(word).reverse()).
                collect(Collectors.toList()).
                forEach(System.out::println);


    }
}
