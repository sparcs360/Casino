package com.sparcs.casino.otherdomains;

/**
 * Represents the Casino, a host for games!
 * 
 * @author Lee Newfeld
 */
public interface Casino {

	/**
	 * A {@link Customer} must sign into the Casino, by providing their
	 * credentials, before they can access the API.
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
    Customer signIn(String userId, String password);

    /**
     * Sign out of the Casino.  No API calls can be made after this call completes.
     * 
     * @param customer
     */
    void signOut(Customer customer);
}
