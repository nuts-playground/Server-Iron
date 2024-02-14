package com.iron.gift.controller;

import com.iron.gift.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PostController {

	@PostMapping("/posts")
	public Map<String, String> post(@RequestBody @Valid PostCreate param, BindingResult result) {
		log.info(param.toString());
		if (result.hasErrors()) {
			List<FieldError> filedErrors = result.getFieldErrors();
			FieldError firstFieldError = filedErrors.get(0);
			String fieldName = firstFieldError.getField();    // title
			String errorMessage = firstFieldError.getDefaultMessage();    // .. 에러 메시지

			Map<String, String> error = new HashMap<>();
			error.put(fieldName, errorMessage);
			return error;
		}
		return Map.of();
	}
}
