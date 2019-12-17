package by.trjava.task03.dataaccess;

import by.trjava.exceptions.InputFromFileImpossibleException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataAccessText {
    public static final String FILE_PASS = "D:\\Workspace\\StringText\\text.txt";

    public static String readTextFromFile() throws InputFromFileImpossibleException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        File file = new File(FILE_PASS);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
            throw new InputFromFileImpossibleException("by.trjava.task03.dataaccess.DataAccessText, " +
                    "readTextFromFile(). Reading from file is impossible! " + FILE_PASS);
        }
        return stringBuilder.toString();
    }
}
