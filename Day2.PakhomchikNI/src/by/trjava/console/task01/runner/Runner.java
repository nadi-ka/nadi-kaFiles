package by.trjava.console.task01.runner;

import by.trjava.console.task01.util.Hello;

public class Runner {

    public static void main(String[] args) {
        String name = args[0];
        String hello = Hello.sayHello(name);
        System.out.println(hello);
    }
}
