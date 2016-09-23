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
	 * @return true if {@link #requestBet(RouletteBet) betting} is allowed
	 */
	boolean isBettingAllowed();

	/**
	 * @return true if {@link #requestBet(RouletteBet) bets} have been resolved
	 * for the current game.
	 */
	boolean areBetsResolved();

	/**
	 * Attempt to place a bet on the current game.
	 * 
	 * @param bet The type of bet you wish to place.
	 * @return true is the bet was accepted.
	 */
	boolean requestBet(RouletteBet bet);
}
