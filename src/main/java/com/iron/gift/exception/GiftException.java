package com.iron.gift.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class GiftException extends RuntimeException{
	private final Map<String, String> validation = new HashMap<>();

	public GiftException(String message) {
		super(message);
	}

	public GiftException(String message, Throwable cause) {
		super(message, cause);
	}

    public GiftException() {

    }

    public abstract int getStatusCode();

	public void addValidation(String field, String message) {
		validation.put(field, message);
	}

	public Map<String, String> getValidation() {
		return validation;
	}
}
