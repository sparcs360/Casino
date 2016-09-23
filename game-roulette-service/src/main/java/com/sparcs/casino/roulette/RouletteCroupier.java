package com.sparcs.casino.roulette;

import java.util.List;

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
	 * @return true if {@link #considerBet(RoulettePlayer, int, int) betting}
	 * is allowed.
	 */
	boolean isBettingAllowed();

	/**
	 * @return true if {@link #considerBet(RoulettePlayer, int, int) bets}
	 * have been resolved for the current game.
	 */
	boolean areBetsResolved();

	/**
	 * Consider taking a bet from a player.
	 * 
	 * @param player The player requesting the bet.
	 * @param bet The type of bet being requested.
	 * @return true if the bet was accepted.
	 */
	boolean considerBet(RoulettePlayer player, RouletteBet bet);

	/**
	 * Get the list of active bets for a {@link RoulettePlayer player}.
	 * 
	 * @param player The {@link RoulettePlayer player} who placed the bets.
	 * @return The list of {@link RouletteBet bets}.
	 */
	List<RouletteBet> getBets(RoulettePlayer player);
}
