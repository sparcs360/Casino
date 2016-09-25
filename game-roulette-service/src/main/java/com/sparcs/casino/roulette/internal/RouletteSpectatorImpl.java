package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.Customer;
import com.sparcs.casino.game.SpectatorImpl;
import com.sparcs.casino.roulette.RouletteRoom;
import com.sparcs.casino.roulette.RouletteSpectator;

/**
 * A {@link Customer} watching a game of Roulette in a {@link RouletteRoom}.
 * 
 * @author Lee Newfeld
 */
@Component("Spectator")
@Scope("prototype")
public class RouletteSpectatorImpl extends SpectatorImpl implements RouletteSpectator {

	private static final Logger log = LoggerFactory.getLogger(RouletteSpectatorImpl.class);

	@Autowired
	protected RouletteSpectatorImpl(Customer customer) {
		
		super(customer);
	}

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	public String toString() {

		return String.format("RouletteSpectator@%x[%s]",
				this.hashCode(), getNickName());
	}
}
