package com.sparcs.casino.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The base implementation of a {@link GameManager)
 * 
 * @author Lee Newfeld
 */
public abstract class GameManagerImpl implements GameManager {

	private static final Logger log = LoggerFactory.getLogger(GameManagerImpl.class);

	protected Room room;
	
	@Autowired
	private GameState state;

	/**
	 * Construction
	 * 
	 * @param room The {@link Room} we're in.
	 */
	protected GameManagerImpl(Room room) {
		
		this.room = room;
	}
	
	@Override
	public boolean isGameRunning() {

		return state.getGameTime() != GameState.UNDEFINED_TIME;
	}

	@Override
	public GameState getGameState() {

		return state;
	}

	@Override
	public void initialise() {

		state.resetTime();
		
		onInitialise();
	}

	@Override
	public boolean update() {

		state.advanceTime();
		log.trace("{}: gameTime={}", this, state.getGameTime());
		
		return onUpdate();
	}

	@Override
	public void shutdown() {
		
		onShutdown();
	}

	/**
	 * Reset the game state to the point prior to game start
	 */
	protected void onInitialise() {};

	/**
	 * Execute a slice of game time
	 *  
	 * @return true if the game is running
	 */
	protected abstract boolean onUpdate();
	
	/**
	 * The game is about to be packed away - tidy up!
	 */
	protected void onShutdown() {};
	
	/**
	 * Send a message that everyone in the Room can hear
	 * 
	 * @param message
	 * @param args
	 */
	protected void shout(String message, Object... args) {

		// TODO: Need events :|
		log.info("[ROOM] " + message, (Object[])args);
	}
}
