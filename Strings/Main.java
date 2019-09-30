package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String text = "Hello world, this is the text. For training methods for working with Marry, Artur STRINGS!";
        String textInRussian = "Мама мыла раму.";
        String textWithMistakes = "Рамашки расли в корабке.";
        String textToPasteSubstringAfterWordsWhichEndInIs = "This is the rockets for playing tennis.";
        String textWithManyPoints = "Here. Will be. T.he text. with m.a.ny points..";
        String textWithDifferentSigns = "John (Smidt-Connor) is &walking& *round the park*. The weather @is@ " +
                "%wonderful%, the sun $is shining$ brightly!";
        String substringForReplacement = "HEY!";
        int isPositionOfChange = 3;
        char isSymbolForChange = '%';

        //Lower we replace symbol k in every word longer, then k, to symbol '%';
        System.out.println(text);
        System.out.println(MethodsForStrings.getStringWithChangedLetterToSymbol(text, isPositionOfChange,
                isSymbolForChange));
        System.out.println();

        //Lower we print text in russian and lover positions of letters in alphabet;
        MethodsForStrings.printStringInRussianAndPositionOfLettersInAlphabet(textInRussian);
        System.out.println();

        //Lower we correct mistakes in the text (replace "pa" to "po");
        System.out.println(textWithMistakes);
        System.out.println(MethodsForStrings.getStringWithReplacedAToOAfterR(textWithMistakes));
        System.out.println();

        //Lower we replace all words with length=5 to substring "HEY!";
        System.out.println(text);
        System.out.println(MethodsForStrings.getStringWithReplacementOfWordsWhichLengthAre5ToSubstring(text,
                substringForReplacement));
        System.out.println();

        //Lower we paste substring "HEY!" after symbol k in our string;
        System.out.println(textInRussian);
        System.out.println(MethodsForStrings.getStringWithSubstringPasteAfterSymbolK(textInRussian,
                isPositionOfChange, substringForReplacement));
        System.out.println();

        //Lower we paste substring "HEY!" after every word, which ends at "ва";
        System.out.println(textToPasteSubstringAfterWordsWhichEndInIs);
        System.out.println(MethodsForStrings.
                getStringWhereSubstringWillBeInsertedAfterEveryWordWhichEndInGivenSubstring1
                        (textToPasteSubstringAfterWordsWhichEndInIs, substringForReplacement));
        System.out.println();

        //Lower we are looking for "." in the middle or at the end oh words and remove them (if at the end)
        //or replace to "*" (if in the middle);
        System.out.println(textWithManyPoints);
        System.out.println(MethodsForStrings.getStringOrWithRemovedSymbolsKOrWithReplacedSymbolsK(textWithManyPoints));
        System.out.println();

        //Lower we are looking for words with given length beginning from consonants and delete them;
        System.out.println(text);
        System.out.println(MethodsForStrings.getTextWithoutWordsWhichBeginFromConsonantAndHaveGivenLength
                (text, 5));
        System.out.println();

        //Lower we delete punctuation, except spaces, from the text;
        System.out.println(text);
        System.out.println(MethodsForStrings.getTextWithoutPunctuation(text));
        System.out.println();

        //Lower we delete substring between signs (get them from console) from the text;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter any sign (not a letter).");
        String isInputedSign1 = scanner.next();
        System.out.println("Please, enter the second one.");
        String isInputedSign2 = scanner.next();
        System.out.println(textWithDifferentSigns);
        System.out.println(MethodsForStrings.getStringWithDeletedSubstringBetweenSigns(textWithDifferentSigns,
                isInputedSign1, isInputedSign2));


    }
}
