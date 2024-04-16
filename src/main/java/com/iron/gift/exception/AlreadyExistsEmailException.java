package com.iron.gift.exception;

public class AlreadyExistsEmailException extends GiftException {

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";
    public AlreadyExistsEmailException(String message, Throwable cause) {
        super(message, cause);
    }
    public AlreadyExistsEmailException(String message) {
        super(message);
    }
    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
