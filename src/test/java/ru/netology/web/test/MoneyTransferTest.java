package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:7777");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        var dashboardPage = new DashboardPage();

        int expectedFirstCard = dashboardPage.getCardBalance("1") - 5_000;
        int expectedSecondCard = dashboardPage.getCardBalance("2") + 5_000;

        dashboardPage.getMoneyTransferFromFirstToSecond();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("1"), "5000");

        int actualFirstCard = dashboardPage.getCardBalance("1");
        int actualSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(expectedFirstCard, actualFirstCard);
        assertEquals(expectedSecondCard, actualSecondCard);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        var dashboardPage = new DashboardPage();

        int expectedFirstCard = dashboardPage.getCardBalance("1") + 5_000;
        int expectedSecondCard = dashboardPage.getCardBalance("2") - 5_000;

        dashboardPage.getMoneyTransferFromSecondToFirst();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("2"), "5000");

        int actualFirstCard = dashboardPage.getCardBalance("1");
        int actualSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(expectedFirstCard, actualFirstCard);
        assertEquals(expectedSecondCard, actualSecondCard);
    }

    @Test
    void shouldReloadCardBalance() {
        var dashboardPage = new DashboardPage();

        int expectedFirstCard = dashboardPage.getCardBalance("1");
        int expectedSecondCard = dashboardPage.getCardBalance("2");

        dashboardPage.reloadBalance();

        int actualFirstCard = dashboardPage.getCardBalance("1");
        int actualSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(expectedFirstCard, actualFirstCard);
        assertEquals(expectedSecondCard, actualSecondCard);
    }

    @Test
    void shouldCancelMoneyTransfer() {
        var dashboardPage = new DashboardPage();

        int expectedFirstCard = dashboardPage.getCardBalance("1");
        int expectedSecondCard = dashboardPage.getCardBalance("2");

        dashboardPage.getMoneyTransferFromSecondToFirst();
        dashboardPage.cancelMoneyTransfer();

        int actualFirstCard = dashboardPage.getCardBalance("1");
        int actualSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(expectedFirstCard, actualFirstCard);
        assertEquals(expectedSecondCard, actualSecondCard);
    }

    @Test
    void shouldNotTransferIfAccountIsNotSpecified() {
        var dashboardPage = new DashboardPage();

        dashboardPage.getMoneyTransferFromSecondToFirst();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo(""), "2000");

        moneyTransferPage.getError();
    }

    @Test
    void shouldNotTransferAmountMoreThanWhatIsOnTheAccount() {
        var dashboardPage = new DashboardPage();
        String balance = String.valueOf(dashboardPage.getCardBalance("1") + 200);

        int expectedFirstCard = dashboardPage.getCardBalance("1");
        int expectedSecondCard = dashboardPage.getCardBalance("2");

        dashboardPage.getMoneyTransferFromFirstToSecond();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("1"), balance);
        moneyTransferPage.getError();

        int actualFirstCard = dashboardPage.getCardBalance("1");
        int actualSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(expectedFirstCard, actualFirstCard);
        assertEquals(expectedSecondCard, actualSecondCard);
    }
}