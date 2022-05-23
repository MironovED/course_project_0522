package ru.netology.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.netology.data.CardInfo;
import ru.netology.dbutils.DbUtils;
import ru.netology.page.PaymentWithCardPage;
import ru.netology.page.TitlePage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentWithCardTest {
    private SelenideElement successfulOperation = $(withText("Операция одобрена Банком."));
    private SelenideElement errorOperation = $(withText("Ошибка! Банк отказал в проведении операции."));
    private ElementsCollection wrongFormat = $$(withText("Неверный формат"));
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement invalidCardDate = $(withText("Неверно указан срок действия карты"));


    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void cleanUp() {
        DbUtils.clearDB();
    }

    @Test
    public void payApprovedCard() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.getApprovedCard());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);
    }

    @Test
    public void payDeclinedCard() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.getDeclinedCard());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payCardExpiresNextMonth() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresNextMonth());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payCardExpiresNextYear() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresNextYear());

        successfulOperation.shouldBe(visible, Duration.ofSeconds(10));
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payCardEmptyFields() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.emptyFields());

        wrongFormat.shouldHaveSize(4);
        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payEmptyFieldCardNumber() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldCardNumber());

        wrongFormat.shouldHaveSize(1);
        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payOtherCardNumber() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.otherCardNumber());

        errorOperation.shouldBe(visible, Duration.ofSeconds(10));

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payFifteenDigitCardNumber() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.fifteenDigitCardNumber());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payOneValueCardNumber() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.oneValueCardNumber());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payEmptyFieldName() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldName());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payInvalidCharacters() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.invalidCharacters());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payNameInCyrillic() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.nameInCyrillic());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payNumberNameField() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.numberNameField());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payInvalidNameField() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.invalidNameField());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void OnlyFirstNameField() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.onlyFirstNameField());

        requiredField.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void emptyFieldMonth() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldMonth());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void cardExpiresLastMonth() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresLastMonth());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void nullInFieldMonth() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.nullInFieldMonth());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheMonthField() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.enteringLettersInTheMonthField());

        wrongFormat.shouldHaveSize(1);


        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void expiredCardInTheCurrentYear() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.expiredCardInTheCurrentYear());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void invalidFieldMonth1_9() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.invalidFieldMonth1_9());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void invalidFieldMonth13_99() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.invalidFieldMonth13_99());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void emptyFieldYear() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldYear());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void cardExpiresLastYear() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.cardExpiresLastYear());

        cardExpired.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void nullInFieldYear() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.nullInFieldYear());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheFieldYear() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.enteringLettersInTheFieldYear());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void invalidYear() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.invalidYear());

        invalidCardDate.shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void emptyFieldCvcCvv() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.emptyFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void nullFieldCvcCvv() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.nullFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void twoCharactersFieldCvcCvv() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.twoCharactersFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheFieldCvcCvv() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.enteringLettersInTheFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void threeNullFieldCvcCvv() {
        var titlePage = new TitlePage();
        titlePage.openBuyCard();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        var payment = new PaymentWithCardPage();
        payment.paymentByCardOrCredit(CardInfo.threeNullFieldCvcCvv());

        wrongFormat.shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }




}
