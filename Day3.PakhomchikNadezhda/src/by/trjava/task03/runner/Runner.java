package by.trjava.task03.runner;

import by.trjava.exceptions.InputFromFileImpossibleException;
import by.trjava.exceptions.NullValueException;
import by.trjava.scanner.DataScanner;
import by.trjava.task03.dataaccess.DataAccessText;
import by.trjava.task03.util.entity.TextStorage;
import by.trjava.task03.util.service.TextService;

public class Runner {

    public static void main(String[] args) {
        //Reading text from console;
        String text1 = DataScanner.readTextFromConsole();
        TextStorage textStorage = new TextStorage(text1);
        System.out.println(textStorage);
        //Reading text from file;
        TextStorage text2 = null;
        try {
            text2 = new TextStorage(DataAccessText.readTextFromFile());
            System.out.println(text2);
        } catch (InputFromFileImpossibleException ex) {
            ex.printStackTrace();
        }

        //Replacement every K-th letter in words to symbol;
        String symbol = DataScanner.readTextFromConsole();
        int indexK = DataScanner.readIntegerFromConsole();
        TextStorage resultText1 = null;
        try {
            resultText1 = TextService.changeEveryKthLetterToSymbol(text2, indexK, symbol);
            System.out.println(resultText1);
        }
        catch (NullValueException ex) {
            ex.printStackTrace();
        }
        //Replacement every K-th letter in words to symbol, using regular expression;
        String resultText2 = null;
        try {
            resultText2 = TextService.changeEveryKthLetterToSymbolPattern(text2.getText(), indexK, symbol);
            System.out.println(resultText2);
        }
        catch (NullValueException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        //Replacement of 'a' to 'o' after 'р', if it isn't the last letter in the word. And the same, using
        //regular expression;
        String resultText3 = null;
        String resultText4 = null;
        try {
            resultText3 = TextService.changeAtoOAfterLetterR(text2.getText());
            System.out.println(resultText3);
            resultText4 = TextService.changeAtoOAfterLetterRPattern(text2.getText());
            System.out.println(resultText4);
        }
        catch (NullValueException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        //Replacement of words of given length to given substring. And the same, using regular expression;
        String resultString5 = null;
        String resultString6 = null;
        int wordLength = DataScanner.readIntegerFromConsole();
        String substring = DataScanner.readTextFromConsole();
        try {
            resultString5 = TextService.changeWordsOfGivenLengthToSubstring(text2.getText(), wordLength, substring);
            System.out.println(resultString5);
            resultString6 = TextService.changeWordsOfGivenLengthToSubstringPattern(text2.getText(), wordLength, substring);
            System.out.println(resultString6);
        }
        catch (NullValueException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        //Removing of all not letter or spaceString symbols from the text. And the same, using regular expression;
        String resultString7 = null;
        String resultString8 = null;
        try {
            resultString7 = TextService.removeAllSymbolsExceptSpaceStrings(text2.getText());
            System.out.println(resultString7);
            resultString8 = TextService.removeAllSymbolsExceptSpaceStringsPattern(text2.getText());
            System.out.println(resultString8);
        } catch (NullValueException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        //Removing of words from text with given length, beginning from consonant.
        String resultString9 = null;
        String resultString10 = null;
        int wordLength2 = DataScanner.readIntegerFromConsole();
        try {
            resultString9 = TextService.removeWordsOfGivenLengthFromConsonants(text2.getText(), wordLength2);
            System.out.println(resultString9);
            resultString10 = TextService.removeWordsOfGivenLengthFromConsonantsPattern(text2.getText(), wordLength2);
            System.out.println(resultString10);
        }
        catch (NullValueException ex) {
            ex.printStackTrace();
        }
    }

}
