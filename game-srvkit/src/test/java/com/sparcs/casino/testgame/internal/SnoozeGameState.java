package com.sparcs.casino.testgame.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.GameState;

/**
 * The current state of a game of Snooze.
 * 
 * @author Lee Newfeld
 */
@Component
@Scope("prototype")
public class SnoozeGameState extends GameState {

	private static final Logger log = LoggerFactory.getLogger(SnoozeGameState.class);

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}
}
