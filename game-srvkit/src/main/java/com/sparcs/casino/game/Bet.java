package com.sparcs.casino.game;

/**
 * Represents a bet placed by a {@link Player} with the
 * {@link GameManager} of a game.
 *  
 * @author Lee Newfeld
 */
public interface Bet {

	/**
	 * @return The {@link Player} who placed the Bet.
	 */
	Player getPlayer();
	
	/**
	 * @return The number of chips at risk.
	 */
	int getStake();

	/**
	 * @return true if the {@link GameManager} considers this to be
	 * a valid bet. 
	 */
	boolean isValid(GameManager gameManager);
}
