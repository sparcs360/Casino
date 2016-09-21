package com.sparcs.casino.game;

import java.util.List;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * Represents a gaming room within the {@link Casino}.<br>
 * <p>It hosts:<br>
 * <li>A {@link #getGameManager() Game Manager} that controls the {@link GameState}</li>
 * <li>A list of {@link #getSpectators() Spectators}</li>
 * </p>
 * @author Lee Newfeld
 */
public interface Room {

	/**
	 * @return The {@link GameManager manager} of the {@link GameState} inside the room.
	 */
	GameManager getGameManager();
	
	/**
	 * @return The {@link Spectator}s inside the Room (i.e., watching the {@link GameState}
	 * rather than {@link Player playing}).
	 */
	List<Spectator> getSpectators();
	
	/**
	 * @return True if there are no {@link #getSpectators() spectators} or
	 * {@link GameState#getPlayers() players} in the room.
	 */
	boolean isEmpty();

	/**
	 * Enter the room and become a {@link #getSpectators() Spectator}.
	 * 
	 * @param customer
	 * @return The {@link Spectator role} wrapping the {@link Customer}
	 */
    Spectator enter(Customer customer);

    /**
     * Take a seat at the table, become a {@link Player}, and get ready
     * to play!
     * 
     * @param spectator
	 * @return The {@link Player role} wrapping the {@link Customer}
     */
    Player joinGame(Spectator spectator);

    /**
     * Stop playing the game (become a {@link Spectator} again).
     * 
     * @param player
	 * @return The {@link Spectator role} wrapping the {@link Customer}
     */
    Spectator leaveGame(Player player);
    
    /**
     * Exit the room
     * 
     * @param spectator
     */
    void exit(Spectator spectator);

    /**
     * Execute a single cycle of the game loop.
     * 
     * @return true if the game is running 
     */
	boolean executeGameLoop();
}
