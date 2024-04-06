package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.page.PaymentPage;


import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayWithoutCredit {
    @BeforeEach
    public void start() {
        open("http://localhost:8080");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;
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
    void positivePurchaseApproved() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getApprovedCard();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void positivePurchaseDeclined() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());

    }

    @Test
    void negativePurchaseWithEmptyFields() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getEmptyCard();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void negativePurchaseNumberCard15Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseCardNotInDatabase() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseMonth1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseMonthOver12() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseMonth00ThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }
    @Test
    void negativePurchaseMonth00OverThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseYear00() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear00();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseYear1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear1Symbol();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseYearUnderThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseYearOverThisYearOn6() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseCvv1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseCvv2Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseOwner1Word() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolder1Word();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseOwnerCyrillic() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderCyrillic();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseOwnerNumeric() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void negativePurchaseOwnerSpecialSymbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
