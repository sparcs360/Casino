package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.Customer;
import com.sparcs.casino.game.PlayerImpl;
import com.sparcs.casino.roulette.RoulettePlayer;
import com.sparcs.casino.roulette.RouletteRoom;

/**
 * A {@link Customer} playing a game of Roulette in a {@link RouletteRoom}.
 * 
 * @author Lee Newfeld
 */
@Component("Player")
@Scope("prototype")
public class RoulettePlayerImpl extends PlayerImpl implements RoulettePlayer {

	private static final Logger log = LoggerFactory.getLogger(RoulettePlayerImpl.class);

	@Autowired
	protected RoulettePlayerImpl(Customer customer) {
		
		super(customer);
	}

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}
}
