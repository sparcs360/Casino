package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * The base implementation of a gaming room within the {@link Casino}.
 * 
 * @author Lee Newfeld
 */
public abstract class RoomImpl implements Room {

	/**
	 * The {@link GameImpl} inside the {@link Room}
	 */
	@Autowired
	private GameImpl game;
	
    private List<Customer> spectators;
	
    /**
     * Constructor
     */
	protected RoomImpl() {

        spectators = new ArrayList<>();
	}

	@Override
	public Game getGame() {
		
		return game;
	}

	@Override
	public List<Customer> getSpectators() {

        return spectators;
	}

	@Override
	public boolean isEmpty() {

		return spectators.isEmpty() && game.getPlayers().isEmpty();
	}

	@Override
	public void enter(Customer customer) {

        spectators.add(customer);
	}

	@Override
	public void join(Customer player) {

        getSpectators().remove(player);
        game.getPlayers().add(player);
	}

	@Override
	public void leave(Customer player) {

        game.getPlayers().remove(player);
        getSpectators().add(player);
	}

	@Override
	public void exit(Customer customer) {

        spectators.remove(customer);
	}
}
