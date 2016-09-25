package com.sparcs.casino;

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
}
