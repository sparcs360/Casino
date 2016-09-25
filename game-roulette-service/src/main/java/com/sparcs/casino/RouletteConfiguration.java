package com.sparcs.casino;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.sparcs.casino.RouletteConfigurationProperties;

/**
 * Application configuration
 * 
 * @author Lee Newfeld
 */
@Configuration
@EnableAutoConfiguration(
	exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
	}
)
@EnableConfigurationProperties(RouletteConfigurationProperties.class)
public class RouletteConfiguration {
	
}
