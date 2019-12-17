package by.trjava.task03.util.service;

import by.trjava.exceptions.NullValueException;

public class TextService {

    public static String changeEveryKthLetterToSymbol(String text, int indexK, String symbol) throws NullValueException {
        if (text == null) {
            throw new NullValueException("Reference to the text shouldn't be equals null!");
        } else if (indexK <= 0 || symbol == null) {
            return text;
        }
            StringBuilder resultText = new StringBuilder();
            int indexStart = 1;
            int indexFinish;
            String word;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ' || text.charAt(i) == '.' || text.charAt(i) == ',' ||
                        text.charAt(i) == '!' || text.charAt(i) == '?' || text.charAt(i) == '\n' ||
                        text.charAt(i) == ';' || text.charAt(i) == ':' || text.charAt(i) == '-' ||
                        text.charAt(i) == '\t') {
                    indexFinish = i;
                    word = text.substring(indexStart, indexFinish);
                    if (word.length() >= indexK) {
                        resultText.append(word.substring(0, indexK - 1)).append(symbol).
                                append(word.substring(indexK));
                    } else {
                        resultText.append(word);
                    }
                    resultText.append(text.charAt(i));
                    if (i != text.length() - 1) {
                        indexStart = i + 1;
                    }
                }
            }
            return resultText.toString();
    }

    public static String changeEveryKthLetterToSymbolPattern(String text, int indexK, String symbol)
            throws NullValueException {
        if (text == null) {
            throw new NullValueException("Reference to the text shouldn't be equals null!");
        } else if (indexK <= 0 || symbol == null) {
            return text;
        }
            String resultText;
            resultText = text.replaceAll(String.format("(\\b\\p{L}{%d})\\p{L}", indexK - 1), "$1" + symbol);
            return resultText;
    }

    public static String changeAtoOAfterLetterR(String text) throws NullValueException{
        if (text == null) {
            throw new NullValueException("Reference to the text shouldn't be equals null!");
        }
        StringBuilder resultText = new StringBuilder();
        for (int i = 0; i < text.length() - 1; i++) {
            if ((text.charAt(i) == 'р' || text.charAt(i) == 'Р') && text.charAt(i + 1) == 'а') {
                resultText.append(text.charAt(i)).append('о');
                i++;
            }
            else {
                resultText.append(text.charAt(i));
            }
        }
        resultText.append(text.charAt(text.length()-1));
        return resultText.toString();
    }

    public static String changeAtoOAfterLetterRPattern(String text) throws NullValueException{
        if (text == null) {
            throw new NullValueException("Reference to the text shouldn't be equals null!");
        }
        String resultText;
        resultText = text.replaceAll("ра", "ро");
        resultText = resultText.replaceAll("Ра", "Ро");
        return resultText;
    }

    public static String changeWordsOfGivenLengthToSubstring(String text, int length, String substring)
    throws NullValueException{
        if (text == null) {
            throw  new NullValueException("Reference to the text shouldn't be equals null!");
        }
        else if (substring == null || substring.length() == 0 || length <= 0) {
            return text;
        }
        StringBuilder resultText = new StringBuilder();
        int indexStart = 1;
        int indexFinish;
        String word;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ' || text.charAt(i) == '.' || text.charAt(i) == ',' ||
                    text.charAt(i) == '!' || text.charAt(i) == '?' || text.charAt(i) == '\n' ||
                    text.charAt(i) == ';' || text.charAt(i) == ':' || text.charAt(i) == '-' ||
                    text.charAt(i) == '\t') {
                indexFinish = i;
                word = text.substring(indexStart, indexFinish);
                if (word.length() == length) {
                    resultText.append(substring);
                } else {
                    resultText.append(word);
                }
                resultText.append(text.charAt(i));
                if (i != text.length() - 1) {
                    indexStart = i + 1;
                }
            }
        }
        return resultText.toString();
    }

    public static String changeWordsOfGivenLengthToSubstringPattern(String text, int length, String substring)
            throws NullValueException {
        if (text == null) {
            throw  new NullValueException("Reference to the text shouldn't be equals null!");
        }
        else if (substring == null || substring.length() == 0 || length <= 0) {
            return text;
        }
        String resultString;
        resultString = text.replaceAll(String.format("\\b\\p{L}{%d}\\b", length), substring);
        return resultString;
    }

    public static String removeAllSymbolsExceptSpaceStrings(String text)throws NullValueException {
        if (text == null) {
            throw  new NullValueException("Reference to the text shouldn't be equals null!");
        }
        String resultString = "";
        char [] textChar = text.toCharArray();
        for (int i = 0; i < textChar.length; i++) {
            if (Character.isLetter(textChar[i]) || textChar[i] == ' ') {
                resultString += textChar[i];
            }
        }
        return resultString;
    }

    public static String removeAllSymbolsExceptSpaceStringsPattern(String text)throws NullValueException {
        if (text == null) {
            throw new NullValueException("Reference to the text shouldn't be equals null!");
        }
        String resultString;
        resultString = text.replaceAll("(?u)[^\\pL ]","");
        return resultString;
    }

    //Method will use for checking if the letter is consonant or vowel in the other method;
    public static boolean isVowel(char letter) {
        letter = Character.toLowerCase(letter);
        switch (letter) {
            case 'а':
            case 'е':
            case 'ё':
            case 'и':
            case 'о':
            case 'у':
            case 'ы':
            case 'э':
            case 'ю':
            case 'я':
                return true;
            default:
                return false;
        }
    }

    public static String removeWordsOfGivenLengthFromConsonants(String text, int length) throws
            NullValueException{
        if (text == null) {
            throw new NullValueException("Reference to the text shouldn't be equals null!");
        } else if (length <= 0) {
            return text;
        }
        StringBuilder resultText = new StringBuilder();
        int indexStart = 1;
        int indexFinish;
        String word;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ' || text.charAt(i) == '.' || text.charAt(i) == ',' ||
                    text.charAt(i) == '!' || text.charAt(i) == '?' || text.charAt(i) == '\n' ||
                    text.charAt(i) == ';' || text.charAt(i) == ':' || text.charAt(i) == '-' ||
                    text.charAt(i) == '\t') {
                indexFinish = i;
                word = text.substring(indexStart, indexFinish);
                if (word.length() == length) {
                    if (!isVowel(word.charAt(0))) {
                        resultText.append("");
                    }
                    else {
                        resultText.append(word);
                    }
                } else {
                    resultText.append(word);
                }
                resultText.append(text.charAt(i));
                if (i != text.length() - 1) {
                    indexStart = i + 1;
                }
            }
        }
        return resultText.toString();
    }

    public static String removeWordsOfGivenLengthFromConsonantsPattern(String text, int length) throws
            NullValueException {
        if (text == null) {
            throw new NullValueException("Reference to the text shouldn't be equals null!");
        } else if (length <= 0) {
            return text;
        }
        String resultString;
        resultString = text.replaceAll
                (String.format("[БВГДЖЗЙКЛМНПРСТФХЦЧШЩЪЬбвгджзклмнпрстфхцчшщъь]{1}[а-яА-ЯёЁ]{%d}", length -1),
                        "");
        return resultString;
    }
}
