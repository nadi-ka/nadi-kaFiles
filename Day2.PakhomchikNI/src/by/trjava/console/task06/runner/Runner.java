package by.trjava.console.task06.runner;

import by.trjava.console.exceptions.NegativeValueException;
import by.trjava.console.task06.util.DeadLineCalculator;

public class Runner {

    public static void main(String[] args) {
        String surname = args[0];
        String days = args[1];
        String deadLineForPerson;
        try {
            deadLineForPerson = DeadLineCalculator.getPlan(surname, days);
            System.out.println(deadLineForPerson);
        } catch (NegativeValueException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
