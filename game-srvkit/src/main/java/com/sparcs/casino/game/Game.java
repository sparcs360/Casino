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
	 * @return The {@link Customer}s currently playing the {@link Game}
	 */
    List<Customer> getPlayers();

    /**
     * @return true if a game currently in progress
     */
    boolean isRunning();
}
