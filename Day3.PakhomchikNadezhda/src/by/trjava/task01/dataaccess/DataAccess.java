package by.trjava.task01.dataaccess;

import by.trjava.exceptions.InputFromFileImpossibleException;

import java.io.*;

public class DataAccess {

    // It's all good, but just as a tip: you would usually consider either
    // asking a user to provide a file path or setting it in some kind of config file.
    // Usually, you would avoid hard coding (e.g. I cannot start it on my linux machine
    // since I have no disk D).
    public final static String FILE_PASS = "D:\\Workspace\\Array\\IntArr.txt";

    public static int[] readTextFromFile() throws InputFromFileImpossibleException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        File file = new File(FILE_PASS);
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
            throw new InputFromFileImpossibleException("by.trjava.task01.dataaccess.DataAccess, readTextFromFile()" +
                    "Reading from file is impossible! " + FILE_PASS);
        }
        return convertTextFromFileToArrayInt(stringBuilder);
    }

    public static int[] convertTextFromFileToArrayInt(StringBuilder stringBuilder) throws NumberFormatException {
        String[] text = stringBuilder.toString().split(" ");
        int[] resultArrayInt = new int[text.length];
        for (int i = 0; i < text.length; i++) {
            int element = Integer.parseInt(text[i]);
            resultArrayInt[i] = element;
        }
        return resultArrayInt;
    }

}
