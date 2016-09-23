package com.sparcs.casino.game;

/**
 * Base implementation of {@link Bet}.
 * 
 * @author Lee Newfeld
 */
public abstract class BetImpl implements Bet {

	protected int chips;
	
	/**
	 * Constructor
	 * @param chips Number of chips being wagered.
	 */
	protected BetImpl(int chips) {
		
		this.chips = chips;
	}

	@Override
	public int getChipsWagered() {

		return chips;
	}
}
