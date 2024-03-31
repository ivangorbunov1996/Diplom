package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class PaymentPage {
    private SelenideElement heading = $(byText("Путешествие дня"));
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public Payment orderCardPage() {
        buyButton.click();
        return new Payment();
    }

    public BuyInCreditPayment creditPage() {
        creditButton.click();
        return new BuyInCreditPayment();
    }
}