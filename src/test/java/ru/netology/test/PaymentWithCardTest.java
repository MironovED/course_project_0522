package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class PaymentWithCardTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @Test
    public void payApprovedCard(){
        $("[role='button'] .button__content .button__text").click();





    }


}
