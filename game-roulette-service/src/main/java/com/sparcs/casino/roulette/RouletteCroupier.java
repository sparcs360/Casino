package com.sparcs.casino.roulette;

import com.sparcs.casino.game.GameManager;

/**
 * The enforcer of the rules!
 * 
 * @author Lee Newfeld
 */
public interface RouletteCroupier extends GameManager {

	/**
	 * Represents the lifecycle stage of the current game.
	 *   
	 * @author Lee Newfeld
	 */
	static enum Stage {
		
		/**
		 * The Croupier is waiting to start a new game. 
		 */
		STARTING_NEW_GAME,
		
		/**
		 * The Croupier has resolved all bets. 
		 */
		BETS_RESOLVED,
	}

	/**
	 * @return true if {@link #considerSingleBet(RoulettePlayer, int, int) betting}
	 * is allowed.
	 */
	boolean isBettingAllowed();

	/**
	 * @return true if {@link #considerSingleBet(RoulettePlayer, int, int) bets}
	 * have been resolved for the current game.
	 */
	boolean areBetsResolved();

	/**
	 * Consider a single number bet from a player.
	 * 
	 * @param player The player requesting the bet.
	 * @param number The number the player wishes to bet on.
	 * @param amount The amount they wish to bet.
	 * @return true if the bet was accepted.
	 */
	boolean considerSingleBet(RoulettePlayer player, int number, int amount);
}
