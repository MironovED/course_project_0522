package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TitlePage {
    private SelenideElement buyCard = $("[role='button'] .button__content .button__text");
    private SelenideElement buyInCredit = $$("[role='button'] .button__content .button__text").last();


    public PaymentWithCardPage openBuyCard() {
        buyCard.click();
        return new PaymentWithCardPage();
    }


}
