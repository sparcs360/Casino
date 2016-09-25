package com.sparcs.casino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration 
@ComponentScan
public class RouletteServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(RouletteServiceApplication.class);

	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(RouletteServiceApplication.class);
		
		log.trace("+run()");
		app.run(args);
		log.trace("-run()");
	}
	
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	
        return application.sources(RouletteServiceApplication.class);
    }
}
