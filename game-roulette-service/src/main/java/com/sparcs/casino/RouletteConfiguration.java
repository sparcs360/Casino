package com.sparcs.casino;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.sparcs.casino.RouletteConfigurationProperties;

/**
 * Application configuration
 * 
 * @author Lee Newfeld
 */
@Configuration
@EnableConfigurationProperties(RouletteConfigurationProperties.class)
public class RouletteConfiguration {
	
}