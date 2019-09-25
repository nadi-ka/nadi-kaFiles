package com.company;

public class Horoscope {
    public Zodiac getZodiacSign(Day day, Month month) {
        switch (month) {
            case JANUARY:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY.getNumberOfDay()) {
                    return Zodiac.CAPRICORN;
                }
                if (day.getNumberOfDay() >= Day.TWENTY.getNumberOfDay() && day.getNumberOfDay() <=
                        Day.THIRTY_ONE.getNumberOfDay()) {
                    return Zodiac.AQUARIUS;
                }
            case FEBRUARY:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.NINETEEN.getNumberOfDay()) {
                    return Zodiac.AQUARIUS;
                }
                if (day.getNumberOfDay() >= Day.NINETEEN.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.TWENTY_NINE.getNumberOfDay()) {
                    return Zodiac.PISCES;
                }
            case MARCH:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_ONE.getNumberOfDay()) {
                    return Zodiac.PISCES;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_ONE.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY_ONE.getNumberOfDay()) {
                    return Zodiac.ARIES;
                }
            case APRIL:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_ONE.getNumberOfDay()) {
                    return Zodiac.ARIES;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_ONE.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY.getNumberOfDay()) {
                    return Zodiac.TAURUS;
                }
            case MAY:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_ONE.getNumberOfDay()) {
                    return Zodiac.TAURUS;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_ONE.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY_ONE.getNumberOfDay()) {
                    return Zodiac.GEMINI;
                }
            case JUNE:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_ONE.getNumberOfDay()) {
                    return Zodiac.GEMINI;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_ONE.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY.getNumberOfDay()) {
                    return Zodiac.CANCER;
                }
            case JULY:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_THREE.getNumberOfDay()) {
                    return Zodiac.CANCER;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_THREE.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY_ONE.getNumberOfDay()) {
                    return Zodiac.LEO;
                }
            case AUGUST:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_THREE.getNumberOfDay()) {
                    return Zodiac.LEO;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_THREE.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY_ONE.getNumberOfDay()) {
                    return Zodiac.VIRGO;
                }
            case SEPTEMBER:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_FOUR.getNumberOfDay()) {
                    return Zodiac.VIRGO;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_FOUR.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY.getNumberOfDay()) {
                    return Zodiac.LIBRA;
                }
            case OCTOBER:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_FOUR.getNumberOfDay()) {
                    return Zodiac.LIBRA;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_FOUR.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY_ONE.getNumberOfDay()) {
                    return Zodiac.SCORPIO;
                }
            case NOVEMBER:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_TWO.getNumberOfDay()) {
                    return Zodiac.SCORPIO;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_TWO.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY.getNumberOfDay()) {
                    return Zodiac.SAGITTARIUS;
                }
            case DECEMBER:
                if (day.getNumberOfDay() >= Day.ONE.getNumberOfDay() &&
                        day.getNumberOfDay() < Day.TWENTY_TWO.getNumberOfDay()) {
                    return Zodiac.SAGITTARIUS;
                }
                if (day.getNumberOfDay() >= Day.TWENTY_TWO.getNumberOfDay() &&
                        day.getNumberOfDay() <= Day.THIRTY_ONE.getNumberOfDay()) {
                    return Zodiac.CAPRICORN;
                }

            default: {
                return null;
            }
        }
    }
}
