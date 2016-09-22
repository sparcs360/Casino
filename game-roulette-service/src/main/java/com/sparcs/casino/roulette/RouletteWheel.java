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
		 * <li>The Wheel is <b>at rest</b>.</li>
		 * <li>The Ball is in the Croupier's hand.</li>
		 * <li>Players can bet.</li>
		 */
		AT_REST,
		
		/**
		 * <li>The Wheel is <b>spinning</b>.</li>
		 * <li>The Ball is in the Croupier's hand.</li>
		 * <li>Players can bet.</li>
		 */
		SPINNING,
		
		/**
		 * <li>The Wheel is <b>spinning</b>.</li>
		 * <li>The Ball is <b>spinning</b>.</li>
		 * <li>Players can bet.</li>
		 */
		BALL_SPINNING,
		
		/**
		 * <li>The Wheel is <b>spinning</b>.</li>
		 * <li>The Ball is <b>spinning</b>.</li>
		 * <li>Players cannot bet.</li>
		 */
		NO_MORE_BETS,
		
		/**
		 * <li>The Wheel is <b>spinning</b> (slowly).</li>
		 * <li>The Ball is <b>at rest</b>.</li>
		 * <li>Players cannot bet.</li>
		 */
		BALL_AT_REST,
	}
	
	/**
	 * Reset the Wheel back to its initial {@link State} (i.e., {@link State#AT_REST})
	 */
	void reset();

	/**
	 * Start the Wheel spinning.
	 * 
	 * @throws RouletteException If the wheel's {@link #getState() state}
	 * isn't {@link State#AT_REST}
	 */
	void start();

	/**
	 * <p>Move the wheel to the next stage in its lifecylce.</p>
	 * <p>
	 * <table border='1'>
	 * 	<tr>
	 *   <td><b>Current State</b></td>
	 *   <td><b>State After Update</b></td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link State#AT_REST}</td>
	 *   <td>{@link State#AT_REST}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link State#SPINNING} (via {@link #start()})</td>
	 *   <td>{@link State#BALL_SPINNING}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link State#BALL_SPINNING}</td>
	 *   <td>{@link State#NO_MORE_BETS}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link State#NO_MORE_BETS}</td>
	 *   <td>{@link State#BALL_AT_REST}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link State#BALL_AT_REST}</td>
	 *   <td>{@link State#AT_REST}</td>
	 *  </tr>
	 * </table>
	 * </p>
	 * 
	 * @return The new {@link #getState() state} of the Wheel (same as {@link #getState()}) 
	 */
	State update();
	
	/**
	 * @return The {@link State} of the Wheel.
	 */
	State getState();
}
