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
		 * @param chips Number of chips to wager.
		 * @param number The number to bet on.
		 */
		private SingleBet(int chips, int number) {
			
			super(chips);
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

			return number==result ? chips*36 : 0;
		}

		@Override
		public String toString() {

			return String.format("SingleBet@%x[%dc on #%d]",
					this.hashCode(), chips, number);
		}
	}

	static SingleBet singleBet(int chips, int number) {

		return new SingleBet(chips, number);
	}
}
