package com.sparcs.casino.game;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * Represents a {@link Customer} playing a particular role within the
 * {@link Casino} (e.g., a {@link Spectator} or a {@link Player}) 
 * 
 * @author Lee Newfeld
 */
public interface CustomerRole extends Customer {

	/**
	 * @return The {@link Customer} playing this role
	 */
	Customer getCustomer();
}
