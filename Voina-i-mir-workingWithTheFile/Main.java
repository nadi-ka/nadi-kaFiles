package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static final String TEXT_PATH = "D:\\VoinaIMir\\tolstoi_l_n__voina_i_mir.txt";

    public static void main(String[] args) {

        ArrayList<Character> textFromBytes = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(TEXT_PATH)) {
            while (fileInputStream.available() > 0) {
                textFromBytes.add((char) fileInputStream.read());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ArrayList<String> text = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(TEXT_PATH);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                for (String word : line.split("[^\\p{L}\\p{Digit}]+")) {
                    text.add(word);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        ArrayList <String> arrayListResult = new ArrayList();

        //Creating of the HashMap, where a world'll be a key, frequency - value.
        //Looking for the most and the least frequent word.

        TreeMap<String, Integer> wordAndItsFrequency = new TreeMap<>();
        for (String word : text) {
            if (wordAndItsFrequency.containsKey(word)) {
                wordAndItsFrequency.put(word, wordAndItsFrequency.get(word) + 1);
            } else {
                wordAndItsFrequency.put(word, 1);
            }
        }
        int maxValueInMap = (Collections.max(wordAndItsFrequency.values()));
        for (HashMap.Entry<String, Integer> entry : wordAndItsFrequency.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                System.out.println("The most often word is: " + entry.getKey());
                arrayListResult.add("The most often word is: "+ entry.getKey());
            }
        }
        //we'll expect to get a list of words, which occur only one time in text.
        int minValueInMap = (Collections.min(wordAndItsFrequency.values()));
        for (HashMap.Entry<String, Integer> entry : wordAndItsFrequency.entrySet()) {
            if (entry.getValue() == minValueInMap) {
                System.out.println("The least often word is: " + entry.getKey());
                arrayListResult.add("The least often word is: "+ entry.getKey());
            }
        }

        String[] textArray = text.stream().toArray(String[]::new);
        // The Longest word.
        String isTheLongest = " ";
        for (int i = 0; i < textArray.length; i++) {
            if (textArray[i].length() >= isTheLongest.length()) {
                isTheLongest = textArray[i];
            }
        }
        System.out.println("The longestWord is: " + isTheLongest + ", It's size is: " + isTheLongest.length());
        arrayListResult.add("The longest word is: " + isTheLongest);

        //Getting of all unique years from the text.
        boolean hasYear = false;
        int matches = 0;
        LinkedHashSet<String> years = new LinkedHashSet<String>();
        for (String s : textArray) {
            if (s.matches("^[0-9]{4}$")) {
                hasYear = true;
                matches++;
                years.add(s + " ");
            }
        }
        System.out.println("Text hasYear: " + hasYear);
        System.out.println("totally amount of years in text(including repeated): " + matches);
        System.out.println("unique list of years: " + years.toString());
        arrayListResult.add("unique list of years: " + years.toString());
        for (String element:arrayListResult){
            System.out.println(element);
        }


        try(FileWriter fileWriter = new FileWriter("D:\\VoinaIMir\\outputVoina_I_Mir.txt")) {
            for (String str : arrayListResult) {
                fileWriter.write(str);
            }
            fileWriter.flush();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }

}
