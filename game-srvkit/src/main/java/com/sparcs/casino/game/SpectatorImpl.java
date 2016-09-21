package com.sparcs.casino.game;

import com.sparcs.casino.Customer;

/**
 * Base implementation of a {@link Spectator}
 * 
 * @author Lee Newfeld
 */
public abstract class SpectatorImpl extends CustomerRoleImpl implements Spectator {

	protected SpectatorImpl(Customer customer) {

		super(customer);
	}
}
