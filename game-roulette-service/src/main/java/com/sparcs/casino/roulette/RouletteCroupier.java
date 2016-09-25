package com.sparcs.casino.roulette;

import java.util.List;

import com.sparcs.casino.events.Event;
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
	 * <p>If the bet was accepted, a {@link RouletteCroupier.BetPlacedEvent} is raised,
	 * otherwise {@link RouletteCroupier.BetRejectedEvent} is raised.</p>
	 * <p>If the bet wins, a {@link RouletteCroupier.BetWinEvent} is raised.</p>
	 * 
	 * @param player The player requesting the bet.
	 * @param bet The type of bet being requested.
	 */
	void considerBet(RoulettePlayer player, RouletteBet bet);

	/**
	 * Get the list of active bets for a {@link RoulettePlayer player}.
	 * 
	 * @param player The {@link RoulettePlayer player} who placed the bets.
	 * @return The list of {@link RouletteBet bets}.
	 */
	List<RouletteBet> getBets(RoulettePlayer player);
	
	/**
	 * Represents a {@link RouletteBet bet} that has been { accepted} or { rejected}
	 * 
	 * @author Lee Newfeld
	 */
	public static abstract class ConsideredBetEvent implements Event {
		
		private RoulettePlayer player;
		private RouletteBet bet;
		
		public ConsideredBetEvent(RoulettePlayer player, RouletteBet bet) {

			this.player = player;
			this.bet = bet;
		}

		public RoulettePlayer getPlayer() {
			return player;
		}

		public RouletteBet getBet() {
			return bet;
		}

		@Override
		public String toString() {

			return String.format("%s@%x[player=%s, bet=%s]",
					getClass().getSimpleName(), hashCode(), player, bet);
		}
	}

	/**
	 * Represents a
	 * {@link RouletteCroupier#considerBet(RoulettePlayer, RouletteBet) considered bet}
	 * that has been accepted.
	 * 
	 * @author Lee Newfeld
	 */
	public static class BetPlacedEvent extends ConsideredBetEvent {
		
		public BetPlacedEvent(RoulettePlayer player, RouletteBet bet) {
			
			super(player, bet);
		}
	}

	/**
	 * Represents a
	 * {@link RouletteCroupier#considerBet(RoulettePlayer, RouletteBet) considered bet}
	 * that has been rejected.
	 * 
	 * @author Lee Newfeld
	 */
	public static class BetRejectedEvent extends ConsideredBetEvent {
		
		private String reason;
		
		public BetRejectedEvent(RoulettePlayer player, RouletteBet bet, String reason) {
			
			super(player, bet);
			
			this.reason = reason;
		}

		/**
		 * @return The reason why the bet wasn't placed
		 */
		public String getReason() {
			
			return reason;
		}

		@Override
		public String toString() {

			return String.format("%s@%x[player=%s, bet=%s, reason=%s]",
					getClass().getSimpleName(), hashCode(), getPlayer(), getBet(), reason);
		}
	}

	/**
	 * Represents a
	 * {@link RouletteCroupier#considerBet(RoulettePlayer, RouletteBet) considered bet}
	 * that has been rejected.
	 * 
	 * @author Lee Newfeld
	 */
	public static class BetWinEvent extends ConsideredBetEvent {
		
		private int winnings;
		
		public BetWinEvent(RoulettePlayer player, RouletteBet bet, int winnings) {
			
			super(player, bet);
			
			this.winnings = winnings;
		}

		/**
		 * @return The number of chips won
		 */
		public int getWinnings() {
			
			return winnings;
		}
	}
}
