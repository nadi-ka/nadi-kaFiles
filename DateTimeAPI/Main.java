package com.company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        System.out.println(LocalTime.now());
        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());
        System.out.println();

        LocalDate now = LocalDate.now();
        LocalDate plusYear = now.plusYears(1);
        LocalDate plusMonth = plusYear.plusMonths(2);
        LocalDate plusDays = plusMonth.plusDays(20);
        System.out.println(plusDays);
        System.out.println();

        LocalDateTime now1 = LocalDateTime.now();
        LocalDateTime plus3 = now1.plusDays(3);
        boolean after = now1.isAfter(plus3);
        System.out.println(after);

        LocalDate now2 = LocalDate.now();
        System.out.println(now2.format(DateTimeFormatter.ofPattern("dd YYYY MMM", new Locale("RU"))));
        System.out.println(now2.format(DateTimeFormatter.ofPattern("dd YYYY MMM", new Locale
                ("Cambodia"))));

    }
}
