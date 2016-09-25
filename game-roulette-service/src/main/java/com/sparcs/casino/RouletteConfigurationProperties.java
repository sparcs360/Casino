package com.sparcs.casino;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.sparcs.casino.RouletteServiceApplication;

/**
 * Configuration for the {@link RouletteServiceApplication} microservice
 * 
 * @author Lee Newfeld
 */
@ConfigurationProperties(prefix="roulette")
public class RouletteConfigurationProperties {

	private static final Logger log = LoggerFactory.getLogger(RouletteConfigurationProperties.class);

	private int gameCount = 1;

	@PostConstruct
	private void initialise() {

		log.info("Initialised");
	}

	public int getGameCount() {
		return gameCount;
	}

	public void setGameCount(int gameCount) {
		this.gameCount = gameCount;
	}
}
