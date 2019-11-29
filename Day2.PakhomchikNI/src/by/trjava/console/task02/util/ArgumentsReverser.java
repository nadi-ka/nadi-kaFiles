package by.trjava.console.task02.util;

public class ArgumentsReverser {

    public static String[] getArgumentsReversed(String[] args) {
        String tmp;
        if (args == null) {
            throw new NullPointerException("You haven't entered any arguments!");
        } else {
        }
        for (int i = 0; i < args.length / 2; i++) {
            tmp = args[i];
            args[i] = args[args.length - 1 - i];
            args[args.length - 1 - i] = tmp;
        }
        return args;
    }
}
