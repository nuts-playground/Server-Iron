package com.iron.gift;

import com.iron.gift.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class GiftApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiftApplication.class, args);
	}

}

