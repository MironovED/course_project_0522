package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentWithCardPage {

    private SelenideElement heading = $$(".heading").findBy(Condition.text("Оплата по карте"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement nameField = $(byXpath("//span[text()='Владелец']/../*[@class='input__box']//*[@class='input__control']"));
    private SelenideElement codeField = $("[placeholder='999']");
    private SelenideElement buttonContinue = $$(".button .button__content .button__text").last();

    private SelenideElement successfulOperation = $(withText("Операция одобрена Банком."));
    private SelenideElement errorOperation = $(withText("Ошибка! Банк отказал в проведении операции."));
    private ElementsCollection wrongFormat = $$(withText("Неверный формат"));
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement invalidCardDate = $(withText("Неверно указан срок действия карты"));


    public PaymentWithCardPage() {
        heading.shouldBe(Condition.visible);
    }

    public PaymentWithCardPage paymentByCardOrCredit(CardInfo data) {
        cardNumberField.setValue(data.getCardNumber());
        monthField.setValue(data.getMonth());
        yearField.setValue(data.getYear());
        nameField.setValue(data.getName());
        codeField.setValue(data.getCodeCvcCvv());
        buttonContinue.click();
        return new TitlePage().openBuyCard();
    }

    public SelenideElement successfulOperation() {
        return successfulOperation;
    }

    public SelenideElement errorOperation() {
        return errorOperation;
    }

    public ElementsCollection wrongFormat() {
        return wrongFormat;
    }

    public SelenideElement requiredField() {
        return requiredField;
    }

    public SelenideElement cardExpired() {
        return cardExpired;
    }

    public SelenideElement invalidCardDate() {
        return invalidCardDate;
    }


}



