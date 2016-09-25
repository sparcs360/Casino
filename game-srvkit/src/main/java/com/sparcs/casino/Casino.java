package com.sparcs.casino;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sparcs.casino.game.GameType;
import com.sparcs.casino.game.Room;

/**
 * Represents the Casino, a host for games!
 * 
 * @author Lee Newfeld
 */
public interface Casino {

	/**
	 * REPRESENTS A BACK-END DATA STORE - ACCESSIBLE VIA AN API
	 */
	public final static Map<String, Customer> CUSTOMERS = new HashMap<>();

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
     * Retrieve a list of {@link Room}s that are hosting a particular
     * {@link GameType type of game}.
     * 
     * @param customer The Customer doing the browsing
     * @param gameType The type of Game the Customer is looking for
     * @return The list of applicable games 
     */
    List<Room> findRooms(Customer customer, GameType gameType);

    /**
     * Sign out of the Casino.  No API calls can be made after this call completes.
     * 
     * @param customer
     */
    void signOut(Customer customer);
}
