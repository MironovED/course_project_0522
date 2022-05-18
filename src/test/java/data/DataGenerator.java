package data;

import com.github.javafaker.Faker;
import lombok.Value;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

private DataGenerator(){}

    @Value
    public static class CardInfo {
        private String name;
        private String CardNumber;
        private String codeCvcCvv;
        private String month;
        private String year;


//        public static CardInfo getApprovedCard () {
//            return new CardInfo(
//                    getValidName("EN"),
//                    "1111_2222_3333_4444",
//                    DataGenerator.getCodeCvcCvv(),
//                    getThisMonth(),
//                    getThisYear());
//        }
//
//        public static CardInfo getDeclinedCard () {
//            return new CardInfo(
//                    getValidName("EN"),
//                    "5555_6666_7777_8888",
//                    DataGenerator.getCodeCvcCvv(),
//                    getThisMonth(),
//                    getThisYear());
//        }

    }



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
