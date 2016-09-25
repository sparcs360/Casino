package com.sparcs.casino.game;

/**
 * Base implementation of {@link Bet}.
 * 
 * @author Lee Newfeld
 */
public abstract class BetImpl implements Bet {

	private Player player;
	private int stake;
	
	/**
	 * Constructor
	 * @param stake Number of chips at risk.
	 */
	protected BetImpl(Player player, int stake) {
		
		this.player = player;
		this.stake = stake;
	}

	@Override
	public Player getPlayer() {

		return player;
	}
	
	@Override
	public int getStake() {

		return stake;
	}
}
