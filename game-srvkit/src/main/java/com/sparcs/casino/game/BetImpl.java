package com.sparcs.casino.game;

/**
 * Base implementation of {@link Bet}.
 * 
 * @author Lee Newfeld
 */
public abstract class BetImpl implements Bet {

	protected int stake;
	
	/**
	 * Constructor
	 * @param stake Number of chips at risk.
	 */
	protected BetImpl(int stake) {
		
		this.stake = stake;
	}

	@Override
	public int getStake() {

		return stake;
	}
}
