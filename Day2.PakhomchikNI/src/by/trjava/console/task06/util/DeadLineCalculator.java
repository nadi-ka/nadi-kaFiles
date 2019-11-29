package by.trjava.console.task06.util;

import java.util.Calendar;
import java.util.Date;

public class DeadLineCalculator {

    public static int getDaysAsDigit(String days) {
        int numberOfDays;
        try {
            numberOfDays = Integer.parseInt(days);
        } catch (NumberFormatException ex) {
            System.out.println("Illegal argument!");
            return 0;
        }
        return numberOfDays;
    }

    public static String getDeadLine(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date dateDeadline = calendar.getTime();
        return dateDeadline.toString();
    }

    public static String getPlan(String surname, String days) {
        int daysAsDigit = getDaysAsDigit(days);
        String message;
        message = String.format("%s is ready to perform the task for %d days.\nDeadline is : %s",
                    surname, daysAsDigit, getDeadLine(daysAsDigit));
        return message;
    }
}
