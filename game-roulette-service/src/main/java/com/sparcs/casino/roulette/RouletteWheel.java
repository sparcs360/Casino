package com.sparcs.casino.roulette;

/**
 * The wheel in a game of Roulette.
 *  
 * @author Lee Newfeld
 */
public interface RouletteWheel {

	/**
	 * Represents the various states of a {@link RouletteWheel}
	 * 
	 * @author Lee Newfeld
	 */
	static enum State {
		
		/**
		 * The Wheel isn't spinning, the ball is in the Croupier's hand.
		 */
		AT_REST,

	}
	
	/**
	 * Reset the Wheel back to its initial {@link State} (i.e., {@link State#AT_REST})
	 */
	void reset();

	/**
	 * @return The {@link State} of the Wheel.
	 */
	State getState();
}
