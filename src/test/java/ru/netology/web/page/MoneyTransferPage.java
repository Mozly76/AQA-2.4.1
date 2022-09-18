package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {

    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transfer = $("[data-test-id=action-transfer]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public DashboardPage moneyTransfer(DataHelper.CardInfo from, String amountToTransfer) {
        amount.setValue(amountToTransfer);
        fromField.setValue(from.getNumber());
        transfer.click();
        return new DashboardPage();
    }

    public void getError() {
        error.shouldBe(Condition.visible);
    }
}