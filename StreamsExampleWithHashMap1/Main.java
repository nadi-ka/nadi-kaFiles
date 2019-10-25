package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Here enter the text, please.");
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String[] text = scanner.nextLine().toLowerCase().split("[^\\p{L}\\p{Digit}]+");
        for (String element : text) {
            System.out.print(element + " ");
        }
        System.out.println();
        //Creating of the HashMap, where a world'll be a key, frequency - value.

        HashMap<String, Integer> wordAndItsFrequency = new HashMap<>();
        for (String word : text) {
            if (wordAndItsFrequency.containsKey(word)) {
                wordAndItsFrequency.put(word, wordAndItsFrequency.get(word) + 1);
            } else {
                wordAndItsFrequency.put(word, 1);
            }
        }
        System.out.println(wordAndItsFrequency.toString());
        System.out.println();

        wordAndItsFrequency.entrySet().stream().sorted(descendingFrequencyWordsOrder()).limit(10).
                map(Map.Entry::getKey).forEach(System.out::println);
    }

        public static Comparator<Map.Entry<String, Integer>> descendingFrequencyWordsOrder(){
            return Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry:: getValue).reversed().
                    thenComparing(Map.Entry::getKey);
        }




}
