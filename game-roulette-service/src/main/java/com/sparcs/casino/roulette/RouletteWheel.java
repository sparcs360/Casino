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
		 * The Wheel is at rest, the ball is in the Croupier's hand.
		 * 
		 * <li>Wheel can be {@link RouletteWheel#reset() reset}</li>
		 * <li>Wheel can be {@link RouletteWheel#start() started}</li>
		 */
		AT_REST,
		
		/**
		 * The Wheel is spinning, the ball is in the Croupier's hand.
		 * 
		 * <li>Wheel can be {@link RouletteWheel#reset() reset}</li>
		 * <li>Wheel <b>cannot</b> be {@link RouletteWheel#start() started}</li>
		 */
		SPINNING,
	}
	
	/**
	 * Reset the Wheel back to its initial {@link State} (i.e., {@link State#AT_REST})
	 */
	void reset();

	/**
	 * @return The {@link State} of the Wheel.
	 */
	State getState();

	/**
	 * Start the Wheel spinning.
	 * 
	 * @throws RouletteException If the wheel's {@link #getState() state}
	 * isn't {@link State#AT_REST}
	 */
	void start();
}
