package com.sparcs.casino;

import java.util.HashMap;
import java.util.Map;

import com.sparcs.casino.game.Bet;

public interface Bank {

	/**
	 * REPRESENTS A BACK-END DATA STORE - ACCESSIBLE VIA AN API
	 */
	public final static Map<Customer, Account> ACCOUNTS = new HashMap<>();

	/**
	 * @param customer The {@link Customer} we want the chip count for.
	 * @return The number of chips they have.
	 */
	int getChipCount(Customer customer);

	/**
	 * A {@link Bet} has been placed - deduct chips from the {@link Customer}s
	 * {@link Account}.
	 * 
	 * @param bet The placed {@link Bet}.
	 */
	void processBet(Bet bet);

	/**
	 * A {@link Bet} has won - add chips to the {@link Customer}s {@link Account}.
	 * 
	 * @param bet The placed {@link Bet}.
	 * @param winnings The number of chips won.
	 */
	void processWinnings(Bet bet, int winnings);
}
