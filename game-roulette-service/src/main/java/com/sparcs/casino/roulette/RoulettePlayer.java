package com.sparcs.casino.roulette;

import java.util.List;

import com.sparcs.casino.Customer;
import com.sparcs.casino.events.Event;
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
	 * <p>See {@link RouletteCroupier#considerBet(RoulettePlayer, RouletteBet)}
	 * for information on the possible {@link Event}s that are raised as a result
	 * of calling this method.</p>
	 * 
	 * @param bet The type of bet you wish to place.
	 */
	void requestBet(RouletteBet bet);

	/**
	 * @return The list of {@link RouletteBet bets} currently in play
	 */
	List<RouletteBet> getBets();
}
