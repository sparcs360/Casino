package com.sparcs.casino.testgame.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.GameManagerImpl;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.testgame.SnoozeGameManager;

/**
 * The referee overseeing a game of Snooze
 *  
 * @author Lee Newfeld
 */
@Component("GameManager")
@Scope("prototype")
public class SnoozeGameManagerImpl extends GameManagerImpl implements SnoozeGameManager {

	private static final Logger log = LoggerFactory.getLogger(SnoozeGameManagerImpl.class);

	private long endGameTime;

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	protected void onInitialise(Room room) {

		log.trace("{}.{}: onInitialise", room, this);

		endGameTime = (int) (Math.random() * 10) + 10;

		log.debug("{}.{}: Game over in {} ticks", room, this, endGameTime);
	}

	@Override
	protected boolean onUpdate(Room room) {

		log.trace("{}.{}: onUpdate", room, this);

		return getGameState().getGameTime() < endGameTime;
	}
	
	@Override
	protected void onShutdown(Room room) {

		log.trace("{}.{}: onShutdown", room, this);
	}
}
