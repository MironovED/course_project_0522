package ru.netology.data;

import lombok.*;

@Data
@RequiredArgsConstructor
@Value
public class CardNumber {
    private String cardNumber;
    private String status;


}
