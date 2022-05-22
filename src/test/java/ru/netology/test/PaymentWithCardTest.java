package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.netology.data.CardInfo;
import ru.netology.page.PaymentWithCardPage;
import ru.netology.page.TitlePage;

import static com.codeborne.selenide.Selenide.*;

public class PaymentWithCardTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @Test
    public void payApprovedCard(){
        var titlePage = new TitlePage();
        titlePage.openBuyCard();
        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.getApprovedCard());
    }

    @Test
    public void fgd(){
        var titlePage = new TitlePage();
        titlePage.openBuyCard();
        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.getApprovedCard());

    }

}
