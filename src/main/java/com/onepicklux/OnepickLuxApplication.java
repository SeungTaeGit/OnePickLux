package com.onepicklux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OnepickLuxApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnepickLuxApplication.class, args);
	}

}
