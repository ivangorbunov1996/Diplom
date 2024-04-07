package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayWithCredit {
    @BeforeEach
    public void start() {
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }


    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }


    @Test
    void positivePurchaseWithCreditApproved() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getApprovedCard();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void positivePurchaseWithCreditDeclined() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void negativePurchaseWithCreditEmptyFields() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getEmptyCard();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat4Fields();

        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void negativePurchaseWithCreditNumberCard15Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditCardNotInDatabase() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditMonth1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditMonthOver12() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditMonth00ThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditMonth00OverThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditYear00() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear00();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditYear1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear1Symbol();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditYearUnderThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditYearOverThisYearOn6() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditCvv1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditCvv2Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditOwner1Word() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolder1Word();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditOwnerCyrillic() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderCyrillic();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditOwnerNumeric() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseWithCreditOwnerSpecialSymbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
