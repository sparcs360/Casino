package com.sparcs.casino;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.sparcs.casino.roulette.Config;

@Configuration
@EnableConfigurationProperties(Config.class)
public class RouletteConfiguration {
	
}