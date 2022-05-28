package ru.netology.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import ru.netology.data.DataGenerator;
import ru.netology.dbutils.DbUtils;
import ru.netology.page.PaymentWithCardPage;
import ru.netology.page.TitlePage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentWithCardTest {

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure",new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void cleanUp() {
        DbUtils.clearDB();
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }


    @Test
    public void payApprovedCard() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.getApprovedCard());
        payment.successfulOperation().shouldBe(visible, Duration.ofSeconds(10));

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();
        var statusPayment = DbUtils.getStatusPaymentEntityTable();
        var statusOrder = DbUtils.getStatusOrderEntityTable();

        assertThat(statusPayment, containsString("APPROVED"));
        assertThat(statusOrder, containsString("APPROVED"));
        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);
    }

    @Test
    public void payDeclinedCard() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.getDeclinedCard());
        payment.successfulOperation().shouldBe(visible, Duration.ofSeconds(10));

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();
        var status = DbUtils.getStatusPaymentEntityTable();

        assertThat(status, containsString("DECLINED"));
        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichExpiresNextMonth() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.cardExpiresNextMonth());
        payment.successfulOperation().shouldBe(visible, Duration.ofSeconds(10));

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payWhichCardExpiresNextYear() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.cardExpiresNextYear());
        payment.successfulOperation().shouldBe(visible, Duration.ofSeconds(10));

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder + 1, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay + 1, actualNumberLinesPay);

    }

    @Test
    public void payCardWithEmptyFields() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.emptyFields());
        payment.wrongFormat().shouldHaveSize(4);
        payment.requiredField().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldCardNumber() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.emptyFieldCardNumber());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payOtherCardNumber() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.otherCardNumber());
        payment.errorOperation().shouldBe(visible, Duration.ofSeconds(10));

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenFifteenDigitCardNumber() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.fifteenDigitCardNumber());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenOneValueCardNumber() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.oneValueCardNumber());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldName() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.emptyFieldName());
        payment.requiredField().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenInvalidCharacters() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.invalidCharacters());
        payment.requiredField().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenNameInCyrillic() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.nameInCyrillic());
        payment.requiredField().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenNumberNameField() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.numberNameField());
        payment.requiredField().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenInvalidNameField() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.invalidNameField());
        payment.requiredField().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenOnlyFirstNameField() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.onlyFirstNameField());
        payment.requiredField().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldMonth() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.emptyFieldMonth());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichExpiresLastMonth() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.cardExpiresLastMonth());
        payment.invalidCardDate().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void checkWhenNullInFieldMonth() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.nullInFieldMonth());
        payment.invalidCardDate().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheMonthField() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.enteringLettersInTheMonthField());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void checkExpiredCardInTheCurrentYear() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.expiredCardInTheCurrentYear());
        payment.invalidCardDate().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenInvalidFieldMonth1_9() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.invalidFieldMonth1_9());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void checkWhenInvalidFieldMonth13_99() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.invalidFieldMonth13_99());
        payment.invalidCardDate().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payWhenEmptyFieldYear() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.emptyFieldYear());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichExpiredLastYear() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.cardExpiresLastYear());
        payment.cardExpired().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void pqyWhenNullInFieldYear() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.nullInFieldYear());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void enteringLettersInTheFieldYear() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.enteringLettersInTheFieldYear());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhichInvalidYear() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.invalidYear());
        payment.invalidCardDate().shouldBe(visible);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhenEmptyFieldCvcCvv() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.emptyFieldCvcCvv());
        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }

    @Test
    public void payCardWhenNullFieldCvcCvv() {
        new TitlePage().openBuyCard();
        var payment = new PaymentWithCardPage();

        var beforeNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var beforeNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        payment.paymentByCardOrCredit(DataGenerator.nullFieldCvcCvv());
        payment.wrongFormat().shouldHaveSize(1);

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
        payment.paymentByCardOrCredit(DataGenerator.twoCharactersFieldCvcCvv());

        payment.wrongFormat().shouldHaveSize(1);

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
        payment.paymentByCardOrCredit(DataGenerator.enteringLettersInTheFieldCvcCvv());

        payment.wrongFormat().shouldHaveSize(1);

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
        payment.paymentByCardOrCredit(DataGenerator.threeNullFieldCvcCvv());

        payment.wrongFormat().shouldHaveSize(1);

        var actualNumberLinesOrder = DbUtils.getCountLinesOrderEntity();
        var actualNumberLinesPay = DbUtils.getCountLinesPaymentEntity();

        assertEquals(beforeNumberLinesOrder, actualNumberLinesOrder);
        assertEquals(beforeNumberLinesPay, actualNumberLinesPay);

    }


}
