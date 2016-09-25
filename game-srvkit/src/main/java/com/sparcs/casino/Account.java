package com.sparcs.casino;

import com.sparcs.casino.game.Bet;

public interface Account {

	/**
	 * @return The {@link Customer} who owns this Account.
	 */
	Customer getCustomer();

	/**
	 * @return The number of chips the Customer currently holds
	 */
	int getChipCount();
	
	/**
	 * A {@link Bet} has been won! - add chips
	 * 
	 * @param pot Number of chips won
	 */
	void addChips(int pot);

	/**
	 * A {@link Bet} has been accepted - remove chips
	 * 
	 * @param stake Number of chips wagered.
	 */
	void deductChips(int stake);
}
