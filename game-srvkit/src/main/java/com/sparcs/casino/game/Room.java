package com.sparcs.casino.game;

import java.util.List;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * The specification for a gaming room within the {@link Casino}.
 * 
 * @author Lee Newfeld
 */
public interface Room {

	/**
	 * @return The {@link Game} inside the room.
	 */
	Game getGame();
	
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
     * Take a seat at the table and get ready to play
     * 
     * @param player
     */
    void join(Customer player);

    /**
     * Stop playing the game and become a Spectator.
     * 
     * @param player
     */
    void leave(Customer player);
    
    /**
     * Exit the room
     * 
     * @param customer
     */
    void exit(Customer customer);

    /**
     * Execute a single cycle of the game loop.
     * 
     * @return true if the game is running 
     */
	boolean executeGameLoop();
}
