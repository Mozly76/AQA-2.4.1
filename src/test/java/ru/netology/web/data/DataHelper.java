package ru.netology.web.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String number;
    }

    public static CardInfo getCardInfo(String id) {
        if (id.equals("1")) {
            return new CardInfo("5559 0000 0000 0001");
        }
        if (id.equals("2")) {
            return new CardInfo("5559 0000 0000 0002");
        } else {
            return new CardInfo("0000 0000 0000 0000");
        }
    }
}