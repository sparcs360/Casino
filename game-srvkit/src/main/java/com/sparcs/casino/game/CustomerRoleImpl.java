package com.sparcs.casino.game;

import com.sparcs.casino.Customer;

/**
 * Base implementation of a Customer Role
 * 
 * @author Lee Newfeld
 */
public abstract class CustomerRoleImpl implements CustomerRole {

	private Customer customer;

	/**
	 * Bean constructor
	 * 
	 * @param customer The customer to grant the role to
	 */
	protected CustomerRoleImpl(Customer customer) {
		
		this.customer = customer;
	}
	
	@Override
	public Customer getCustomer() {

		return customer;
	}

    //####################################################################//
	// Delegate methods on the Customer interface to the Customer 
    //####################################################################//
	
	@Override
	public String getNickName() {

		return customer.getNickName();
	}
}
