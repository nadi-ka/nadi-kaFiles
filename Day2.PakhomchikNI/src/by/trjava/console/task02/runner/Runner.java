package by.trjava.console.task02.runner;

import by.trjava.console.task02.util.ArgumentsReverser;

public class Runner {

    public static void main(String[] args) {
        String[] argumentsReversed;
        argumentsReversed = ArgumentsReverser.getArgumentsReversed(args);
        System.out.println("Command line's arguments in reversed order are: ");
        for (int i = 0; i < argumentsReversed.length; i++) {
            System.out.print(argumentsReversed[i] + " ");
        }
    }
}
