package by.trjava.console.task06.util;

import by.trjava.console.exceptions.NegativeValueException;

import java.util.Calendar;
import java.util.Date;

public class DeadLineCalculator {

    public static int getDaysAsDigit(String days) throws NegativeValueException, NumberFormatException {
        int numberOfDays;
        numberOfDays = Integer.parseInt(days);
        if (numberOfDays < 0) {
            throw new NegativeValueException("Exception in the Class " +
                    "by.trjava.console.task06.util.DeadLineCalculator, method getDaysAsDigit. You've entered" +
                    "negative value of days!");
        }
        return numberOfDays;
    }

    public static String getDeadLine(String days) throws NegativeValueException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, getDaysAsDigit(days));
        Date dateDeadline = calendar.getTime();
        return dateDeadline.toString();
    }

    public static String getPlan(String surname, String days) throws NegativeValueException {
        int daysAsDigit = getDaysAsDigit(days);
        String message;
        message = String.format("%s is ready to perform the task for %d days.\nDeadline is : %s",
                surname, daysAsDigit, getDeadLine(days));
        return message;
    }
}
