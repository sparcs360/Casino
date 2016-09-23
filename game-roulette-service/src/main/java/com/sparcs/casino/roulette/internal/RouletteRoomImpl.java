package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.Room;
import com.sparcs.casino.game.RoomImpl;
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

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}
	
	@Override
	public String toString() {

		return String.format("RouletteRoom@%x",
				this.hashCode());
	}
}
