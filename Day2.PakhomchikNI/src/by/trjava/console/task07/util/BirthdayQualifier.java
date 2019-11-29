package by.trjava.console.task07.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BirthdayQualifier {

    public static Calendar getObjectDate(String dayArgs, String monthArgs, String yearArgs) {
        int day;
        int month;
        int year;
        try {
            day = Integer.parseInt(dayArgs);
            month = Integer.parseInt(monthArgs);
            year = Integer.parseInt(yearArgs);
        }
        catch (NumberFormatException ex){
            System.out.println("Illegal arguments!");
            return null;
        }
        Calendar calendar = new GregorianCalendar(year, month, day);

        return calendar;
    }

    public static int getDayOfWeek(String dayArgs, String monthArgs, String yearArgs) {
        int dayOfWeek;
        Calendar dateOfBirth = getObjectDate(dayArgs, monthArgs, yearArgs);
        dayOfWeek = dateOfBirth.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public static int getAge(String dayArgs, String monthArgs, String yearArgs) {
        int age;
        Calendar dateOfBirth = getObjectDate(dayArgs, monthArgs, yearArgs);
        Calendar today = Calendar.getInstance();
        age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    public static boolean isBirthday(String dayArgs, String monthArgs, String yearArgs) {
        boolean isBirthday;
        Calendar dateOfBirth = getObjectDate(dayArgs, monthArgs, yearArgs);
        Calendar today = Calendar.getInstance();
        if ((today.get(Calendar.DAY_OF_MONTH) == dateOfBirth.get(Calendar.DAY_OF_MONTH)) &&
                (today.get(Calendar.MONTH) == dateOfBirth.get(Calendar.MONTH))) {
            isBirthday = true;
        } else {
            isBirthday = false;
        }
        return isBirthday;
    }

    public static String getInformationAboutBirthday(String dayArgs, String monthArgs, String yearArgs) {
        String message;
        Calendar calendarBirth = getObjectDate(dayArgs, monthArgs, yearArgs);
        Date dateOfBirth = calendarBirth.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM yyyy");
        dateFormat.format(dateOfBirth.getTime());

        int dayOfWeek = getDayOfWeek(dayArgs, monthArgs, yearArgs);
        int age = getAge(dayArgs, monthArgs, yearArgs);
        boolean isBirthday = isBirthday(dayArgs, monthArgs, yearArgs);

        message = String.format("Your Date of Birth is: %s.\nYou was born on the day of the week: %d.\n" +
                "You are %d years old.\n", dateOfBirth.toString(), dayOfWeek, age);
        if (isBirthday == true) {
            message += "Happy Birthday!!!";
        }
        return message;
    }
}
