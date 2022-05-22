package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static ru.netology.data.DataGenerator.*;

@Data
@RequiredArgsConstructor
@Value
public class CardInfo {
    private String cardNumber;
    private String name;
    private String codeCvcCvv;
    private String month;
    private String year;


    public static CardInfo getApprovedCard() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(declinedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo emptyFields() {
        return new CardInfo("", "", "", "", "");
    }

//    Поле НОМЕР КАРТЫ

    public static CardInfo emptyFieldCardNumber() {
        return new CardInfo("",
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getThisMonth(),
                getThisYear());
    }

    public static CardInfo otherCardNumber() {
        return new CardInfo(DataGenerator.getOtherCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getThisMonth(),
                getThisYear());
    }

    public static CardInfo FifteenDigitCardNumber() {
        return new CardInfo(DataGenerator.getFifteenDigitCardNumber(),
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getThisMonth(),
                getThisYear());
    }

    public static CardInfo oneValueCardNumber() {
        return new CardInfo("1",
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getThisMonth(),
                getThisYear());
    }


//    Поле ВЛАДЕЛЕЦ

    public static CardInfo getEmptyFieldName() {
        return new CardInfo(approvedCard().cardNumber,
                "",
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }




    //    Поле МЕСЯЦ

    public static CardInfo emptyFieldMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                "",
                getThisYear());
    }

    public static CardInfo cardExpiresNextMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getNextMonth(),
                getThisYear());
    }

    public static CardInfo cardExpiresLastMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getLastMonth(),
                getThisYear());
    }

    public static CardInfo nullInFieldMonth() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                "00",
                getThisYear());
    }

    public static CardInfo enteringLettersInTheMonthField() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                "kf",
                getThisYear());
    }

    public static CardInfo expiredCardInTheCurrentYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getLastMonth(),
                getThisYear());
    }

    public static CardInfo invalidFieldMonth1_9() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getRandomInvalidMonth1_9(),
                getThisYear());
    }

    public static CardInfo invalidFieldMonth13_99() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                DataGenerator.getRandomInvalidMonth13_99(),
                getThisYear());
    }


//    Поле ГОД

    public static CardInfo emptyFieldYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                "");
    }

    public static CardInfo cardExpiresNextYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                DataGenerator.getNextYear());
    }

    public static CardInfo cardExpiresLastYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                DataGenerator.getLastYear());
    }

    public static CardInfo nullInFieldYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                "00");
    }

    public static CardInfo enteringLettersInTheFieldYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                "fg");
    }

    public static CardInfo cardExpiredInTheLastYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                DataGenerator.getLastYear());
    }

    public static CardInfo invalidYear() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                DataGenerator.getFutureYear());
    }


// Поле CVC/CVV

    public static CardInfo getEmptyFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "",
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo getNullFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "0",
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo getTwoCharactersFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "12",
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo enteringLettersInTheFieldCvcCvv() {
        return new CardInfo(approvedCard().cardNumber,
                getValidName("EN"),
                "kr",
                getThisMonth(),
                getThisYear());
    }



    @Value
    public static class CardNumber {
        private String cardNumber;
        private String status;
    }

    public static CardNumber approvedCard() {

        return new CardNumber("1111_2222_3333_4444", "APPROVED");
    }

    public static CardNumber declinedCard() {

        return new CardNumber("5555_6666_7777_8888", "DECLINED");

    }
}