package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

public class DataGenerator {


    public static String getValidName (String locale) {
        Faker name = new Faker(new Locale(locale));
        return name.name().fullName();
    }

    public static String getCodeCvcCvv(){
    Faker code = new Faker();
    return code.number().digits(3);
    }

    public static String getOtherCardNumber() {
        Faker cardNumber = new Faker();
    return cardNumber.number().digits(16);
    }

    public static String getThisMonth() {
        LocalDate date = LocalDate.now();
        return String.format("%02d", date.getMonthValue());

    }

    public static String getValidMonth() {
        var random = new Random();
        var list = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        String randomMonth = list.get(random.nextInt(list.size()));
        return  randomMonth;
    }

    public static String getThisYear() {
        return String.format("%ty", Year.now());
    }

    public static String getRandomInvalidName() {
        var random = new Random();
        var list = Arrays.asList("", "+79008007060", "Alina_Fomina", "Olga", "Cветлана Виноградова", ";.,:%№!");
        String randomName = list.get(random.nextInt(list.size()));
        return randomName;
    }

}
