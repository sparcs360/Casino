package com.sparcs.casino.roulette.internal;

import com.sparcs.casino.game.BetImpl;
import com.sparcs.casino.roulette.RouletteBet;

/**
 * Base implementation of a {@link RouletteBet}.
 * 
 * @author Lee Newfeld
 */
public abstract class RouletteBetImpl extends BetImpl implements RouletteBet {

	protected RouletteBetImpl(int chips) {
		
		super(chips);
	}
}
