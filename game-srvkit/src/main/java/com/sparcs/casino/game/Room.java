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
	 * Create a new Room with the specified Game installed within it
	 * 
	 * @param game The game to install into the Room
	 * @param <G> The {@link Game} subclass that the room hosts
	 * @return New Room
	 */
	static <G extends Game> Room<G> create(G game) {

		// TODO: Urgh.  Need a better way of creating the required bi-directional
		// association between Room and Game (required so that the Customer can
		// be moved from the Game.players collection to the Room.spectators
		// collection).
		//
		// Maybe we'll have the Room observing the Game and watching for
		// join/leave events
		Room<G> room = new RoomImpl<G>(game);
		game.installInto(room);
		return room;
	}

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
     * Exit the room
     * 
     * @param customer
     */
    void exit(Customer customer);
}
