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

//    public static String getThisMonth() {
//        LocalDate date = LocalDate.now();
//        return String.format("%02d", date.getMonthValue());
//
//    }

    //    public static String getLastMonth(int value) {
//        LocalDate date = LocalDate.now();
//        return String.format("%tm", date.minusMonths(value));
//
//    }

    public static String getMonth(int value) {
        LocalDate date = LocalDate.now();
        return String.format("%tm", date.plusMonths(value));

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

//    public static String getThisYear() {
//        return String.format("%ty", Year.now());
//    }
//    public static String getFutureYear() {
//        return String.format("%ty", Year.now().plusYears(6));
//    }

    public static String getLastYear(int value) {
        return String.format("%ty", Year.now().minusYears(value));
    }

    public static String getCurrentAndFutureYear(int value) {
        return String.format("%ty", Year.now().plusYears(value));
    }

    public static String getRandomInvalidName(String locale) {
        Faker name = new Faker(new Locale(locale));
        return name.name().firstName() + "_" + name.name().lastName();
    }

    public static String getFirstName(String locale) {
        Faker name = new Faker(new Locale(locale));
        return name.name().firstName();
    }

    public static CardNumber approvedCard() {

        return new CardNumber("1111_2222_3333_4444", "APPROVED");
    }

    public static CardNumber declinedCard() {

        return new CardNumber("5555_6666_7777_8888", "DECLINED");

    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(declinedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo emptyFields() {
        return new CardInfo("", "", "", "", "");
    }

//    Поле НОМЕР КАРТЫ

    public static CardInfo emptyFieldCardNumber() {
        return new CardInfo("",
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo otherCardNumber() {
        return new CardInfo(DataGenerator.getOtherCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo fifteenDigitCardNumber() {
        return new CardInfo(DataGenerator.getFifteenDigitCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo oneValueCardNumber() {
        return new CardInfo("1",
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }


//    Поле ВЛАДЕЛЕЦ

    public static CardInfo emptyFieldName() {
        return new CardInfo(approvedCard().getCardNumber(),
                "",
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo invalidCharacters() {
        return new CardInfo(approvedCard().getCardNumber(),
                "?.:%№!",
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo nameInCyrillic() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("ru"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo numberNameField() {
        return new CardInfo(approvedCard().getCardNumber(),
                "12345",
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo invalidNameField() {
        return new CardInfo(approvedCard().getCardNumber(),
                DataGenerator.getRandomInvalidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo onlyFirstNameField() {
        return new CardInfo(approvedCard().getCardNumber(),
                DataGenerator.getFirstName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(0));
    }


    //    Поле МЕСЯЦ

    public static CardInfo emptyFieldMonth() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                "",
                getCurrentAndFutureYear(0));
    }

    public static CardInfo cardExpiresNextMonth() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(1),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo cardExpiresLastMonth() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(11),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo nullInFieldMonth() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                "00",
                getCurrentAndFutureYear(0));
    }

    public static CardInfo enteringLettersInTheMonthField() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                "kf",
                getCurrentAndFutureYear(0));
    }

    public static CardInfo expiredCardInTheCurrentYear() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(11),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo invalidFieldMonth1_9() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getRandomInvalidMonth1_9(),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo invalidFieldMonth13_99() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getRandomInvalidMonth13_99(),
                getCurrentAndFutureYear(0));
    }


//    Поле ГОД

    public static CardInfo emptyFieldYear() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                "");
    }

    public static CardInfo cardExpiresNextYear() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(1));
    }

    public static CardInfo cardExpiresLastYear() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                DataGenerator.getLastYear(1));
    }

    public static CardInfo nullInFieldYear() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                "00");
    }

    public static CardInfo enteringLettersInTheFieldYear() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                "fg");
    }


    public static CardInfo invalidYear() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getMonth(0),
                getCurrentAndFutureYear(6));
    }


// Поле CVC/CVV

    public static CardInfo emptyFieldCvcCvv() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                "",
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo nullFieldCvcCvv() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                "0",
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo threeNullFieldCvcCvv() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                "000",
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo twoCharactersFieldCvcCvv() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                "12",
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

    public static CardInfo enteringLettersInTheFieldCvcCvv() {
        return new CardInfo(approvedCard().getCardNumber(),
                getValidName("EN"),
                "kr",
                getMonth(0),
                getCurrentAndFutureYear(0));
    }

}