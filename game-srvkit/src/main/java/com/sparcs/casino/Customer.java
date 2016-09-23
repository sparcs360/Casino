package com.sparcs.casino;

import com.sparcs.casino.game.Bet;

/**
 * Represents a {@link Casino} patron.
 * 
 * @author Lee Newfeld
 */
public interface Customer {

	/**
	 * @return The name displayed to other Customers
	 */
	String getNickName();
	
	/**
	 * @return The number of chips the Customer currently holds
	 */
	int getChipCount();

	/**
	 * A {@link Bet} has been accepted - remove chips
	 * 
	 * @param stake Number of chips at risk.
	 */
	void deductChips(int stake);
	
	/**
	 * A {@link Bet} has been won! - add chips
	 * 
	 * @param pot Number of chips won
	 */
	void addChips(int pot);
}
