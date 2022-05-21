package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static ru.netology.data.DataGenerator.*;

@Data
@RequiredArgsConstructor
@Value
public class CardInfo {
    private String name;
    private String codeCvcCvv;
    private String month;
    private String year;


    public static CardInfo getApprovedCard() {
        return new CardInfo(
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
                getThisMonth(),
                getThisYear());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(
                getValidName("EN"),
                DataGenerator.getCodeCvcCvv(),
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