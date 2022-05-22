package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentWithCard {
    private SelenideElement heading = $$(".heading").findBy(Condition.text("Оплата по карте"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement nameField = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement codeField = $("[placeholder='999']");
    private SelenideElement buttonContinue = $$(".button .button__content .button__text").last();

    private SelenideElement successfulOperation = $(withText("Операция одобрена Банком."));
    private SelenideElement errorOperation = $(withText("Ошибка! Банк отказал в проведении операции."));
    private ElementsCollection wrongFormat = $$(withText("Неверный формат"));
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement invalidCardDate = $(withText("Неверно указан срок действия карты"));




    public PaymentWithCard() {
        heading.shouldBe(Condition.visible);
    }

    public void paymentByCardOrCredit (CardInfo data) {
        cardNumberField.setValue(data.getCardNumber());
        monthField.setValue(data.getMonth());
        yearField.setValue(data.getYear());
        nameField.setValue(data.getName());
        codeField.setValue(data.getCodeCvcCvv());
        buttonContinue.click();
//        successfulOperation.shouldBe(visible, Duration.ofSeconds(5));
    }

    public void emptyFields(){
        cardNumberField.setValue("");
        monthField.setValue("");
        yearField.setValue("");
        nameField.setValue("");
        codeField.setValue("");
        buttonContinue.click();
        wrongFormat.shouldHaveSize(4);
        requiredField.shouldBe(visible);
    }

    public void emptyFieldCardNumber (CardInfo data) {
        cardNumberField.setValue("");
        monthField.setValue(data.getMonth());
        yearField.setValue(data.getYear());
        nameField.setValue(data.getName());
        codeField.setValue(data.getCodeCvcCvv());
        buttonContinue.click();
        wrongFormat.shouldHaveSize(1);
    }


    public void emptyFieldMonth (CardInfo data) {
        cardNumberField.setValue(data.getCardNumber());
        monthField.setValue("");
        yearField.setValue(data.getYear());
        nameField.setValue(data.getName());
        codeField.setValue(data.getCodeCvcCvv());
        buttonContinue.click();
        wrongFormat.shouldHaveSize(1);
    }

    public void emptyFieldYear (CardInfo data) {
        cardNumberField.setValue(data.getCardNumber());
        monthField.setValue(data.getMonth());
        yearField.setValue("");
        nameField.setValue(data.getName());
        codeField.setValue(data.getCodeCvcCvv());
        buttonContinue.click();
        wrongFormat.shouldHaveSize(1);
    }

    public void emptyFieldName (CardInfo data) {
        cardNumberField.setValue(data.getCardNumber());
        monthField.setValue(data.getMonth());
        yearField.setValue(data.getYear());
        nameField.setValue("");
        codeField.setValue(data.getCodeCvcCvv());
        buttonContinue.click();
        requiredField.shouldBe(visible);
    }

    public void emptyFieldCodeCvc (CardInfo data) {
        cardNumberField.setValue(data.getCardNumber());
        monthField.setValue(data.getMonth());
        yearField.setValue(data.getYear());
        nameField.setValue(data.getName());
        codeField.setValue("");
        buttonContinue.click();
        wrongFormat.shouldHaveSize(1);
    }




}



