package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.Bank;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.game.RoomImpl;
import com.sparcs.casino.roulette.RouletteCroupier;
import com.sparcs.casino.roulette.RouletteRoom;

/**
 * A {@link Room} hosting a game of Roulette.
 * 
 * @author Lee Newfeld
 */
@Component("Room")
@Scope("prototype")
public class RouletteRoomImpl extends RoomImpl implements RouletteRoom {

	private static final Logger log = LoggerFactory.getLogger(RouletteRoomImpl.class);

	@Autowired
	private Bank bank;
	
	@PostConstruct
	private void postConstruct() {

		log.trace("Created {}", this);
		
		// EVENT SUBSCRIPTION
		//
		// RouletteCroupier.BetPlacedEvent
		getEventBroker().subscribe(be -> {
			
			RouletteCroupier.BetPlacedEvent e = (RouletteCroupier.BetPlacedEvent)be;

			bank.processBet(e.getBet());

		}, RouletteCroupier.BetPlacedEvent.class);		

		// RouletteCroupier.BetWinEvent
		getEventBroker().subscribe(be -> {
			
			RouletteCroupier.BetWinEvent e = (RouletteCroupier.BetWinEvent)be;

			bank.processWinnings(e.getBet(), e.getWinnings());

		}, RouletteCroupier.BetWinEvent.class);		
	}
	
	@Override
	public String toString() {

		return String.format("RouletteRoom@%x",
				this.hashCode());
	}
}
