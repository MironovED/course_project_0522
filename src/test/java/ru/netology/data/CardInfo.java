package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;


@Data
@RequiredArgsConstructor
@Value
public class CardInfo {
    private String cardNumber;
    private String name;
    private String codeCvcCvv;
    private String month;
    private String year;


}