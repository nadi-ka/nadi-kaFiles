package by.trjava.console.task01.util;

public class Hello {

    public static String sayHello(String name) {
        String resultString;
        if (name == null) {
            resultString = "Hello, user!";
        } else {
            resultString = "Hello, " + name;
        }
        return resultString;
    }
}
