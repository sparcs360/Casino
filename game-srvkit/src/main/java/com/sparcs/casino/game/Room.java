package com.sparcs.casino.game;

import java.util.List;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * The specification for a gaming room within the {@link Casino}.
 * 
 * @author Lee Newfeld
 *
 * @param <G> The {@link Game} subclass that the room hosts
 */
public interface Room<G extends Game> {

	/**
	 * @return The {@link Game} inside the room.
	 */
	G getGame();
	
	/**
	 * @return The {@link Customer}s inside the Room who aren't playing
	 * the {@link Game}
	 */
	List<Customer> getSpectators();
	
	/**
	 * @return True if there are no {@link #getSpectators() spectators} or
	 * {@link Game#getPlayers() players} in the room.
	 */
	boolean isEmpty();

	/**
	 * Enter the room and become a {@link #getSpectators() Spectator}.
	 * 
	 * @param customer
	 */
    void enter(Customer customer);

    /**
     * Leave the room
     * 
     * @param customer
     */
    void leave(Customer customer);
}
