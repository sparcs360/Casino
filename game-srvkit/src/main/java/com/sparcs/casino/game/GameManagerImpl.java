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

	@Autowired
	private GameState state;

	@Override
	public boolean isGameRunning() {

		return state.getGameTime() != GameState.UNDEFINED_TIME;
	}

	@Override
	public GameState getGameState() {

		return state;
	}

	@Override
	public void initialise(Room room) {

		state.resetTime();
		
		onInitialise(room);
	}

	@Override
	public boolean update(Room room) {

		state.advanceTime();
		log.trace("{}: gameTime={}", this, state.getGameTime());
		
		return onUpdate(room);
	}

	@Override
	public void shutdown(Room room) {
		
		onShutdown(room);
	}

	/**
	 * Reset the game state to the point prior to game start
	 *  
	 * @param room
	 */
	protected void onInitialise(Room room) {};

	/**
	 * Execute a slice of game time
	 *  
	 * @param room
	 * @return true if the game is running
	 */
	protected abstract boolean onUpdate(Room room);
	
	/**
	 * The game is about to be packed away - tidy up!
	 * 
	 * @param room
	 */
	protected void onShutdown(Room room) {};
	
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
