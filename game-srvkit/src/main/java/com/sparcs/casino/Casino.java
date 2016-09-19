package com.sparcs.casino;

import java.util.List;

import com.sparcs.casino.Customer;
import com.sparcs.casino.game.Game;
import com.sparcs.casino.game.GameType;
import com.sparcs.casino.game.Room;

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
     * Retrieve a list of {@link Room}s that are hosting a particular
     * {@link GameType type of game}.
     *  
     * @param customer
     * @param gameType
     * @return
     */
    <R extends Room<? extends Game>> List<R> getGamingRooms(Customer customer, GameType gameType);

    /**
     * Sign out of the Casino.  No API calls can be made after this call completes.
     * 
     * @param customer
     */
    void signOut(Customer customer);
}
