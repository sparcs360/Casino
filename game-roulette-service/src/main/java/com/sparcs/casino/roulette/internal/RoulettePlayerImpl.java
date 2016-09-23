package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.Customer;
import com.sparcs.casino.game.PlayerImpl;
import com.sparcs.casino.roulette.RouletteCroupier;
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

	private RouletteCroupier croupier;
	
	@Autowired
	protected RoulettePlayerImpl(Customer customer, RouletteCroupier croupier) {
		
		super(customer);
		
		this.croupier = croupier;
	}

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	public boolean isBettingAllowed() {

		return croupier.isBettingAllowed();
	}

	@Override
	public boolean areBetsResolved() {

		return croupier.areBetsResolved();
	}

	@Override
	public boolean requestSingleBet(int number, int amount) {

		log.trace("{}: requestSingleBet(number={}, amount={})", this, number, amount);

		return croupier.considerSingleBet(this, number, amount);
	}
}
