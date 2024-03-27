package com.iron.gift.exception;

public class InvalidSigninInformation extends GiftException {

    private static final String MESSAGE = "이메일과 비밀번호를 제대로 입력해 주세요.";
    public InvalidSigninInformation(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidSigninInformation(String message) {
        super(message);
    }
    public InvalidSigninInformation() {
        super("이메일과 비밀번호를 입력해 주세요.");
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
