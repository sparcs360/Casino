package com.sparcs.casino.testgame.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.Customer;
import com.sparcs.casino.game.SpectatorImpl;
import com.sparcs.casino.testgame.SnoozeRoom;
import com.sparcs.casino.testgame.SnoozeSpectator;

/**
 * A {@link Customer} watching a game of Snooze in a {@link SnoozeRoom}.
 * 
 * @author Lee Newfeld
 */
@Component("Spectator")
@Scope("prototype")
public class SnoozeSpectatorImpl extends SpectatorImpl implements SnoozeSpectator {

	private static final Logger log = LoggerFactory.getLogger(SnoozeSpectatorImpl.class);

	@Autowired
	protected SnoozeSpectatorImpl(Customer customer) {
		
		super(customer);
	}

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	public String toString() {

		return String.format("SnoozeSpectatorImpl@%x[%s]",
				this.hashCode(), getNickName());
	}
}
