package com.ij026.team3.mfpe.offersmicroservice;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OffersMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffersMicroserviceApplication.class, args);
	}

	@Bean
	public SimpleDateFormat dateFormat() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return simpleDateFormat;
	}

}
