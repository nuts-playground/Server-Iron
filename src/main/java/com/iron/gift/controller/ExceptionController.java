package com.iron.gift.controller;

import com.iron.gift.exception.GiftException;
import com.iron.gift.exception.InvalidRequest;
import com.iron.gift.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
		ErrorResponse response = ErrorResponse.builder()
				.code("400")
				.message("잘못된 요청입니다.")
				.build();

		for (FieldError error : e.getFieldErrors()) {
			response.addValidation(error.getField(), error.getDefaultMessage());
		}
		return response;
	}

	@ResponseBody
	@ExceptionHandler(GiftException.class)
	public ResponseEntity<ErrorResponse> giftException(GiftException e) {
		int statusCode = e.getStatusCode();

		ErrorResponse responseBody = ErrorResponse.builder()
				.code(statusCode+"")
				.message(e.getMessage())
				.validation(e.getValidation())
				.build();


		ResponseEntity<ErrorResponse> response = ResponseEntity.status(e.getStatusCode())
				.body(responseBody);

		return response;
	}
}
