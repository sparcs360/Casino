package com.sparcs.casino.roulette.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.GameImpl;
import com.sparcs.casino.game.RoomImpl;
import com.sparcs.casino.roulette.Roulette;

/**
 * An implementation of the game of {@link Roulette}
 * 
 * @author Lee Newfeld
 */
@Component
@Scope("prototype")
public class RouletteImpl extends GameImpl implements Roulette {

	private static final Logger log = LoggerFactory.getLogger(RouletteImpl.class);

	private int endTick;
	
	@Override
	protected void onReset(RoomImpl room) {

		endTick = (int)(Math.random() * 10) + 10;

		log.debug("{}: Game over in {} ticks", this, endTick);
	}

	@Override
	protected boolean onUpdate(RoomImpl room) {

		log.debug("{}, onUpdate", this);
		
		return room.getTick() < endTick;
	}
	
	@Override
	public String toString() {

		return super.toString();
	}
}
