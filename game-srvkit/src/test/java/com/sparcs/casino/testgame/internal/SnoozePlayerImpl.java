package com.sparcs.casino.testgame.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.Customer;
import com.sparcs.casino.game.PlayerImpl;
import com.sparcs.casino.testgame.SnoozeGameManager;
import com.sparcs.casino.testgame.SnoozePlayer;
import com.sparcs.casino.testgame.SnoozeRoom;

/**
 * A {@link Customer} playing a game of Snooze in a {@link SnoozeRoom}.
 * 
 * @author Lee Newfeld
 */
@Component("Player")
@Scope("prototype")
public class SnoozePlayerImpl extends PlayerImpl implements SnoozePlayer {

	private static final Logger log = LoggerFactory.getLogger(SnoozePlayerImpl.class);

	@SuppressWarnings("unused")
	private SnoozeGameManager gameManager;
	
	@Autowired
	protected SnoozePlayerImpl(Customer customer, SnoozeGameManager gameManager) {
		
		super(customer);
	}

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}
}
