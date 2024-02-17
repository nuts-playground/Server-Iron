package com.iron.gift;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping
	@RequestMapping("/hello")
	public String hello(@RequestParam(required = false) String param) {
		return "Hello " + param;
	}
}
