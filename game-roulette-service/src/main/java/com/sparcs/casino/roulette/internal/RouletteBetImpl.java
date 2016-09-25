package com.sparcs.casino.roulette.internal;

import com.sparcs.casino.game.BetImpl;
import com.sparcs.casino.roulette.RouletteBet;
import com.sparcs.casino.roulette.RoulettePlayer;

/**
 * Base implementation of a {@link RouletteBet}.
 * 
 * @author Lee Newfeld
 */
public abstract class RouletteBetImpl extends BetImpl implements RouletteBet {

	/**
	 * Constructor.
	 *  
	 * @param player The {@link RoulettePlayer} making the bet
	 * @param stake The number of chips at risk
	 */
	protected RouletteBetImpl(RoulettePlayer player, int stake) {
		
		super(player, stake);
	}
}
