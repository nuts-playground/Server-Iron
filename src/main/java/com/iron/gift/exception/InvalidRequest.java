package com.iron.gift.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class InvalidRequest extends GiftException{
	private static final String MESSAGE = "잘못된 요청입니다.";

	public InvalidRequest() {
		super(MESSAGE);
	}

	public InvalidRequest(Throwable cause) {
		super(MESSAGE, cause);
	}

	public InvalidRequest(String field, String message) {
		super(MESSAGE);
		addValidation(field, message);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}

}
