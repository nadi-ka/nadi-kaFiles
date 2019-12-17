package by.trjava.task06.util;


import by.trjava.exception.NegativeValueException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UtilTime {

    public static String getTime(int seconds) throws NegativeValueException {
        if (seconds < 0) {
            throw new NegativeValueException("Exception in Class by.trjava.task06.util.UtilTime, Method - " +
                    "getTime(). Value of time cannot be negative!");
        }
        int resultHours;
        int resultMinutes;
        int resultSeconds;
        int amountOfSecondsInMinute = 60;
        int amountOfMinutesInHour = 60;
        resultHours = seconds / (amountOfMinutesInHour * amountOfSecondsInMinute);
        resultMinutes = (seconds - (resultHours * amountOfMinutesInHour * amountOfSecondsInMinute)) /
                amountOfSecondsInMinute;
        resultSeconds = seconds - (resultHours * amountOfSecondsInMinute * amountOfMinutesInHour) -
                (resultMinutes * amountOfSecondsInMinute);
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, resultHours);
        calendar.set(Calendar.MINUTE, resultMinutes);
        calendar.set(Calendar.SECOND, resultSeconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String resultString = dateFormat.format(calendar.getTime());
        return resultString;
    }
}
