package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the current state of a Game.
 * 
 * @author Lee Newfeld
 */
public abstract class GameState {

	/**
	 * The value of {@link #getGameTime()} if the game isn't running
	 */
	public static final long UNDEFINED_TIME = -1;

	/**
	 * The value of {@link #getGameTime()} at the start of a game
	 */
	public static final long START_TIME = 0;

	private long gameTime;
	
	private List<Player> players;
	
	/**
	 * Constructor
	 */
    protected GameState() {

    	gameTime = UNDEFINED_TIME;
        players = new ArrayList<>();
    }

    /**
     * @return Current game time.
     */
    public long getGameTime() {

    	return gameTime;
    }

    /**
     * 
     */
	protected void resetTime() {

		gameTime = START_TIME;
	}

	/**
     * Advance game time by one unit 
     */
    protected void advanceTime() {

		if( gameTime == UNDEFINED_TIME ) {
			
			throw new GameException("Can't advance game time when game isn't running");
		}
		
		gameTime++;
	}

	/**
	 * @return The {@link Player}s currently playing the game.
	 */
	public List<Player> getPlayers() {

		return players;
	}
}
