package com.sparcs.casino;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

//Temporarily disable database components
//@SpringBootApplication
@ComponentScan
public class RouletteServiceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(RouletteServiceApplication.class, args);
	}
}
