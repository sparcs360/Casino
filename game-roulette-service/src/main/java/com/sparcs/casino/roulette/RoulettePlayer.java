package com.sparcs.casino.roulette;

import com.sparcs.casino.Customer;
import com.sparcs.casino.game.Player;

/**
 * A {@link Customer} playing a game of Roulette in a {@link RouletteRoom}.
 * 
 * @author Lee Newfeld
 */
public interface RoulettePlayer extends Player {

	/**
	 * @return true if {@link #placeSingleBet(int) betting} is allowed
	 */
	boolean isBettingAllowed();

	/**
	 * @return true if {@link #placeSingleBet(int) bets} have been resolved
	 * for the current game.
	 */
	boolean areBetsResolved();

	/**
	 * Place a bet on a single number.
	 * 
	 * @param number The number to bet on
	 * @param amount The amount to bet
	 * @return true is the bet was accepted.
	 */
	boolean requestSingleBet(int number, int amount);
}
