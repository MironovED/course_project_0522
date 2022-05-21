package ru.netology.data;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

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
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.MONTH));
    }

    public static String getThisYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public static String getRandomInvalidName() {
        var random = new Random();
        var list = Arrays.asList("", "+79008007060", "Alina_Fomina", "Olga", "Cветлана Виноградова", ";.,:%№!");
        String randomName = list.get(random.nextInt(list.size()));
        return randomName;
    }

}
