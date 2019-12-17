package by.trjava.task02.util;

public class UtilNumberOfDaysInTheMonth {

    public static boolean isALeapYear(int anyYear) {
        if (anyYear % 4 != 0) {
            return false;
        } else if (anyYear % 400 == 0) {
            return true;
        } else if (anyYear % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static int getNumberOfDaysInTheMonth(int anyMonth, int anyYear) {
        int monthWith30Days = 30;
        int monthWith31Days = 31;
        int february28 = 28;
        int february29 = 29;
        switch (anyMonth) {
            case 1:
                return monthWith31Days;
            case 2:
                if (isALeapYear(anyYear)){
                    return february29;
                }
                else{
                    return february28;
                }
            case 3:
                return monthWith31Days;
            case 4:
                return monthWith30Days;
            case 5:
                return monthWith31Days;
            case 6:
                return monthWith30Days;
            case 7:
                return monthWith31Days;
            case 8:
                return monthWith31Days;
            case 9:
                return monthWith30Days;
            case 10:
                return monthWith31Days;
            case 11:
                return monthWith30Days;
            case 12:
                return monthWith31Days;
            default:
                return 0;
        }
    }
}
