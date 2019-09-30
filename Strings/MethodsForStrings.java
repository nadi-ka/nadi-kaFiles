package com.company;

import java.util.Arrays;

public abstract class MethodsForStrings {
    final static String spaceString = " ";

    public static String getStringWithChangedLetterToSymbol(String anyText, int isPositionOfChange,
                                                            char isSymbolForChange) {
        String[] wordsArray = anyText.split(" ");
        for (int i = 0; i < wordsArray.length; i++) {
            if (wordsArray[i].length() >= isPositionOfChange) {
                wordsArray[i] = wordsArray[i].substring(0, isPositionOfChange - 1) + isSymbolForChange +
                        wordsArray[i].substring(isPositionOfChange);
            }

        }
        return String.join(" ", wordsArray);
    }

    public static void printStringInRussianAndPositionOfLettersInAlphabet(String anyText) {
        String doubleSpaceString = "  ";
        String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        char[] anyTextToCharArray = anyText.replaceAll("[^а-яА-ЯёЁ]", "").
                toLowerCase().toCharArray();
        for (int i = 0; i < anyTextToCharArray.length; i++) {
            System.out.print(anyTextToCharArray[i] + doubleSpaceString);
        }
        System.out.println();
        for (char symbol : anyTextToCharArray) {
            int i = russianAlphabet.lastIndexOf(symbol);
            i = i + 1;
            System.out.print(i + spaceString);
        }
        System.out.println();
    }

    public static String getStringWithReplacedAToOAfterR(String anyString) {
        String resultString = anyString.replaceAll("ра", "ро");
        String resultString1 = resultString.replaceAll("Ра", "Ро");
        return resultString1;
    }

    public static String getStringWithReplacementOfWordsWhichLengthAre5ToSubstring(String anyString,
                                                                                   String substringForChanging) {
        String resultString = anyString.replaceAll("\\b[a-zA-Z]{5}\\b", substringForChanging);
        return resultString;
    }

    public static String getStringWithSubstringPasteAfterSymbolK(String anyString, int isPositionToPasteAfter,
                                                                 String isSubstringToPaste) {
        StringBuffer stringBuffer = new StringBuffer(anyString);
        stringBuffer.insert(isPositionToPasteAfter, isSubstringToPaste);
        return stringBuffer.toString();
    }

    public static String getStringWhereSubstringWillBeInsertedAfterEveryWordWhichEndInGivenSubstring1
            (String anyString, String isSubstringForInsertion) {
        String resultString = anyString.replaceAll("(\\w+is\\b)", "$1 " + isSubstringForInsertion);
        return resultString;
    }

    public static String getStringOrWithRemovedSymbolsKOrWithReplacedSymbolsK(String anyString) {
        boolean hasWordThisSymbol;
        String[] words = anyString.split(" ");
        for (int i = 0; i < words.length; i++) {
            hasWordThisSymbol = (words[i].contains(".")) ? true : false;
            if (hasWordThisSymbol) {
                words[i] = words[i].charAt(words[i].length() - 1) == '.' ? words[i].replace(words[i].substring
                        (words[i].length() - 1), "") : words[i].replace(".", "*");
            }
        }
        return String.join(" ", words);
    }

    public static String getTextWithoutPunctuation(String anyText) {
        String resultString = anyText.replaceAll("[^\\pL]", " ");
        return resultString;
    }

    public static String getTextWithoutWordsWhichBeginFromConsonantAndHaveGivenLength(String anyString,
                                                                                      int lengthOfWordToDelete) {
        String consonants = "BbCcDdFfGgHhJjKkLlMmNnPpQqRrSsTtVvWwXxZz";
        String[] words = anyString.split(" ");
        StringBuffer stringBuffer = new StringBuffer();
        int lengthOfTheWord;
        for (int i = 0; i < words.length; i++) {
            if (words[i].charAt(words[i].length() - 1) == '.' || words[i].charAt(words[i].length() - 1) == ',' ||
                    words[i].charAt(words[i].length() - 1) == '!') {
                lengthOfTheWord = words[i].length() - 1;
            } else {
                lengthOfTheWord = words[i].length();
            }
            if (lengthOfTheWord == lengthOfWordToDelete) {
                if (consonants.contains(words[i].substring(0, 1))) {
                    continue;
                } else {
                    stringBuffer = stringBuffer.append(words[i] + " ");
                }
            } else {
                stringBuffer = stringBuffer.append(words[i] + " ");
            }
        }
        return stringBuffer.toString().trim();
    }

    public static String getStringWithDeletedSubstringBetweenSigns(String anyText, String specialSign1,
                                                                   String specialSign2) {
        String resultString;
        if (anyText.contains(specialSign1) && anyText.contains(specialSign2)) {
            String subString = anyText.substring(anyText.indexOf(specialSign1) + 1, anyText.lastIndexOf(specialSign2));
            resultString = anyText.replaceAll(subString, "");
        } else {
            return anyText;
        }
        return resultString;
    }
}
