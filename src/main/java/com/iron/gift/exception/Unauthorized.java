package com.iron.gift.exception;

/**
 * @author iron
 * <p>
 * status : 401
 */

public class Unauthorized extends GiftException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() {
        super();
    }
    public Unauthorized(String message) {
        super(message);
    }

    public Unauthorized(String message, Throwable cause) {
        super(message, cause);
    }


    @Override
    public int getStatusCode() {
        return 401;
    }
}
