package com.sparcs.casino.game;

import java.util.List;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * Represents a Game that can be played in the {@link Casino}
 * 
 * @author Lee Newfeld
 */
public interface Game {
	
	/**
	 * Create the bi-directional association with the Room the Game is in
	 * 
	 * @param room
	 */
	void installInto(Room<? extends Game> room);
	
	/**
	 * @return The {@link Customer}s currently playing the {@link Game}
	 */
    List<Customer> getPlayers();

    /**
     * Take a seat at the table and get ready to play
     * 
     * @param player
     */
    void join(Customer player);

    /**
     * Stop playing the game and become a Spectator.
     * 
     * @param player
     */
    void leave(Customer player);

    /**
     * @return true if a game currently in progress
     */
    boolean isRunning();
}
