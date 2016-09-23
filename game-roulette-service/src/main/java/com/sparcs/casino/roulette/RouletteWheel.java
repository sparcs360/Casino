package com.sparcs.casino.roulette;

/**
 * The wheel in a game of Roulette.
 *  
 * @author Lee Newfeld
 */
public interface RouletteWheel {

	/**
	 * The value returned by {@link #getResult()} when a result can't be determined.
	 */
	public static final int RESULT_UNDEFINED = -1;

	/**
	 * Represents the lifecycle stage of a {@link RouletteWheel}.<br>
	 * <p>
	 * Use {@link RouletteWheel#update()} to move to the next stage:
	 * <ul>
	 * <li>{@link #AT_REST}</li>
	 * <li>{@link #SPINNING}</li>
	 * <li>{@link #BALL_SPINNING}</li>
	 * <li>{@link #NO_MORE_BETS}</li>
	 * <li>{@link #BALL_AT_REST}</li>
	 * <li>{@link #BETS_RESOLVED}</li>
	 * </ul>
	 * </p>
	 * 
	 * @author Lee Newfeld
	 */
	static enum Stage {
		
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
		 * <li>The Croupier starts to resolve bets.</li>
		 */
		BALL_AT_REST,

		/**
		 * <li>The Wheel is <b>at rest</b>.</li>
		 * <li>The Ball is <b>at rest</b>.</li>
		 * <li>Players cannot bet.</li>
		 * <li>The Croupier has <b>resolved all bets</b> and is about to start a new game.</li>
		 */
		BETS_RESOLVED,;

		/**
		 * @return true if players are allowed to bet during this stage
		 */
		public boolean isBettingAllowed() {

			return this==AT_REST || this==SPINNING || this==BALL_SPINNING;
		}
	}
	
	/**
	 * Reset the Wheel back to its initial {@link Stage} (i.e., {@link Stage#AT_REST})
	 */
	void reset();

	/**
	 * <p>Move the wheel to the next stage in its lifecylce.</p>
	 * <p>
	 * <table border='1'>
	 * 	<tr>
	 *   <td><b>Current Stage</b></td>
	 *   <td><b>Stage After Update</b></td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link Stage#AT_REST}</td>
	 *   <td>{@link Stage#SPINNING}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link Stage#SPINNING}</td>
	 *   <td>{@link Stage#BALL_SPINNING}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link Stage#BALL_SPINNING}</td>
	 *   <td>{@link Stage#NO_MORE_BETS}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link Stage#NO_MORE_BETS}</td>
	 *   <td>{@link Stage#BALL_AT_REST}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link Stage#BALL_AT_REST}</td>
	 *   <td>{@link Stage#BETS_RESOLVED}</td>
	 *  </tr>
	 * 	<tr>
	 *   <td>{@link Stage#BETS_RESOLVED}</td>
	 *   <td>{@link Stage#AT_REST}</td>
	 *  </tr>
	 * </table>
	 * </p>
	 * 
	 * @return The new {@link #getStage() state} of the Wheel (same as {@link #getStage()}) 
	 */
	Stage update();
	
	/**
	 * @return The current lifecycle {@link Stage} of the Wheel.
	 */
	Stage getStage();

	/**
	 * @return The number that the Ball landed on.<br>
	 * Only valid if the {@link #getStage() current state}
	 * of the Wheel is {@link Stage#BALL_AT_REST} or {@link Stage#BETS_RESOLVED}.  At all
	 * other times, the return value is {@link RouletteWheel.RESULT_UNDEFINED}.
	 */
	int getResult();

	/**
	 * @param number A number on the Wheel
	 * @return true if this number can be bet on
	 */
	static boolean canBetOnNumber(int number) {

		return number > 0 && number <= 36;
	}
}
