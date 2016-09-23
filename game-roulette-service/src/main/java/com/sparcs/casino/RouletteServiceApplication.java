package com.sparcs.casino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RouletteServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(RouletteServiceApplication.class);

	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(RouletteServiceApplication.class);
		
		log.trace("+run()");
		app.run(args);
		log.trace("-run()");
	}
}
