package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

public class DataGenerator {


    public static String getValidName(String locale) {
        Faker name = new Faker(new Locale(locale));
        return name.name().fullName();
    }

    public static String getCodeCvcCvv() {
        Faker code = new Faker();
        return code.number().digits(3);
    }

    public static String getOtherCardNumber() {
        Faker cardNumber = new Faker();
        return cardNumber.number().digits(16);
    }

    public static String getFifteenDigitCardNumber() {
        Faker cardNumber = new Faker();
        return cardNumber.number().digits(15);
    }

//    Методы МЕСЯЦ

    public static String getThisMonth() {
        LocalDate date = LocalDate.now();
        return String.format("%02d", date.getMonthValue());

    }

    public static String getNextMonth() {
        LocalDate date = LocalDate.now();
        return String.format("%tm", date.plusMonths(1));

    }

    public static String getLastMonth() {
        LocalDate date = LocalDate.now();
        return String.format("%tm", date.minusMonths(1));

    }

    public static String getRandomInvalidMonth1_9() {
        var min = 1;
        var max = 9;
        return String.valueOf(rnd(min, max));
    }

    public static String getRandomInvalidMonth13_99() {
        var min = 13;
        var max = 99;
        return String.valueOf(rnd(min, max));
    }

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }


// Методы ГОД

    public static String getThisYear() {
        return String.format("%ty", Year.now());
    }

    public static String getLastYear() {
        return String.format("%ty", Year.now().minusYears(1));
    }

    public static String getNextYear() {
        return String.format("%ty", Year.now().plusYears(1));
    }

    public static String getFutureYear() {
        return String.format("%ty", Year.now().plusYears(6));
    }


    public static String getRandomInvalidName(String locale) {
        Faker name = new Faker(new Locale(locale));
        return name.name().firstName() + "_" + name.name().lastName();
    }

    public static String getFirstName(String locale) {
        Faker name = new Faker(new Locale(locale));
        return name.name().firstName();
    }

}