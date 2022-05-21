package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentWithCard {
    private SelenideElement heading = $$(".heading").findBy(Condition.text("Оплата по карте"));
    private SelenideElement cardNumberField = $("[role='button'] .button__content .button__text");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement nameField = $("[#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input]");
    private SelenideElement codeField = $("[placeholder='999']");
    private SelenideElement buttonContinue = $(".button .button__content .button__text .spin");


    public PaymentWithCard() {
        heading.shouldBe(Condition.visible);
    }



}



