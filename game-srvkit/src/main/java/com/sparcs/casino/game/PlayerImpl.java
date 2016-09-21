package com.sparcs.casino.game;

import com.sparcs.casino.Customer;

/**
 * Base implementation of a {@link Player}
 * 
 * @author Lee Newfeld
 */
public abstract class PlayerImpl extends CustomerRoleImpl implements Player {

	protected PlayerImpl(Customer customer) {
		
		super(customer);
	}
}
