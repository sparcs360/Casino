package com.sparcs.casino.roulette;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sparcs.casino.game.Bet;
import com.sparcs.casino.game.GameManager;
import com.sparcs.casino.roulette.internal.RouletteBetImpl;

/**
 * Represents a bet placed by a {@link RoulettePlayer Player} with
 * the {@link RouletteCroupier Croupier} of a game of Roulette.
 * 
 * @author Lee Newfeld
 */
public interface RouletteBet extends Bet {

	/**
	 * Given the result, how much has this bet won?
	 * 
	 * @param result The number that came up on the wheel
	 * @return The number of chips won
	 */
	int calculateWinnings(int result);

	//####################################//
	//#
	//#	Implementations for various Bets
	//#	
	//####################################//

	/**
	 * Represents a bet on a single number.
	 *  
	 * @author Lee Newfeld
	 */
	static class SingleBet extends RouletteBetImpl {

		private static final Logger log = LoggerFactory.getLogger(RouletteBet.class);

		private int number;

		/**
		 * Constructor
		 * 
		 * @param stake Number of chips at risk.
		 * @param number The number to bet on.
		 */
		private SingleBet(RoulettePlayer player, int stake, int number) {
			
			super(player, stake);
			this.number = number;
		}
		
		/**
		 * @return The number bet on. 
		 */
		public int getNumber() {
			
			return number;
		}

		@Override
		public boolean isValid(GameManager gameManager) {

			boolean valid = RouletteWheel.canBetOnNumber(number);
			log.trace("{}: isValid={}", this, valid);
			return valid;
		}

		@Override
		public int calculateWinnings(int result) {

			return number==result ? getStake()*36 : 0;
		}

		@Override
		public String toString() {

			return String.format("SingleBet@%x[%dc on #%d]",
					this.hashCode(), getStake(), number);
		}
	}

	/**
	 * Create a request to bet on a single number.
	 * 
	 * @param stake Number of chips at risk.
	 * @param number The number to bet on.
	 * @return The bet.
	 */
	static SingleBet singleBet(RoulettePlayer player, int stake, int number) {

		return new SingleBet(player, stake, number);
	}
}
